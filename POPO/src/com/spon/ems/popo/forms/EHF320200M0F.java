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
import org.apache.commons.validator.GenericValidator;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;



import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.ems.popo.models.EHF320200;
import com.spon.utils.util.BA_Vaildate;

/**
 * <Form>EMS讀取VIEWDATA的Form
 * @version 1.0
 * @created 25-四月-2011 下午 02:26:36
 */
public class EHF320200M0F extends ActionForm implements ValidateForm {
	
	private String  KEY_01C02C;	//取KEY的值(EHF320200T0_01+"/"+EHF320200T0_02)
	private String EHF320200T0_01;	//上菜順序天
	private String EHF320200T0_02;	//上菜順序項次
	private String EHF320200T0_01C; //collection的上菜順序天
	private String EHF320200T0_02C; //collection的上菜順序項次
	private String EHF320200T0_03; //菜單編號
	private String EHF320200T0_03_TXT; //collection的菜單名稱
	private String EHF320200T0_04;	//公司代碼
	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_UPDATE;
	private String DATE_CREATE;
	
	private String EHF320100T0_01; //系統編號
	private String EHF320100T0_02; //菜單編號
	private String EHF320100T0_04; //菜單餐別
	private String EHF320100T0_05; //菜單類別
	private List EHF320200_C  = new ArrayList(); //JSP0的collection的list

	
	private String EHF320200T1_01;	//JSP1的上菜順序天
	private String EHF320200T1_02;	//JSP1的上菜順序項次
	private String EHF320200T1_03;	//JSP1的菜單編號
	private String EHF320200T1_03_TXT;	//JSP1的菜單名稱

	private String space;

	
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
					ve.isEmpty(l_actionErrors, EHF320200T1_01, "EHF320200T1_01","不可空白");
					if((!"".equals(getEHF320200T1_01()) && getEHF320200T1_01()!=null) && !GenericValidator.maxLength(getEHF320200T1_01(), 2)){//
						l_actionErrors.add("EHF320200T1_01", new ActionMessage(""));
						request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"上菜順序天字數不得超過2個字!!");
					}
					
					ve.isEmpty(l_actionErrors, EHF320200T1_02, "EHF320200T1_02","不可空白");
					if((!"".equals(getEHF320200T1_02()) && getEHF320200T1_02()!=null) && !GenericValidator.maxLength(getEHF320200T1_02(), 2)){//
						l_actionErrors.add("EHF320200T1_02", new ActionMessage(""));
						request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"上菜順序項次字數不得超過2個字!!");
					}
					
					ve.isEmpty(l_actionErrors, EHF320200T1_03_TXT, "EHF320200T1_03_TXT","不可空白");
					addData_validate(l_actionErrors, request, conn);
				}
				
				//修改時判斷條件
				if (request.getAttribute("action").equals("saveData")) {
					
					saveData_validate(l_actionErrors, request, conn);
				}
				
				//刪除時判斷條件
				if (request.getAttribute("action").equals("delData")) {
					
					
					delData_validate(l_actionErrors, request, conn);
				}
				
				
				
			}
		 return l_actionErrors;
	}
	
	
	private void delData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		String arrid[] = request.getParameterValues("checkId");
		String comp_id = (String) request.getAttribute("compid");
		
		if(arrid!=null){      //判斷checkbox是否有選
			
			try{                  //判斷是否使用中
				Statement stmt = conn.createStatement();
				String sql = "SELECT EHF320300T0_03 FROM EHF320300T0 WHERE EHF320300T0_03 = '"
						+ arrid[0] + "' and EHF320300T0_04 = '"+request.getAttribute("compid")+"'";
				ResultSet rs = stmt.executeQuery(sql);
				if (rs.next()) {
					errors.add("", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"刪除失敗，上菜順序" +arrid[0]+ "為使用中，不可刪除!!");
				}
				stmt.close();
				rs.close();
			}catch (SQLException e) {
				System.out.println("EHF320200M0F.addData_validate()" + e);
			}
		
			
		
		}else{
			request.setAttribute("ERR_MSG", "請先選擇一筆要刪除的資料!!");
		}
		
	}
	
	private void deleteFOODT1_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
	}
	

	private void saveData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
	
	}
	
	
	private void addData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		try{
			//判斷同一天的上菜順序項次是否重複
			Statement stmt = conn.createStatement();
			String sql = "SELECT EHF320200T0_02 FROM EHF320200T0 WHERE EHF320200T0_01 = '"
					+ EHF320200T1_01 + "' and EHF320200T0_02 = '" 
					+ EHF320200T1_02 + "' and EHF320200T0_04 = '"
					+request.getAttribute("compid")+"'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				errors.add("EHF320200T1_02", new ActionMessage(""));
				request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"同一天的上菜順序項次不可重複!!");
			}
			stmt.close();
			rs.close();
			
		}catch (SQLException e) {
			System.out.println("EHF320200M0F.addData_validate()" + e);
		}
		
	}
	
	
	private void addDetailData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub

	}



	public void setEHF320200T0_03(String EHF320200T0_03) {
		EHF320200T0_03 = EHF320200T0_03;
	}



	public String getEHF320200T0_03() {
		return EHF320200T0_03;
	}



	public void setEHF320200T0_04(String EHF320200T0_04) {
		EHF320200T0_04 = EHF320200T0_04;
	}



	public String getEHF320200T0_04() {
		return EHF320200T0_04;
	}





	public void setUSER_CREATE(String uSER_CREATE) {
		USER_CREATE = uSER_CREATE;
	}



	public String getUSER_CREATE() {
		return USER_CREATE;
	}



	public void setUSER_UPDATE(String uSER_UPDATE) {
		USER_UPDATE = uSER_UPDATE;
	}



	public String getUSER_UPDATE() {
		return USER_UPDATE;
	}



	public void setVERSION(int vERSION) {
		VERSION = vERSION;
	}



	public int getVERSION() {
		return VERSION;
	}



	public void setDATE_UPDATE(String dATE_UPDATE) {
		DATE_UPDATE = dATE_UPDATE;
	}



	public String getDATE_UPDATE() {
		return DATE_UPDATE;
	}


