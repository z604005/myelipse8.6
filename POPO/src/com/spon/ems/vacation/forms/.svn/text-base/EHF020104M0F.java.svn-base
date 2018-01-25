package com.spon.ems.vacation.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.utils.util.BA_Vaildate;

public class EHF020104M0F extends ActionForm implements ValidateForm {
	
	private static final long serialVersionUID = 1L;
	private boolean CHECKED;
	private boolean CHANGED;
	
	private  String  EHF020104T0_01;
	private  String  EHF020104T0_02;
	private  String  EHF020104T0_03;
	private  String  EHF020104T0_04;
	private  String  EHF020104T0_05;
	
	private  String  EHF020104T1_01;
	private  String  EHF020104T1_02;
	private  String  EHF020104T1_02_NUM;
	private  String  EHF020104T1_02_TXT;
	private  String  EHF020104T1_03;
	private  String  EHF020104T1_03_NUM;
	private  String  EHF020104T1_03_TXT;
	private  String  EHF020104T1_04;
	private  String  EHF020104T1_05;

	private  String  USER_CREATE;
	private  String  USER_UPDATE;
	private   int  VERSION;
	private  String  DATE_CREATE;
	private  String  DATE_UPDATE;
	
	private List EHF020104C = new ArrayList();

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(l_actionErrors, EHF020104T0_02, "EHF020104T0_02", "不可空白");	//換班日期
		
		if (l_actionErrors.isEmpty()) {
			
			//新增時判斷條件
			if (request.getAttribute("action").equals("addData")) {
				
				addData_validate(l_actionErrors, request, conn);
			}
			
			//新增明細時判斷條件
			if (request.getAttribute("action").equals("addDetailData")) {
				
				//檢查不可為空白
				ve.isEmpty(l_actionErrors, EHF020104T1_02, "EHF020104T1_02", "不可空白");
				ve.isEmpty(l_actionErrors, EHF020104T1_03, "EHF020104T1_03", "不可空白");
				ve.isEmpty(l_actionErrors, EHF020104T1_04, "EHF020104T1_04", "不可空白");
				ve.isEmpty(l_actionErrors, EHF020104T1_05, "EHF020104T1_05", "不可空白");
				
				addDetailData_validate(l_actionErrors, request, conn);
			}
			
			//	     修改時判斷條件
			if (request.getAttribute("action").equals("saveData")) {
				
				saveData_validate(l_actionErrors, request, conn);
			}
		}

