package com.spon.ems.com.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;

public class EHF000200M0F extends ActionForm implements ValidateForm {
	
	private String EHF000200T0_01;
	private String EHF000200T0_02;
	private String EHF000200T0_03;
	private String EHF000200T0_04;//上層系統代碼(系統使用)
	private String EHF000200T0_04_1;//上層部門代號(使用者自訂)
	private String EHF000200T0_05;
	private String EHF000200T0_06;
	private String EHF000200T0_07;
	private String EHF000200T0_08;
	private String EHF000200T0_09;
	private String EHF000200T0_10;
	private String HR_CompanySysNo;
	
	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_CREATE;
	private String HR_LastUpdateDate;
	
	private List EHF000200T0_LIST = new ArrayList();

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
				
				ve.isEmpty(l_actionErrors, EHF000200T0_02, "EHF000200T0_02", "不可空白");  //
				ve.isEmpty(l_actionErrors, EHF000200T0_03, "EHF000200T0_03", "不可空白");  //
				ve.isEmpty(l_actionErrors, EHF000200T0_04, "EHF000200T0_04", "不可空白");  //
				ve.isEmpty(l_actionErrors, EHF000200T0_08, "EHF000200T0_08", "不可空白");  //
				
				if((!"".equals(getEHF000200T0_02()) && getEHF000200T0_02()!=null) && !GenericValidator.maxLength(getEHF000200T0_02(), 10)){//
					l_actionErrors.add("EHF000200T0_02", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"部門代碼字數不得超過10個字!!");
				}
				if((!"".equals(getEHF000200T0_03()) && getEHF000200T0_03()!=null) && !GenericValidator.maxLength(getEHF000200T0_03(), 200)){//
					l_actionErrors.add("EHF000200T0_03", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"部門名稱字數不得超過200個字!!");
				}
				if((!"".equals(getEHF000200T0_07()) && getEHF000200T0_07()!=null) && !GenericValidator.maxLength(getEHF000200T0_07(), 20)){//
					l_actionErrors.add("EHF000200T0_07", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"部門簡稱字數不得超過20個字!!");
				}
				if((!"".equals(getEHF000200T0_10()) && getEHF000200T0_10()!=null) && !GenericValidator.maxLength(getEHF000200T0_10(), 50)){//
					l_actionErrors.add("EHF000200T0_10", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"部門簡介字數不得超過50個字!!");
				}
								
