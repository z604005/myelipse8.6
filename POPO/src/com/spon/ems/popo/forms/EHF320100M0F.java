package com.spon.ems.popo.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.ems.popo.models.EHF320100;
import com.spon.utils.util.BA_Vaildate;

public class EHF320100M0F extends ActionForm implements ValidateForm {
	
	private String EHF320100T0_01;
	private String EHF320100T0_02;
	private String EHF320100T0_03;
	private String EHF320100T0_04;
	private String EHF320100T0_05;
	private String EHF320100T0_06;
	private String EHF320100T0_07;
	private String EHF320100T0_08;
	
	private String EHF320100T0_04_TXT;
	private String EHF320100T0_05_TXT;
	private String EHF320100T0_06_TXT;
	private String EHF320100T0_07_TXT;
	
	private String EHF320100T1_01;
	private int EHF320100T1_02;
	private String EHF320100T1_03;
	private String EHF320100T1_03_TXT;
	
	private String EHF320100T2_01;
	private int EHF320100T2_02;
	private String EHF320100T2_03;
	private String EHF320100T2_03_TXT;
	
	private String EHF320100T3_01;
	private int EHF320100T3_02;
	private String EHF320100T3_03;
	private String EHF320100T3_04;
	private String EHF320100T3_05;
	private String EHF320100T3_06;
	private String EHF320100T3_07;
	
	private String EHF320100T3_03_TXT;
	private String EHF320100T3_04_TXT;
	private String EHF320100T3_05_TXT;
	private String EHF320100T3_06_TXT;
	
	private String EHF320100T4_01;
	private int EHF320100T4_02;
	private String EHF320100T4_03;
	
	private String EHF320100T4_03_SHOW;
	private String EHF320100T4_03_TXT;
	
	private String EHF320100T5_01;
	private int EHF320100T5_02;
	private String EHF320100T5_03;
	
	private String EHF320100T5_03_SHOW;
	private String EHF320100T5_03_TXT;
	
	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_CREATE;
	private String DATE_UPDATE;
	
	private List EHF320100T0_LIST = new ArrayList();
	private List EHF320100T3_DETAIL = new ArrayList();
	private List EHF320100T1_DETAIL = new ArrayList();
	private List EHF320100T2_DETAIL = new ArrayList();
	private List EHF320100T4_DETAIL = new ArrayList();
	private List EHF320100T5_DETAIL = new ArrayList();

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		if (l_actionErrors.isEmpty()) {
			
			//新增時判斷條件
			if (request.getAttribute("action").equals("addData")) {
				
				ve.isEmpty(l_actionErrors, EHF320100T0_02, "EHF320100T0_02", "不可空白");
				ve.isEmpty(l_actionErrors, EHF320100T0_03, "EHF320100T0_03", "不可空白");
				ve.isEmpty(l_actionErrors, EHF320100T0_04, "EHF320100T0_04", "不可空白");
				ve.isEmpty(l_actionErrors, EHF320100T0_05, "EHF320100T0_05", "不可空白");
				ve.isEmpty(l_actionErrors, EHF320100T0_06, "EHF320100T0_06", "不可空白");
				ve.isEmpty(l_actionErrors, EHF320100T0_07, "EHF320100T0_07", "不可空白");
								
				addData_validate(l_actionErrors, request, conn);
			}
			
			//新增明細時判斷條件
			if (request.getAttribute("action").equals("addEHF320100T3")) {
				
				ve.isEmpty(l_actionErrors, EHF320100T3_03, "EHF320100T3_03", "不可空白");
				ve.isEmpty(l_actionErrors, EHF320100T3_04, "EHF320100T3_04", "不可空白");
				ve.isEmpty(l_actionErrors, EHF320100T3_05, "EHF320100T3_05", "不可空白");
				
				addEHF320100T3_validate(l_actionErrors, request, conn);
			}
			
			if (request.getAttribute("action").equals("addEHF320100T1")) {
				
				ve.isEmpty(l_actionErrors, EHF320100T1_03, "EHF320100T1_03", "不可空白");
				
				addEHF320100T1_validate(l_actionErrors, request, conn);
			}

			if (request.getAttribute("action").equals("addEHF320100T2")) {
				
				ve.isEmpty(l_actionErrors, EHF320100T2_03, "EHF320100T2_03", "不可空白");
				
				addEHF320100T2_validate(l_actionErrors, request, conn);
			}
			
			if (request.getAttribute("action").equals("addEHF320100T4")) {
				
				ve.isEmpty(l_actionErrors, EHF320100T4_03, "EHF320100T4_03_SHOW", "不可空白");
				
				addEHF320100T4_validate(l_actionErrors, request, conn);
			}

			if (request.getAttribute("action").equals("addEHF320100T5")) {
				
				ve.isEmpty(l_actionErrors, EHF320100T5_03, "EHF320100T5_03_SHOW", "不可空白");
				
				addEHF320100T5_validate(l_actionErrors, request, conn);
			}

			//修改時判斷條件
			if (request.getAttribute("action").equals("saveData")) {
				
				ve.isEmpty(l_actionErrors, EHF320100T0_02, "EHF320100T0_02", "不可空白");
				ve.isEmpty(l_actionErrors, EHF320100T0_03, "EHF320100T0_03", "不可空白");
				ve.isEmpty(l_actionErrors, EHF320100T0_04, "EHF320100T0_04", "不可空白");
				ve.isEmpty(l_actionErrors, EHF320100T0_05, "EHF320100T0_05", "不可空白");
				ve.isEmpty(l_actionErrors, EHF320100T0_06, "EHF320100T0_06", "不可空白");
				ve.isEmpty(l_actionErrors, EHF320100T0_07, "EHF320100T0_07", "不可空白");

				saveData_validate(l_actionErrors, request, conn);
			}
			
			//刪除時判斷條件
			if (request.getAttribute("action").equals("delData")) {
				
				delData_validate(l_actionErrors, request, conn);
			}
			
		}
		
