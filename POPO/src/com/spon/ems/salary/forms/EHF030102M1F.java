package com.spon.ems.salary.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.spon.utils.util.BA_Vaildate;

public class EHF030102M1F extends ActionForm {
	private static final long serialVersionUID = 1L;
	private boolean CHECKED;
	private boolean CHANGED;

	private  String  EHF030102T1_01;
	private  String  EHF030102T1_02;
	private  String  EHF030102T1_03;
	private  String  EHF030102T1_03_TYPE;
	private  String  EHF030102T1_04;
	private  String  EHF030102T1_04_FREQ;
	private  String  EHF030102T1_05;
	private  String  EHF030102T1_06;
	private  String  EHF030102T1_07;
	private  String  EHF030102T1_08;
	private  String  EHF030102T1_09;
	private  String  EHF030102T1_10;
	private  String  EHF030102T1_11;

	private  String  USER_CREATE;
	private  String  USER_UPDATE;
	private   int  VERSION;
	private  String  DATE_UPDATE;

	private List EHF030102C=new ArrayList();

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;
	}
	public ActionErrors validate(ActionMapping mapping,	HttpServletRequest request, Connection conn) {
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		BA_Vaildate ve=BA_Vaildate.getInstance();
		
		ve.isEmpty(l_actionErrors, EHF030102T1_02, "EHF030102T1_02", "不可空白");  //薪資計算公式名稱
		
		
		
		if (l_actionErrors.isEmpty()) {
			
			
			try{
				Statement stmt = conn.createStatement();
				String sql = "";
				
				if(EHF030102T1_02 != null ){
				
					sql += " SELECT * FROM EHF030102T1 " +
						   " WHERE 1=1 " +
						   " AND EHF030102T1_02 = '"+EHF030102T1_02+"' " +
						   " AND EHF030102T1_11 = '"+request.getAttribute("compid")+"' ";
				
					ResultSet rs = stmt.executeQuery(sql);
				
					if(rs.next()){
						if(!(EHF030102T1_01.equals(rs.getString("EHF030102T1_01")))){
							if(EHF030102T1_02.equals(rs.getString("EHF030102T1_02"))){
								l_actionErrors.add("EHF030102T1_02",new ActionMessage(""));
								request.setAttribute("ErrMSG",
										(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
										"薪資計算公式名稱重複,請確認!! ");
							}
						
						}
					rs.close();
					}
				}
				stmt.close();
				
			}catch (SQLException e) {
				System.out.println("EHF030102M1F.addData_validate()" + e);
			}
			
			//新增時判斷條件
			if (request.getAttribute("action").equals("addData")) {
				
				addData_validate(l_actionErrors, request, conn);
			}

			//	     修改時判斷條件
			if (request.getAttribute("action").equals("saveData")) {
				saveData_validate(l_actionErrors, request, conn);
			}
		}

		return l_actionErrors;
	}
	private void saveData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
	}
	private void addData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("utf-8");
			EHF030102C = ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new EHF030102M1F();
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
	public String getEHF030102T1_01() {
		return EHF030102T1_01;
	}
	public void setEHF030102T1_01(String eHF030102T1_01) {
		EHF030102T1_01 = eHF030102T1_01;
	}
	public String getEHF030102T1_02() {
		return EHF030102T1_02;
	}
	public void setEHF030102T1_02(String eHF030102T1_02) {
		EHF030102T1_02 = eHF030102T1_02;
	}
	public String getEHF030102T1_03() {
		return EHF030102T1_03;
	}
	public void setEHF030102T1_03(String eHF030102T1_03) {
		EHF030102T1_03 = eHF030102T1_03;
	}
	public String getEHF030102T1_04() {
		return EHF030102T1_04;
	}
	public void setEHF030102T1_04(String eHF030102T1_04) {
		EHF030102T1_04 = eHF030102T1_04;
	}
	public String getEHF030102T1_05() {
		return EHF030102T1_05;
	}
	public void setEHF030102T1_05(String eHF030102T1_05) {
		EHF030102T1_05 = eHF030102T1_05;
	}
	public String getEHF030102T1_06() {
		return EHF030102T1_06;
	}
	public void setEHF030102T1_06(String eHF030102T1_06) {
		EHF030102T1_06 = eHF030102T1_06;
	}
	public String getEHF030102T1_07() {
		return EHF030102T1_07;
	}
	public void setEHF030102T1_07(String eHF030102T1_07) {
		EHF030102T1_07 = eHF030102T1_07;
	}
	public String getEHF030102T1_08() {
		return EHF030102T1_08;
	}
	public void setEHF030102T1_08(String eHF030102T1_08) {
		EHF030102T1_08 = eHF030102T1_08;
	}
	public String getEHF030102T1_09() {
		return EHF030102T1_09;
	}
	public void setEHF030102T1_09(String eHF030102T1_09) {
		EHF030102T1_09 = eHF030102T1_09;
	}
	public String getEHF030102T1_10() {
		return EHF030102T1_10;
	}
	public void setEHF030102T1_10(String eHF030102T1_10) {
		EHF030102T1_10 = eHF030102T1_10;
	}
	public String getEHF030102T1_11() {
		return EHF030102T1_11;
	}
	public void setEHF030102T1_11(String eHF030102T1_11) {
		EHF030102T1_11 = eHF030102T1_11;
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
	public List getEHF030102C() {
		return EHF030102C;
	}
	public void setEHF030102C(List eHF030102C) {
		EHF030102C = eHF030102C;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getEHF030102T1_03_TYPE() {
		return EHF030102T1_03_TYPE;
	}
	public void setEHF030102T1_03_TYPE(String eHF030102T1_03TYPE) {
		EHF030102T1_03_TYPE = eHF030102T1_03TYPE;
	}
	public String getEHF030102T1_04_FREQ() {
		return EHF030102T1_04_FREQ;
	}
	public void setEHF030102T1_04_FREQ(String eHF030102T1_04FREQ) {
		EHF030102T1_04_FREQ = eHF030102T1_04FREQ;
	}
	
	
}