////
	
	



	public void setSpace(String space) {
		this.space = space;
	}



	public String getSpace() {
		return space;
	}



	public void setEnable_list(boolean b) {
		// TODO Auto-generated method stub
		
	}
	

	public void setEHF320200T0_03_TXT(String eHF320200T0_03_TXT) {
		EHF320200T0_03_TXT = eHF320200T0_03_TXT;
	}


	public String getEHF320200T0_03_TXT() {
		return EHF320200T0_03_TXT;
	}


	public void setDATE_CREATE(String dATE_CREATE) {
		DATE_CREATE = dATE_CREATE;
	}


	public String getDATE_CREATE() {
		return DATE_CREATE;
	}


	public void setEHF320100T0_01(String eHF320100T0_01) {
		EHF320100T0_01 = eHF320100T0_01;
	}


	public String getEHF320100T0_01() {
		return EHF320100T0_01;
	}


	public void setEHF320100T0_04(String eHF320100T0_04) {
		EHF320100T0_04 = eHF320100T0_04;
	}


	public String getEHF320100T0_04() {
		return EHF320100T0_04;
	}


	public void setEHF320200_C(List eHF320200_C) {
		EHF320200_C = eHF320200_C;
	}


	public List getEHF320200_C() {
		return EHF320200_C;
	}


	public void setEHF320100T0_02(String eHF320100T0_02) {
		EHF320100T0_02 = eHF320100T0_02;
	}


	public String getEHF320100T0_02() {
		return EHF320100T0_02;
	}


	public void setEHF320200T0_01C(String eHF320200T0_01C) {
		EHF320200T0_01C = eHF320200T0_01C;
	}


	public String getEHF320200T0_01C() {
		return EHF320200T0_01C;
	}


	public void setEHF320200T0_02C(String eHF320200T0_02C) {
		EHF320200T0_02C = eHF320200T0_02C;
	}


	public String getEHF320200T0_02C() {
		return EHF320200T0_02C;
	}


	public void setEHF320200T1_01(String eHF320200T1_01) {
		EHF320200T1_01 = eHF320200T1_01;
	}


	public String getEHF320200T1_01() {
		return EHF320200T1_01;
	}


	public void setEHF320200T1_02(String eHF320200T1_02) {
		EHF320200T1_02 = eHF320200T1_02;
	}


	public String getEHF320200T1_02() {
		return EHF320200T1_02;
	}


	public void setEHF320200T1_03_TXT(String eHF320200T1_03_TXT) {
		EHF320200T1_03_TXT = eHF320200T1_03_TXT;
	}


	public String getEHF320200T1_03_TXT() {
		return EHF320200T1_03_TXT;
	}


	public void setEHF320200T1_03(String eHF320200T1_03) {
		EHF320200T1_03 = eHF320200T1_03;
	}


	public String getEHF320200T1_03() {
		return EHF320200T1_03;
	}


	public void setEHF320200T0_01(String eHF320200T0_01) {
		EHF320200T0_01 = eHF320200T0_01;
	}


	public String getEHF320200T0_01() {
		return EHF320200T0_01;
	}


	public void setEHF320200T0_02(String eHF320200T0_02) {
		EHF320200T0_02 = eHF320200T0_02;
	}


	public String getEHF320200T0_02() {
		return EHF320200T0_02;
	}


	public void setEHF320100T0_05(String eHF320100T0_05) {
		EHF320100T0_05 = eHF320100T0_05;
	}


	public String getEHF320100T0_05() {
		return EHF320100T0_05;
	}


	public void setKEY_01C02C(String kEY_01C02C) {
		KEY_01C02C = kEY_01C02C;
	}


	public String getKEY_01C02C() {
		return KEY_01C02C;
	}


	
}