		return l_actionErrors;
	}

	private void addEHF320100T5_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		try{
			EHF320100 ehf320100 = new EHF320100();
			
			Map idMap = ehf320100.CheckSingleField("EHF320100T5_02", "EHF320100T5", String.valueOf(EHF320100T5_02), " AND EHF320100T5_01 = '"+EHF320100T0_01+"' ");
			
			if(!idMap.isEmpty()){
				errors.add("EHF320100T5_02",new ActionMessage(""));
				request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":
					request.getAttribute("ErrMSG")+"<br>")+"遞補順序重複，請重新確認!" + "<br>");
			}
			
			ehf320100.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private void addEHF320100T4_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		try{
			EHF320100 ehf320100 = new EHF320100();
			
			Map idMap = ehf320100.CheckSingleField("EHF320100T4_02", "EHF320100T4", String.valueOf(EHF320100T4_02), " AND EHF320100T4_01 = '"+EHF320100T0_01+"' ");
			
			if(!idMap.isEmpty()){
				errors.add("EHF320100T4_02",new ActionMessage(""));
				request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":
					request.getAttribute("ErrMSG")+"<br>")+"遞補順序重複，請重新確認!" + "<br>");
			}
			
			ehf320100.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private void addEHF320100T3_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		String arrid[] = request.getParameterValues("checkId");
		
		try{
			EHF320100 ehf320100 = new EHF320100();
			
			Map idMap = ehf320100.CheckSingleField("EHF320100T3_05", "EHF320100T3", EHF320100T3_05, " AND EHF320100T3_01 = '"+EHF320100T0_01+"' ");
			
			if(!idMap.isEmpty()){
				errors.add("EHF320100T3_05",new ActionMessage(""));
				request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":
					request.getAttribute("ErrMSG")+"<br>")+"食材重複，請重新確認!" + "<br>");
			}
			
			ehf320100.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private void addEHF320100T1_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		String arrid[] = request.getParameterValues("checkId");
		
		try{
			EHF320100 ehf320100 = new EHF320100();
			
			Map idMap = ehf320100.CheckSingleField("EHF320100T1_03", "EHF320100T1", EHF320100T1_03, " AND EHF320100T1_01 = '"+EHF320100T0_01+"' ");
			
			if(!idMap.isEmpty()){
				errors.add("EHF320100T1_03",new ActionMessage(""));
				request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":
					request.getAttribute("ErrMSG")+"<br>")+"添加物重複，請重新確認!" + "<br>");
			}
			
			ehf320100.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private void addEHF320100T2_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		String arrid[] = request.getParameterValues("checkId");
		
		try{
			EHF320100 ehf320100 = new EHF320100();
			
			Map idMap = ehf320100.CheckSingleField("EHF320100T2_03", "EHF320100T2", EHF320100T2_03, " AND EHF320100T2_01 = '"+EHF320100T0_01+"' ");
			
			if(!idMap.isEmpty()){
				errors.add("EHF320100T2_03",new ActionMessage(""));
				request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":
					request.getAttribute("ErrMSG")+"<br>")+"擺盤重複，請重新確認!" + "<br>");
			}
			
			ehf320100.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private void delData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		String arrid[] = request.getParameterValues("checkId");
		String comp_id = (String) request.getAttribute("compid");
		
		try{
			String sql = "SELECT EHF320200T0_03 FROM EHF320200T0 " +
						 "WHERE 1 = 1 " +
						 "AND EHF320200T0_03 = '"+arrid[0]+"' " +
						 "AND EHF320200T0_04 = '"+comp_id+"' ";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				errors.add("EHF320100T0_01",new ActionMessage(""));
				request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":
					request.getAttribute("ErrMSG")+"<br>")+"菜單已被上菜順序設定使用，不可刪除!" + "<br>");
			}
			
			rs.close();
			stmt.close();
			
			String sql_self_m = "SELECT EHF320100T4_03 FROM EHF320100T4 " +
			 				  "WHERE 1 = 1 " +
			 				  "AND EHF320100T4_03 = '"+arrid[0]+"' " ;

			Statement stmt_self_m = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs_self_m = stmt_self_m.executeQuery(sql_self_m);

			if(rs_self_m.next()){
				errors.add("EHF320100T0_01",new ActionMessage(""));
				request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":
					request.getAttribute("ErrMSG")+"<br>")+"菜單已在遞補主食選項，不可刪除!" + "<br>");
			}

			rs_self_m.close();
			stmt_self_m.close();
			
			String sql_self_p = "SELECT EHF320100T5_03 FROM EHF320100T5 " +
								"WHERE 1 = 1 " +
								"AND EHF320100T5_03 = '"+arrid[0]+"' " ;

			Statement stmt_self_p = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs_self_p = stmt_self_p.executeQuery(sql_self_p);

			if(rs_self_p.next()){
				errors.add("EHF320100T0_01",new ActionMessage(""));
				request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":
					request.getAttribute("ErrMSG")+"<br>")+"菜單已在遞補副食選項，不可刪除!" + "<br>");
			}

			rs_self_p.close();
			stmt_self_p.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private void saveData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		String comp_id = (String) request.getAttribute("compid");
		
		try{
			EHF320100 ehf320100 = new EHF320100();
			
			Map nameMap = ehf320100.CheckSingleField("EHF320100T0_03", "EHF320100T0", EHF320100T0_03, " AND EHF320100T0_08 = '"+comp_id+"' " +
					"AND EHF320100T0_01 <> '"+EHF320100T0_01+"' ");
			
			if(!nameMap.isEmpty()){
				errors.add("EHF320100T0_03",new ActionMessage(""));
				request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":
					request.getAttribute("ErrMSG")+"<br>")+"菜單名稱重複，請重新確認!" + "<br>");
			}
			
			ehf320100.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	private void addData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		String arrid[] = request.getParameterValues("checkId");
		String comp_id = (String) request.getAttribute("compid");
		
		try{
			EHF320100 ehf320100 = new EHF320100();
			
			Map idMap = ehf320100.CheckSingleField("EHF320100T0_02", "EHF320100T0", EHF320100T0_02, " AND EHF320100T0_08 = '"+comp_id+"' ");
			
			if(!idMap.isEmpty()){
				errors.add("EHF320100T0_02",new ActionMessage(""));
				request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":
					request.getAttribute("ErrMSG")+"<br>")+"菜單編號重複，請重新確認!" + "<br>");
			}
			
			Map nameMap = ehf320100.CheckSingleField("EHF320100T0_03", "EHF320100T0", EHF320100T0_03, " AND EHF320100T0_08 = '"+comp_id+"' ");
			
			if(!nameMap.isEmpty()){
				errors.add("EHF320100T0_03",new ActionMessage(""));
				request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":
					request.getAttribute("ErrMSG")+"<br>")+"菜單名稱重複，請重新確認!" + "<br>");
			}
			
			ehf320100.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public String getEHF320100T0_01() {
		return EHF320100T0_01;
	}

	public void setEHF320100T0_01(String eHF320100T0_01) {
		EHF320100T0_01 = eHF320100T0_01;
	}

	public String getEHF320100T0_02() {
		return EHF320100T0_02;
	}

	public void setEHF320100T0_02(String eHF320100T0_02) {
		EHF320100T0_02 = eHF320100T0_02;
	}

	public String getEHF320100T0_03() {
		return EHF320100T0_03;
	}

	public void setEHF320100T0_03(String eHF320100T0_03) {
		EHF320100T0_03 = eHF320100T0_03;
	}

	public String getEHF320100T0_04() {
		return EHF320100T0_04;
	}

	public void setEHF320100T0_04(String eHF320100T0_04) {
		EHF320100T0_04 = eHF320100T0_04;
	}

	public String getEHF320100T0_05() {
		return EHF320100T0_05;
	}

	public void setEHF320100T0_05(String eHF320100T0_05) {
		EHF320100T0_05 = eHF320100T0_05;
	}

	public String getEHF320100T0_06() {
		return EHF320100T0_06;
	}

	public void setEHF320100T0_06(String eHF320100T0_06) {
		EHF320100T0_06 = eHF320100T0_06;
	}

	public String getEHF320100T0_07() {
		return EHF320100T0_07;
	}

	public void setEHF320100T0_07(String eHF320100T0_07) {
		EHF320100T0_07 = eHF320100T0_07;
	}

	public String getEHF320100T0_08() {
		return EHF320100T0_08;
	}

	public void setEHF320100T0_08(String eHF320100T0_08) {
		EHF320100T0_08 = eHF320100T0_08;
	}

	public String getEHF320100T0_04_TXT() {
		return EHF320100T0_04_TXT;
	}

	public void setEHF320100T0_04_TXT(String eHF320100T0_04TXT) {
		EHF320100T0_04_TXT = eHF320100T0_04TXT;
	}

	public String getEHF320100T0_05_TXT() {
		return EHF320100T0_05_TXT;
	}

	public void setEHF320100T0_05_TXT(String eHF320100T0_05TXT) {
		EHF320100T0_05_TXT = eHF320100T0_05TXT;
	}

	public String getEHF320100T1_01() {
		return EHF320100T1_01;
	}

	public void setEHF320100T1_01(String eHF320100T1_01) {
		EHF320100T1_01 = eHF320100T1_01;
	}

	public int getEHF320100T1_02() {
		return EHF320100T1_02;
	}

	public void setEHF320100T1_02(int eHF320100T1_02) {
		EHF320100T1_02 = eHF320100T1_02;
	}

	public String getEHF320100T1_03() {
		return EHF320100T1_03;
	}

	public void setEHF320100T1_03(String eHF320100T1_03) {
		EHF320100T1_03 = eHF320100T1_03;
	}

	public String getEHF320100T1_03_TXT() {
		return EHF320100T1_03_TXT;
	}

	public void setEHF320100T1_03_TXT(String eHF320100T1_03TXT) {
		EHF320100T1_03_TXT = eHF320100T1_03TXT;
	}

	public String getEHF320100T2_01() {
		return EHF320100T2_01;
	}

	public void setEHF320100T2_01(String eHF320100T2_01) {
		EHF320100T2_01 = eHF320100T2_01;
	}

	public int getEHF320100T2_02() {
		return EHF320100T2_02;
	}

	public void setEHF320100T2_02(int eHF320100T2_02) {
		EHF320100T2_02 = eHF320100T2_02;
	}

	public String getEHF320100T2_03() {
		return EHF320100T2_03;
	}

	public void setEHF320100T2_03(String eHF320100T2_03) {
		EHF320100T2_03 = eHF320100T2_03;
	}

	public String getEHF320100T2_03_TXT() {
		return EHF320100T2_03_TXT;
	}

	public void setEHF320100T2_03_TXT(String eHF320100T2_03TXT) {
		EHF320100T2_03_TXT = eHF320100T2_03TXT;
	}

	public String getEHF320100T3_01() {
		return EHF320100T3_01;
	}

	public void setEHF320100T3_01(String eHF320100T3_01) {
		EHF320100T3_01 = eHF320100T3_01;
	}

	public int getEHF320100T3_02() {
		return EHF320100T3_02;
	}

	public void setEHF320100T3_02(int eHF320100T3_02) {
		EHF320100T3_02 = eHF320100T3_02;
	}

	public String getEHF320100T3_03() {
		return EHF320100T3_03;
	}

	public void setEHF320100T3_03(String eHF320100T3_03) {
		EHF320100T3_03 = eHF320100T3_03;
	}

	public String getEHF320100T3_04() {
		return EHF320100T3_04;
	}

	public void setEHF320100T3_04(String eHF320100T3_04) {
		EHF320100T3_04 = eHF320100T3_04;
	}

	public String getEHF320100T3_05() {
		return EHF320100T3_05;
	}

	public void setEHF320100T3_05(String eHF320100T3_05) {
		EHF320100T3_05 = eHF320100T3_05;
	}

	public String getEHF320100T3_06() {
		return EHF320100T3_06;
	}

	public void setEHF320100T3_06(String eHF320100T3_06) {
		EHF320100T3_06 = eHF320100T3_06;
	}

	public String getEHF320100T3_07() {
		return EHF320100T3_07;
	}

	public void setEHF320100T3_07(String eHF320100T3_07) {
		EHF320100T3_07 = eHF320100T3_07;
	}

	public String getEHF320100T3_03_TXT() {
		return EHF320100T3_03_TXT;
	}

	public void setEHF320100T3_03_TXT(String eHF320100T3_03TXT) {
		EHF320100T3_03_TXT = eHF320100T3_03TXT;
	}

	public String getEHF320100T3_04_TXT() {
		return EHF320100T3_04_TXT;
	}

	public void setEHF320100T3_04_TXT(String eHF320100T3_04TXT) {
		EHF320100T3_04_TXT = eHF320100T3_04TXT;
	}

	public String getEHF320100T3_05_TXT() {
		return EHF320100T3_05_TXT;
	}

	public void setEHF320100T3_05_TXT(String eHF320100T3_05TXT) {
		EHF320100T3_05_TXT = eHF320100T3_05TXT;
	}

	public String getEHF320100T3_06_TXT() {
		return EHF320100T3_06_TXT;
	}

	public void setEHF320100T3_06_TXT(String eHF320100T3_06TXT) {
		EHF320100T3_06_TXT = eHF320100T3_06TXT;
	}

	public String getEHF320100T4_01() {
		return EHF320100T4_01;
	}

	public void setEHF320100T4_01(String eHF320100T4_01) {
		EHF320100T4_01 = eHF320100T4_01;
	}

	public int getEHF320100T4_02() {
		return EHF320100T4_02;
	}

	public void setEHF320100T4_02(int eHF320100T4_02) {
		EHF320100T4_02 = eHF320100T4_02;
	}

	public String getEHF320100T4_03() {
		return EHF320100T4_03;
	}

	public void setEHF320100T4_03(String eHF320100T4_03) {
		EHF320100T4_03 = eHF320100T4_03;
	}

	public String getEHF320100T4_03_SHOW() {
		return EHF320100T4_03_SHOW;
	}

	public void setEHF320100T4_03_SHOW(String eHF320100T4_03SHOW) {
		EHF320100T4_03_SHOW = eHF320100T4_03SHOW;
	}

	public String getEHF320100T4_03_TXT() {
		return EHF320100T4_03_TXT;
	}

	public void setEHF320100T4_03_TXT(String eHF320100T4_03TXT) {
		EHF320100T4_03_TXT = eHF320100T4_03TXT;
	}

	public String getEHF320100T5_01() {
		return EHF320100T5_01;
	}

	public void setEHF320100T5_01(String eHF320100T5_01) {
		EHF320100T5_01 = eHF320100T5_01;
	}

	public int getEHF320100T5_02() {
		return EHF320100T5_02;
	}

	public void setEHF320100T5_02(int eHF320100T5_02) {
		EHF320100T5_02 = eHF320100T5_02;
	}

	public String getEHF320100T5_03() {
		return EHF320100T5_03;
	}

	public void setEHF320100T5_03(String eHF320100T5_03) {
		EHF320100T5_03 = eHF320100T5_03;
	}

	public String getEHF320100T5_03_SHOW() {
		return EHF320100T5_03_SHOW;
	}

	public void setEHF320100T5_03_SHOW(String eHF320100T5_03SHOW) {
		EHF320100T5_03_SHOW = eHF320100T5_03SHOW;
	}

	public String getEHF320100T5_03_TXT() {
		return EHF320100T5_03_TXT;
	}

	public void setEHF320100T5_03_TXT(String eHF320100T5_03TXT) {
		EHF320100T5_03_TXT = eHF320100T5_03TXT;
	}

	public String getUSER_CREATE() {
		return USER_CREATE;
	}

	public void setUSER_CREATE(String uSERCREATE) {
		USER_CREATE = uSERCREATE;
	}

	public String getUSER_UPDATE() {
		return USER_UPDATE;
	}

	public void setUSER_UPDATE(String uSERUPDATE) {
		USER_UPDATE = uSERUPDATE;
	}

	public int getVERSION() {
		return VERSION;
	}

	public void setVERSION(int vERSION) {
		VERSION = vERSION;
	}

	public String getDATE_CREATE() {
		return DATE_CREATE;
	}

	public void setDATE_CREATE(String dATECREATE) {
		DATE_CREATE = dATECREATE;
	}

	public String getDATE_UPDATE() {
		return DATE_UPDATE;
	}

	public void setDATE_UPDATE(String dATEUPDATE) {
		DATE_UPDATE = dATEUPDATE;
	}

	public List getEHF320100T0_LIST() {
		return EHF320100T0_LIST;
	}

	public void setEHF320100T0_LIST(List eHF320100T0LIST) {
		EHF320100T0_LIST = eHF320100T0LIST;
	}

	public List getEHF320100T3_DETAIL() {
		return EHF320100T3_DETAIL;
	}

	public void setEHF320100T3_DETAIL(List eHF320100T3DETAIL) {
		EHF320100T3_DETAIL = eHF320100T3DETAIL;
	}

	public List getEHF320100T1_DETAIL() {
		return EHF320100T1_DETAIL;
	}

	public void setEHF320100T1_DETAIL(List eHF320100T1DETAIL) {
		EHF320100T1_DETAIL = eHF320100T1DETAIL;
	}

	public List getEHF320100T2_DETAIL() {
		return EHF320100T2_DETAIL;
	}

	public void setEHF320100T2_DETAIL(List eHF320100T2DETAIL) {
		EHF320100T2_DETAIL = eHF320100T2DETAIL;
	}

	public List getEHF320100T4_DETAIL() {
		return EHF320100T4_DETAIL;
	}

	public void setEHF320100T4_DETAIL(List eHF320100T4DETAIL) {
		EHF320100T4_DETAIL = eHF320100T4DETAIL;
	}

	public List getEHF320100T5_DETAIL() {
		return EHF320100T5_DETAIL;
	}

	public void setEHF320100T5_DETAIL(List eHF320100T5DETAIL) {
		EHF320100T5_DETAIL = eHF320100T5DETAIL;
	}

	public void setEHF320100T0_06_TXT(String eHF320100T0_06_TXT) {
		EHF320100T0_06_TXT = eHF320100T0_06_TXT;
	}

	public String getEHF320100T0_06_TXT() {
		return EHF320100T0_06_TXT;
	}

	public void setEHF320100T0_07_TXT(String eHF320100T0_07_TXT) {
		EHF320100T0_07_TXT = eHF320100T0_07_TXT;
	}

	public String getEHF320100T0_07_TXT() {
		return EHF320100T0_07_TXT;
	}

}
