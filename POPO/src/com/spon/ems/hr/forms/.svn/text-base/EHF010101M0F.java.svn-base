package com.spon.ems.hr.forms;

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

public class EHF010101M0F extends ActionForm {
	private static final long serialVersionUID = 1L;
	private boolean CHECKED;
	private boolean CHANGED;

	private  String  EHF010101T0_01;
	private  String  EHF010101T0_02;
	private  String  EHF010101T0_03;
	private  String  EHF010101T0_04;
	private  String  EHF010101T0_05;
	private  String  EHF010101T0_06;
	private  String  EHF010101T0_06_FLG;
	private  String  EHF010101T0_06_RATE;
	private  String  EHF010101T0_07;
	private  String  EHF010101T0_08;
	private  String  EHF010101T0_09;
	private  String  EHF010101T0_09_HH;
	private  String  EHF010101T0_09_MM;
	private  String  EHF010101T0_10;
	private  String  EHF010101T0_11;
	private  String  EHF010101T0_12;
	private  String  EHF010101T0_13;
	private  String  EHF010101T0_14;
	private  String  EHF010101T0_15;
	private  String  EHF010101T0_16;
	private  String  EHF010101T0_17;
	private  String  EHF010101T0_18;
	private  String  EHF010101T0_19;
	private  String  EHF010101T0_19_HH;
	private  String  EHF010101T0_19_MM;
	private  String  EHF010101T0_20;
	private  String  EHF010101T0_21;
	private  String  EHF010101T0_22;
	private  String  EHF010101T0_24;//是否參考加班單 20131107 新增  BY賴泓錡

	private  String  Class_no;//計算津貼時  用於暫存津貼使用班別
	
	private  String  USER_CREATE;
	private  String  USER_UPDATE;
	private   int  VERSION;
	private  String  DATE_UPDATE;

	private List EHF010101C=new ArrayList();
	
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
		