				addData_validate(l_actionErrors, request, conn);
			}
			
			//新增明細時判斷條件
			if (request.getAttribute("action").equals("addDetailData")) {
				
				addDetailData_validate(l_actionErrors, request, conn);
			}

			//修改時判斷條件
			if (request.getAttribute("action").equals("saveData")) {
				
				ve.isEmpty(l_actionErrors, EHF000200T0_02, "EHF000200T0_02", "不可空白");  //
				ve.isEmpty(l_actionErrors, EHF000200T0_03, "EHF000200T0_03", "不可空白");  //
				ve.isEmpty(l_actionErrors, EHF000200T0_08, "EHF000200T0_08", "不可空白");  //
				
				if((!"".equals(getEHF000200T0_02()) && getEHF000200T0_02()!=null) && !GenericValidator.maxLength(getEHF000200T0_02(), 10)){//
					l_actionErrors.add("EHF000200T0_02", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"部門代碼字數不得超過10個字!!");
				}
				if((!"".equals(getEHF000200T0_03()) && getEHF000200T0_03()!=null) && !GenericValidator.maxLength(getEHF000200T0_03(), 200)){//
					l_actionErrors.add("EHF000200T0_03", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"部門名稱字數不得超過200個字!!");
				}
				if((!"".equals(getEHF000200T0_07()) && getEHF000200T0_07()!=null) && !GenericValidator.maxLength(getEHF000200T0_07(), 20)){//
					l_actionErrors.add("EHF000200T0_07", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"部門簡稱字數不得超過20個字!!");
				}
				if((!"".equals(getEHF000200T0_10()) && getEHF000200T0_10()!=null) && !GenericValidator.maxLength(getEHF000200T0_10(), 50)){//
					l_actionErrors.add("EHF000200T0_10", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"部門簡介字數不得超過50個字!!");
				}

				saveData_validate(l_actionErrors, request, conn);
			}
			
			//刪除時判斷條件
			if (request.getAttribute("action").equals("delData")) {
				
				delData_validate(l_actionErrors, request, conn);
			}
			
			//
			/*if (request.getAttribute("action").equals("deleteEHF010108T0")) {
				
				deleteEHF010108T0_validate(l_actionErrors, request, conn);
			}*/
			
		}
		
		return l_actionErrors;
	}
	
	private void delData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		BA_TOOLS tool = BA_TOOLS.getInstance();
		
		try{
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM EHF000200T0 WHERE EHF000200T0_04 = '"
					+ EHF000200T0_01 + "' and HR_CompanySysNo = '"+request.getAttribute("compid")+"'";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.next()) {				
				errors.add("EHF000200T0_01", new ActionMessage(""));
				request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?
									"":request.getAttribute("ERR_MSG")+"<br>")+"該部門還有轄下部門，請勿刪除!!");				
			}
			
			rs.close();
			stmt.close();
			
		}catch (SQLException e) {
			System.out.println("EHF010108M0F.delData_validate()" + e);
		}
		
		try{
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM EHF010100T6 WHERE EHF010100T6_05 = '" + EHF000200T0_01 + "' " +
						 "and '"+tool.ymdTostring(tool.getSysDateYYMMDD())+"' between DATE_FORMAT(EHF010100T6_03, '%Y/%m/%d') and DATE_FORMAT(EHF010100T6_04, '%Y/%m/%d')" +
						 "and HR_CompanySysNo = '"+request.getAttribute("compid")+"'";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.next()) {				
				errors.add("EHF000200T0_01", new ActionMessage(""));
				request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?
									"":request.getAttribute("ERR_MSG")+"<br>")+"尚有員工歸屬該部門，請勿刪除!!");				
			}
			
			rs.close();
			stmt.close();
			
		}catch (SQLException e) {
			System.out.println("EHF010108M0F.delData_validate()" + e);
		}
		
	}

	private void saveData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		String sdate[]=EHF000200T0_08.split("/");
		String edate[]=EHF000200T0_09.split("/");
		
		String OKsdate=sdate[0]+"-"+sdate[1]+"-"+sdate[2];
		String OKedate=edate[0]+"-"+edate[1]+"-"+edate[2];
		
		if(!compareDate(OKsdate,OKedate)){
			errors.add("EHF000200T0_08", new ActionMessage(""));
			errors.add("EHF000200T0_09", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?
								"":request.getAttribute("ERR_MSG")+"<br>")+"日期起訖錯誤!!");

		}
		
	}

	private void addDetailData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
	}

	private void addData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		try{
			Statement stmt = conn.createStatement();
			
			String sql = " SELECT EHF000200T0_02 FROM EHF000200T0 WHERE EHF000200T0_02 = '"+EHF000200T0_02+"' " +
						 " and HR_CompanySysNo = '"+request.getAttribute("compid")+"' ";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				errors.add("EHF000200T0_02", new ActionMessage(""));
				request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?
									"":request.getAttribute("ERR_MSG")+"<br>")+"部門代碼已重複，請重新填寫!!");
			}
			rs.close();
			stmt.close();
			
			
			String sdate[]=EHF000200T0_08.split("/");
			String edate[]=EHF000200T0_09.split("/");
			
			String OKsdate=sdate[0]+"-"+sdate[1]+"-"+sdate[2];
			String OKedate=edate[0]+"-"+edate[1]+"-"+edate[2];
			
			if(!compareDate(OKsdate,OKedate)){
				errors.add("EHF000200T0_08", new ActionMessage(""));
				errors.add("EHF000200T0_09", new ActionMessage(""));
				request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?
									"":request.getAttribute("ERR_MSG")+"<br>")+"日期起訖錯誤!!");

			}
			
			
			
		}catch (SQLException e) {
			System.out.println("EHF010108M0F.addData_validate()" + e);
		}
		
	}
	
	
	

	
	public boolean compareDate(String startDate, String endDate) {

	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	    try {

	        Date sDate = sdf.parse(startDate);

	        Date eDate = sdf.parse(endDate);

	        if (eDate.before(sDate)) {

	            return false;

	        } else {

	            return true;

	        }

	    } catch (ParseException e) {

	        e.printStackTrace();

	    }

	    return false;

	}
	
	

	public String getEHF000200T0_01() {
		return EHF000200T0_01;
	}

	public void setEHF000200T0_01(String eHF000200T0_01) {
		EHF000200T0_01 = eHF000200T0_01;
	}

	public String getEHF000200T0_02() {
		return EHF000200T0_02;
	}

	public void setEHF000200T0_02(String eHF000200T0_02) {
		EHF000200T0_02 = eHF000200T0_02;
	}

	public String getEHF000200T0_03() {
		return EHF000200T0_03;
	}

	public void setEHF000200T0_03(String eHF000200T0_03) {
		EHF000200T0_03 = eHF000200T0_03;
	}

	public String getEHF000200T0_04() {
		return EHF000200T0_04;
	}

	public void setEHF000200T0_04(String eHF000200T0_04) {
		EHF000200T0_04 = eHF000200T0_04;
	}

	public String getEHF000200T0_04_1() {
		return EHF000200T0_04_1;
	}

	public void setEHF000200T0_04_1(String eHF000200T0_04_1) {
		EHF000200T0_04_1 = eHF000200T0_04_1;
	}

	public String getEHF000200T0_05() {
		return EHF000200T0_05;
	}

	public void setEHF000200T0_05(String eHF000200T0_05) {
		EHF000200T0_05 = eHF000200T0_05;
	}

	public String getEHF000200T0_06() {
		return EHF000200T0_06;
	}

	public void setEHF000200T0_06(String eHF000200T0_06) {
		EHF000200T0_06 = eHF000200T0_06;
	}

	public String getEHF000200T0_07() {
		return EHF000200T0_07;
	}

	public void setEHF000200T0_07(String eHF000200T0_07) {
		EHF000200T0_07 = eHF000200T0_07;
	}

	public String getEHF000200T0_08() {
		return EHF000200T0_08;
	}

	public void setEHF000200T0_08(String eHF000200T0_08) {
		EHF000200T0_08 = eHF000200T0_08;
	}

	public String getEHF000200T0_09() {
		return EHF000200T0_09;
	}

	public void setEHF000200T0_09(String eHF000200T0_09) {
		EHF000200T0_09 = eHF000200T0_09;
	}

	public String getEHF000200T0_10() {
		return EHF000200T0_10;
	}

	public void setEHF000200T0_10(String eHF000200T0_10) {
		EHF000200T0_10 = eHF000200T0_10;
	}

	public String getHR_CompanySysNo() {
		return HR_CompanySysNo;
	}

	public void setHR_CompanySysNo(String hRCompanySysNo) {
		HR_CompanySysNo = hRCompanySysNo;
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

	public String getHR_LastUpdateDate() {
		return HR_LastUpdateDate;
	}

	public void setHR_LastUpdateDate(String hRLastUpdateDate) {
		HR_LastUpdateDate = hRLastUpdateDate;
	}

	public List getEHF000200T0_LIST() {
		return EHF000200T0_LIST;
	}

	public void setEHF000200T0_LIST(List eHF000200T0LIST) {
		EHF000200T0_LIST = eHF000200T0LIST;
	}

}
