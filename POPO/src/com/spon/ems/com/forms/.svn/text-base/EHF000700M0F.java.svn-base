package com.spon.ems.com.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.ems.com.models.EHF000300;
import com.spon.ems.com.models.EHF000700;
import com.spon.ems.vacation.tools.EMS_CaculateVacationByCal;
import com.spon.ems.vacation.tools.EMS_CheckVacation;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;
import com.spon.utils.util.EMS_GetEmsFlowInf;

/**
 *@author maybe
 *@version 1.0
 *@created 2015/11/13 上午9:46:59
 */
public class EHF000700M0F extends ActionForm implements ValidateForm {
	
	private  String  EHF000700T0_01;
	private  String  EHF000700T0_02;
	private  String  EHF000700T0_03;
	private  String  EHF000700T0_04;
	private  String  EHF000700T0_05;
	private  String  USER_CREATE;
	private  String  USER_UPDATE;
	private   int  VERSION;
	private  String  DATE_UPDATE;
	private  String  DATE_CREATE;
	private List EHF000700C = new ArrayList();
	private FormFile UPLOADFILE;
	private String SIGN_COMMENT;
	private String HR_CompanySysNo;
	
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		List flow_sign_log_list = new ArrayList();
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		if (l_actionErrors.isEmpty()) {
			
			EMS_CheckVacation checkva_tools = (EMS_CheckVacation) EMS_CheckVacation.getInstance();
			
			//新增時判斷條件
			if (request.getAttribute("action").equals("addData")) {
				
				ve.isEmpty(l_actionErrors, EHF000700T0_02, "EHF000700T0_02", "不可空白");
				addData_validate(l_actionErrors, request, conn);
			}

			//修改時判斷條件
			if (request.getAttribute("action").equals("saveData")) {
				ve.isEmpty(l_actionErrors, EHF000700T0_02, "EHF000700T0_02", "不可空白");
				
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
	}

	private void saveData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		BA_Vaildate ve = BA_Vaildate.getInstance();
		ve.isEmpty(errors, EHF000700T0_02, "EHF000300T0_02", "不可空白");
	}

	private void addData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		BA_Vaildate ve = BA_Vaildate.getInstance();
		ve.isEmpty(errors, EHF000700T0_02, "EHF000700T0_02", "不可空白");
		
		try{
			String comp_id = (String) request.getAttribute("compid");
			
			int CountLimit = Integer.parseInt("".equals(tools.getSysParam(conn, comp_id, "TRAILCOUNTLIMIT"))?
					"0":tools.getSysParam(conn, comp_id, "TRAILCOUNTLIMIT"));
			
			if(CountLimit > 0){
				//試用版限制只能新增至n筆
				//若TRAILCOUNTLIMIT = 0, 則為正常版
				String sql = "SELECT COUNT(*) FROM EHF000700T0";
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				if(rs.next()){
					if(!(Integer.parseInt(rs.getString("COUNT(*)")) < CountLimit)){
						errors.add("EHF000700T0_01",new ActionMessage(""));
						request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":
							request.getAttribute("MSG")+"<br>")+"此版本為試用版本, 無法繼續新增請假單, 請洽詢您的經銷商做處理");
					}
				}
				rs.close();
				stmt.close();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String getEHF000700T0_01() {
		return EHF000700T0_01;
	}

	public void setEHF000700T0_01(String eHF000700T0_01) {
		EHF000700T0_01 = eHF000700T0_01;
	}

	public String getEHF000700T0_02() {
		return EHF000700T0_02;
	}

	public void setEHF000700T0_02(String eHF000700T0_02) {
		EHF000700T0_02 = eHF000700T0_02;
	}

	public String getEHF000700T0_03() {
		return EHF000700T0_03;
	}

	public void setEHF000700T0_03(String eHF000700T0_03) {
		EHF000700T0_03 = eHF000700T0_03;
	}

	
	public String getEHF000700T0_04() {
		return EHF000700T0_04;
	}

	public void setEHF000700T0_04(String eHF000700T0_04) {
		EHF000700T0_04 = eHF000700T0_04;
	}

	public String getEHF000700T0_05() {
		return EHF000700T0_05;
	}

	public void setEHF000700T0_05(String eHF000700T0_05) {
		EHF000700T0_05 = eHF000700T0_05;
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

	public String getDATE_UPDATE() {
		return DATE_UPDATE;
	}

	public void setDATE_UPDATE(String dATEUPDATE) {
		DATE_UPDATE = dATEUPDATE;
	}

	public List getEHF000700C() {
		return EHF000700C;
	}

	public void setEHF000700C(List eHF000700C) {
		EHF000700C = eHF000700C;
	}

	public void setUPLOADFILE(FormFile uPLOADFILE) {
		UPLOADFILE = uPLOADFILE;
	}

	public FormFile getUPLOADFILE() {
		return UPLOADFILE;
	}

	public void setSIGN_COMMENT(String sIGN_COMMENT) {
		SIGN_COMMENT = sIGN_COMMENT;
	}

	public String getSIGN_COMMENT() {
		return SIGN_COMMENT;
	}

	public void setHR_CompanySysNo(String hR_CompanySysNo) {
		HR_CompanySysNo = hR_CompanySysNo;
	}

	public String getHR_CompanySysNo() {
		return HR_CompanySysNo;
	}

	public void setDATE_CREATE(String dATE_CREATE) {
		DATE_CREATE = dATE_CREATE;
	}

	public String getDATE_CREATE() {
		return DATE_CREATE;
	}
	
}