		if (!request.getAttribute("action").equals("delData")) {//刪除時不用檢查是否空白
			//檢查不可為空白
			BA_Vaildate ve=BA_Vaildate.getInstance();
			
			ve.isEmpty(l_actionErrors, EHF010101T0_02, "EHF010101T0_02", "不可空白");
			ve.isEmpty(l_actionErrors, EHF010101T0_03, "EHF010101T0_03", "不可空白");
			ve.isEmpty(l_actionErrors, EHF010101T0_05, "EHF010101T0_05", "不可空白");
			ve.isEmpty(l_actionErrors, EHF010101T0_06, "EHF010101T0_06", "不可空白");
		}

		
		
		
		if (l_actionErrors.isEmpty()) {
			
			
			try{
				Statement stmt = conn.createStatement();
				String sql = "";
				
				if(EHF010101T0_02 != null ){
				
					sql += " SELECT * FROM EHF010101T0 " +
						   " WHERE 1=1 " +
						   " AND EHF010101T0_02 = '"+EHF010101T0_02+"' " +
						   " AND EHF010101T0_23 = '"+request.getAttribute("compid")+"' ";
				
					ResultSet rs = stmt.executeQuery(sql);
				
					if(rs.next()){
						if(!(EHF010101T0_01.equals(rs.getString("EHF010101T0_01")))){
							if(EHF010101T0_02.equals(rs.getString("EHF010101T0_02"))){
								l_actionErrors.add("EHF010101T0_02",new ActionMessage(""));
								request.setAttribute("ErrMSG",
										(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
										"津貼名稱重複,請確認!! ");
							}
						}
						
					}
					rs.close();
				}
				
				stmt.close();
				
			}catch (SQLException e) {
				System.out.println("EHF010101M0F.addData_validate()" + e);
			}
			
			//新增時判斷條件
			if (request.getAttribute("action").equals("addData")) {
				
				addData_validate(l_actionErrors, request, conn);
			}

			//	     修改時判斷條件
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
	private void delData_validate(ActionErrors errors,	HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		try{
			String arrid[] = request.getParameterValues("checkId");
			String comp_id = (String) request.getAttribute("compid");
			
			Statement stmt = conn.createStatement();
			String sql = " SELECT EHF030200T1_01 FROM EHF030200T1 " +
						 " WHERE 1=1 " +
						 " AND EHF030200T1_02 = '"+arrid[0]+"' " +
					     " AND EHF030200T1_04 = '"+comp_id+"' " +  //公司代碼
					     " ORDER BY EHF030200T1_01 ";
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
					errors.add("EHF010101T0_02",new ActionMessage(""));
					request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":
						request.getAttribute("ErrMSG")+"<br>")+"刪除失敗，有人正在使此項津貼，請到員工基本資料刪除設定後再試一次!!" + "<br>");
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
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
			EHF010101C = ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new EHF010101M0F();
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
	public String getEHF010101T0_01() {
		return EHF010101T0_01;
	}
	public void setEHF010101T0_01(String eHF010101T0_01) {
		EHF010101T0_01 = eHF010101T0_01;
	}
	public String getEHF010101T0_02() {
		return EHF010101T0_02;
	}
	public void setEHF010101T0_02(String eHF010101T0_02) {
		EHF010101T0_02 = eHF010101T0_02;
	}
	public String getEHF010101T0_03() {
		return EHF010101T0_03;
	}
	public void setEHF010101T0_03(String eHF010101T0_03) {
		EHF010101T0_03 = eHF010101T0_03;
	}
	public String getEHF010101T0_04() {
		return EHF010101T0_04;
	}
	public void setEHF010101T0_04(String eHF010101T0_04) {
		EHF010101T0_04 = eHF010101T0_04;
	}
	public String getEHF010101T0_05() {
		return EHF010101T0_05;
	}
	public void setEHF010101T0_05(String eHF010101T0_05) {
		EHF010101T0_05 = eHF010101T0_05;
	}
	public String getEHF010101T0_06() {
		return EHF010101T0_06;
	}
	public void setEHF010101T0_06(String eHF010101T0_06) {
		EHF010101T0_06 = eHF010101T0_06;
	}
	public String getEHF010101T0_07() {
		return EHF010101T0_07;
	}
	public void setEHF010101T0_07(String eHF010101T0_07) {
		EHF010101T0_07 = eHF010101T0_07;
	}
	public String getEHF010101T0_08() {
		return EHF010101T0_08;
	}
	public void setEHF010101T0_08(String eHF010101T0_08) {
		EHF010101T0_08 = eHF010101T0_08;
	}
	public String getEHF010101T0_09() {
		return EHF010101T0_09;
	}
	public void setEHF010101T0_09(String eHF010101T0_09) {
		EHF010101T0_09 = eHF010101T0_09;
	}
	public String getEHF010101T0_09_HH() {
		return EHF010101T0_09_HH;
	}
	public void setEHF010101T0_09_HH(String eHF010101T0_09HH) {
		EHF010101T0_09_HH = eHF010101T0_09HH;
	}
	public String getEHF010101T0_09_MM() {
		return EHF010101T0_09_MM;
	}
	public void setEHF010101T0_09_MM(String eHF010101T0_09MM) {
		EHF010101T0_09_MM = eHF010101T0_09MM;
	}
	public String getEHF010101T0_10() {
		return EHF010101T0_10;
	}
	public void setEHF010101T0_10(String eHF010101T0_10) {
		EHF010101T0_10 = eHF010101T0_10;
	}
	public String getEHF010101T0_11() {
		return EHF010101T0_11;
	}
	public void setEHF010101T0_11(String eHF010101T0_11) {
		EHF010101T0_11 = eHF010101T0_11;
	}
	public String getEHF010101T0_12() {
		return EHF010101T0_12;
	}
	public void setEHF010101T0_12(String eHF010101T0_12) {
		EHF010101T0_12 = eHF010101T0_12;
	}
	public String getEHF010101T0_13() {
		return EHF010101T0_13;
	}
	public void setEHF010101T0_13(String eHF010101T0_13) {
		EHF010101T0_13 = eHF010101T0_13;
	}
	public String getEHF010101T0_14() {
		return EHF010101T0_14;
	}
	public void setEHF010101T0_14(String eHF010101T0_14) {
		EHF010101T0_14 = eHF010101T0_14;
	}
	public String getEHF010101T0_15() {
		return EHF010101T0_15;
	}
	public void setEHF010101T0_15(String eHF010101T0_15) {
		EHF010101T0_15 = eHF010101T0_15;
	}
	public String getEHF010101T0_16() {
		return EHF010101T0_16;
	}
	public void setEHF010101T0_16(String eHF010101T0_16) {
		EHF010101T0_16 = eHF010101T0_16;
	}
	public String getEHF010101T0_17() {
		return EHF010101T0_17;
	}
	public void setEHF010101T0_17(String eHF010101T0_17) {
		EHF010101T0_17 = eHF010101T0_17;
	}
	public String getEHF010101T0_18() {
		return EHF010101T0_18;
	}
	public void setEHF010101T0_18(String eHF010101T0_18) {
		EHF010101T0_18 = eHF010101T0_18;
	}
	public String getEHF010101T0_19() {
		return EHF010101T0_19;
	}
	public void setEHF010101T0_19(String eHF010101T0_19) {
		EHF010101T0_19 = eHF010101T0_19;
	}
	public String getEHF010101T0_19_HH() {
		return EHF010101T0_19_HH;
	}
	public void setEHF010101T0_19_HH(String eHF010101T0_19HH) {
		EHF010101T0_19_HH = eHF010101T0_19HH;
	}
	public String getEHF010101T0_19_MM() {
		return EHF010101T0_19_MM;
	}
	public void setEHF010101T0_19_MM(String eHF010101T0_19MM) {
		EHF010101T0_19_MM = eHF010101T0_19MM;
	}
	public String getEHF010101T0_20() {
		return EHF010101T0_20;
	}
	public void setEHF010101T0_20(String eHF010101T0_20) {
		EHF010101T0_20 = eHF010101T0_20;
	}
	public String getEHF010101T0_21() {
		return EHF010101T0_21;
	}
	public void setEHF010101T0_21(String eHF010101T0_21) {
		EHF010101T0_21 = eHF010101T0_21;
	}
	public String getEHF010101T0_22() {
		return EHF010101T0_22;
	}
	public void setEHF010101T0_22(String eHF010101T0_22) {
		EHF010101T0_22 = eHF010101T0_22;
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
	public List getEHF010101C() {
		return EHF010101C;
	}
	public void setEHF010101C(List eHF010101C) {
		EHF010101C = eHF010101C;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getEHF010101T0_06_FLG() {
		return EHF010101T0_06_FLG;
	}
	public void setEHF010101T0_06_FLG(String eHF010101T0_06FLG) {
		EHF010101T0_06_FLG = eHF010101T0_06FLG;
	}
	public String getEHF010101T0_06_RATE() {
		return EHF010101T0_06_RATE;
	}
	public void setEHF010101T0_06_RATE(String eHF010101T0_06RATE) {
		EHF010101T0_06_RATE = eHF010101T0_06RATE;
	}
	public String getEHF010101T0_24() {
		return EHF010101T0_24;
	}
	public void setEHF010101T0_24(String eHF010101T0_24) {
		EHF010101T0_24 = eHF010101T0_24;
	}
	public String getClass_no() {
		return Class_no;
	}
	public void setClass_no(String classNo) {
		Class_no = classNo;
	}
	
}