		return l_actionErrors;
	}

	private void saveData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
	}

	private void addDetailData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		try{
			
			Statement stmt = conn.createStatement();
			String sql = "";
			
			sql += " SELECT * FROM EHF020104T0 a" +
				   " LEFT JOIN EHF020104T1  b ON a.EHF020104T0_01 = b.EHF020104T1_01 " +
				   " WHERE 1=1 " +
				  // " AND EHF020104T1_05 = '"+EHF020104T1_05+"' " +  //新班別序號
				   " AND EHF020104T1_02 = '"+EHF020104T1_02+"' " +  //部門
				   " AND EHF020104T1_03 = '"+EHF020104T1_03+"' " +  //員工
				   " AND EHF020104T1_06 = '"+request.getAttribute("compid")+"' " +
				   " AND EHF020104T0_02 = '"+EHF020104T0_02+"'";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				errors.add("EHF020104T1_02",new ActionMessage(""));
				errors.add("EHF020104T1_03",new ActionMessage(""));
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						"該人員已有資料,請檢查序號："+rs.getString("EHF020104T0_01"));
			}
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private void addData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		try{
			
			Statement stmt = conn.createStatement();
			String sql = "";
			
			sql += " SELECT * FROM EHF020104T1 " +
				   " WHERE 1=1 " +
				   " AND EHF020104T1_01 = '"+EHF020104T0_01+"' " +  //預排換班序號
				   " AND EHF020104T1_02 = '"+EHF020104T1_02+"' " +  //部門
				   " AND EHF020104T1_03 = '"+EHF020104T1_03+"' " +  //員工
				   " AND EHF020104T1_06 = '"+request.getAttribute("compid")+"' ";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				errors.add("EHF020104T1_02",new ActionMessage(""));
				errors.add("EHF020104T1_03",new ActionMessage(""));
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						"該人員已有資料,請確認!! ");
			}
			rs.close();
			stmt.close();
			/*String EHF020104T0_02_new[]=EHF020104T0_02.toString().split("/");
			sql = "" +
			" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02" +
			" FROM EHF020900T0 " +
			" WHERE 1=1 " +
			" AND EHF020900T0_M2 = '"+EHF020104T0_02_new[0]+"/"+EHF020104T0_02_new[1]+"'"+	//考勤年月
			" AND EHF020900T0_04 = '"+request.getAttribute("compid")+"'" +//公司代碼
			" AND (EHF020900T0_02 = '02' OR EHF020900T0_02 = '03')"; //已經確認考勤

			Statement stmt_new = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs_new = stmt_new.executeQuery(sql);
			
			while(rs_new.next()){
				if("02".equals(rs_new.getString("EHF020900T0_02"))||"03".equals(rs_new.getString("EHF020900T0_02")))
					errors.add("EHF020104T0_02",new ActionMessage("考勤已確認，不可換班!!"));
				
			}
			rs_new.close();
			stmt_new.close();*/
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("utf-8");
			EHF020104C = ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new EHF020104M0F();
				            }
			 });
		} catch (Exception e) {
		}

	}

	public boolean isCHECKED() {
		return CHECKED;
	}

	public void setCHECKED(boolean cHECKED) {
		CHECKED = cHECKED;
	}

	public boolean isCHANGED() {
		return CHANGED;
	}

	public void setCHANGED(boolean cHANGED) {
		CHANGED = cHANGED;
	}

	public String getEHF020104T0_01() {
		return EHF020104T0_01;
	}

	public void setEHF020104T0_01(String eHF020104T0_01) {
		EHF020104T0_01 = eHF020104T0_01;
	}

	public String getEHF020104T0_02() {
		return EHF020104T0_02;
	}

	public void setEHF020104T0_02(String eHF020104T0_02) {
		EHF020104T0_02 = eHF020104T0_02;
	}

	public String getEHF020104T0_03() {
		return EHF020104T0_03;
	}

	public void setEHF020104T0_03(String eHF020104T0_03) {
		EHF020104T0_03 = eHF020104T0_03;
	}

	public String getEHF020104T0_04() {
		return EHF020104T0_04;
	}

	public void setEHF020104T0_04(String eHF020104T0_04) {
		EHF020104T0_04 = eHF020104T0_04;
	}

	public String getEHF020104T0_05() {
		return EHF020104T0_05;
	}

	public void setEHF020104T0_05(String eHF020104T0_05) {
		EHF020104T0_05 = eHF020104T0_05;
	}

	public String getEHF020104T1_01() {
		return EHF020104T1_01;
	}

	public void setEHF020104T1_01(String eHF020104T1_01) {
		EHF020104T1_01 = eHF020104T1_01;
	}

	public String getEHF020104T1_02() {
		return EHF020104T1_02;
	}

	public void setEHF020104T1_02(String eHF020104T1_02) {
		EHF020104T1_02 = eHF020104T1_02;
	}

	public String getEHF020104T1_02_NUM() {
		return EHF020104T1_02_NUM;
	}

	public void setEHF020104T1_02_NUM(String eHF020104T1_02NUM) {
		EHF020104T1_02_NUM = eHF020104T1_02NUM;
	}

	public String getEHF020104T1_02_TXT() {
		return EHF020104T1_02_TXT;
	}

	public void setEHF020104T1_02_TXT(String eHF020104T1_02TXT) {
		EHF020104T1_02_TXT = eHF020104T1_02TXT;
	}

	public String getEHF020104T1_03() {
		return EHF020104T1_03;
	}

	public void setEHF020104T1_03(String eHF020104T1_03) {
		EHF020104T1_03 = eHF020104T1_03;
	}

	public String getEHF020104T1_03_NUM() {
		return EHF020104T1_03_NUM;
	}

	public void setEHF020104T1_03_NUM(String eHF020104T1_03NUM) {
		EHF020104T1_03_NUM = eHF020104T1_03NUM;
	}

	public String getEHF020104T1_03_TXT() {
		return EHF020104T1_03_TXT;
	}

	public void setEHF020104T1_03_TXT(String eHF020104T1_03TXT) {
		EHF020104T1_03_TXT = eHF020104T1_03TXT;
	}

	public String getEHF020104T1_04() {
		return EHF020104T1_04;
	}

	public void setEHF020104T1_04(String eHF020104T1_04) {
		EHF020104T1_04 = eHF020104T1_04;
	}

	public String getEHF020104T1_05() {
		return EHF020104T1_05;
	}

	public void setEHF020104T1_05(String eHF020104T1_05) {
		EHF020104T1_05 = eHF020104T1_05;
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

	public List getEHF020104C() {
		return EHF020104C;
	}

	public void setEHF020104C(List eHF020104C) {
		EHF020104C = eHF020104C;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
