package com.spon.ems.com.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.spon.ems.com.models.EHF000400;
import com.spon.utils.util.BA_Vaildate;

public class EHF000400M0F extends ActionForm implements ValidateForm {

	private static final long serialVersionUID = 1L;
	private boolean CHECKED;
	private boolean CHANGED;

	private  String  EHF000400T0_01;
	private  String  EHF000400T0_02;
	private  String  EHF000400T0_02_1;
	private  String  EHF000400T0_02_TXT;
	private  String  EHF000400T0_03;
	private  String  EHF000400T0_04;
	
	private  String  EHF000400T0_05;
	private  String  EHF000400T0_05_HH;
	private  String  EHF000400T0_05_MM;
	private  String  EHF000400T0_06;
	private  String  EHF000400T0_06_HH;
	private  String  EHF000400T0_06_MM;
	private  String  EHF000400T0_NFLG;
	private  String  EHF000400T0_07;
	private  String  EHF000400T0_07_HH;
	private  String  EHF000400T0_07_MM;
	private  String  EHF000400T0_08;
	private  String  EHF000400T0_08_HH;
	private  String  EHF000400T0_08_MM;
	private  String  EHF000400T0_05_FLG;
	private  String  EHF000400T0_05_VAL;
	private  String  EHF000400T0_06_FLG;
	private  String  EHF000400T0_06_VAL;
	private  String  EHF000400T0_09;
	private  String  EHF000400T0_10;
	private  String  EHF000400T0_11;
	private  String  EHF000400T0_12;
	private  String  EHF000400T0_13;
	private  String  EHF000400T0_14;
	private  String  EHF000400T0_15;
	private  String  EHF000400T0_16;
	private  String  EHF000400T0_17;//休假方式：(1)周休二日，(2)隔周休二日
	private  String[]  EHF000400T0_VAC = new String[7];
	private  String EHF000400T0_18;
	
	/*private  String  EHF010100T2_01;
	private  String  EHF010100T2_02;
	private  String  EHF010100T2_03;
	private  String  EHF010100T2_04;*/
	
	private  String  USER_CREATE;
	private  String  USER_UPDATE;
	private   int  VERSION;
	private  String  DATE_UPDATE;

	private List EHF000400C = new ArrayList();

	
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
		
		if (l_actionErrors.isEmpty()) {
			
			
			try{
				Statement stmt = conn.createStatement();
				String sql = "";
				
				if("1".equals(EHF000400T0_09) && EHF000400T0_09!=null ){
				
					sql += " SELECT * FROM EHF000400T0 " +
						   " WHERE 1=1 " +
						   " AND EHF000400T0_09 = '"+EHF000400T0_09+"' " +
						   //" AND EHF000400T0_02 = '"+EHF000400T0_02+"' " +
						   " AND EHF000400T0_11 = '"+request.getAttribute("compid")+"' ";
				
					ResultSet rs = stmt.executeQuery(sql);
				
					if(rs.next()){
						if(!EHF000400T0_01.equals(rs.getString("EHF000400T0_01"))){
							l_actionErrors.add("EHF000400T0_09",new ActionMessage(""));
							request.setAttribute("ErrMSG",
									(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
									"已有系統預設班別,請確認!! ");
						}
					}
					rs.close();
				}
				stmt.close();
			}catch (SQLException e) {
				System.out.println("EHF000400M0F.addData_validate()" + e);
			}
			
			
			//新增時判斷條件
			if (request.getAttribute("action").equals("addData")) {
				
				//ve.isEmpty(l_actionErrors, EHF000400T0_02, "EHF000400T0_02", "<br>不可空白");
				ve.isEmpty(l_actionErrors, EHF000400T0_03, "EHF000400T0_03", "<br>不可空白");
				ve.isEmpty(l_actionErrors, EHF000400T0_04, "EHF000400T0_04", "<br>不可空白");
				ve.isEmpty(l_actionErrors, EHF000400T0_09, "EHF000400T0_09", "<br><br>不可空白");
				ve.isEmpty(l_actionErrors, EHF000400T0_18, "EHF000400T0_18", "<br><br>不可空白");
				
				addData_validate(l_actionErrors, request, conn);
			}

			//	     修改時判斷條件
			if (request.getAttribute("action").equals("saveData")) {
				
				//ve.isEmpty(l_actionErrors, EHF000400T0_02, "EHF000400T0_02", "<br>不可空白");
				ve.isEmpty(l_actionErrors, EHF000400T0_03, "EHF000400T0_03", "<br>不可空白");
				ve.isEmpty(l_actionErrors, EHF000400T0_04, "EHF000400T0_04", "<br>不可空白");
				ve.isEmpty(l_actionErrors, EHF000400T0_09, "EHF000400T0_09", "<br><br>不可空白");
				ve.isEmpty(l_actionErrors, EHF000400T0_18, "EHF000400T0_18", "<br><br>不可空白");
				
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
		
		boolean query ;
		String arrid[] = request.getParameterValues("checkId");
		String comp_id = (String) request.getAttribute("compid");
		
		try{
			EHF000400 ehf000400 = new EHF000400();
			
			query = ehf000400.selectEHF010100T8(arrid[0], EHF000400T0_03, comp_id);
			
			if(query){
				errors.add("EHF000400T0_03", new ActionMessage(""));
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						"此班別使用中，請勿刪除!!");
			}
			
			ehf000400.close();
			
		}catch (Exception e) {
			System.out.println("EHF000400M0F.delData_validate()" + e);
		}
		
	}
	private void saveData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
	}
	private void addData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		try{
			Statement stmt = conn.createStatement();
			String sql = "";
			
			sql += " SELECT * FROM EHF000400T0 " +
				   " WHERE 1=1 " +
				   //" AND EHF000400T0_02 = '"+EHF000400T0_02+"' " +
				   " AND EHF000400T0_03 = '"+EHF000400T0_03+"' " +
				   " AND EHF000400T0_11 = '"+request.getAttribute("compid")+"' ";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				errors.add("EHF000400T0_03",new ActionMessage("班別代號重複,請確認後再建立資料"));
			}
			rs.close();
			stmt.close();
			
		}catch (SQLException e) {
			System.out.println("EHF000400M0F.addData_validate()" + e);
		}
	}
	/*public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("utf-8");
			EHF000400C = ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new EHF000400M0F();
				            }         
			 });
			
			EHF000400T0_VAC = new String[7];
			
		} catch (Exception e) {
		}

	}*/
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
	public String getEHF000400T0_01() {
		return EHF000400T0_01;
	}
	public void setEHF000400T0_01(String eHF000400T0_01) {
		EHF000400T0_01 = eHF000400T0_01;
	}
	public String getEHF000400T0_02() {
		return EHF000400T0_02;
	}
	public void setEHF000400T0_02(String eHF000400T0_02) {
		EHF000400T0_02 = eHF000400T0_02;
	}
	public String getEHF000400T0_02_1() {
		return EHF000400T0_02_1;
	}
	public void setEHF000400T0_02_1(String eHF000400T0_02_1) {
		EHF000400T0_02_1 = eHF000400T0_02_1;
	}
	public String getEHF000400T0_02_TXT() {
		return EHF000400T0_02_TXT;
	}
	public void setEHF000400T0_02_TXT(String eHF000400T0_02TXT) {
		EHF000400T0_02_TXT = eHF000400T0_02TXT;
	}
	public String getEHF000400T0_03() {
		return EHF000400T0_03;
	}
	public void setEHF000400T0_03(String eHF000400T0_03) {
		EHF000400T0_03 = eHF000400T0_03;
	}
	public String getEHF000400T0_04() {
		return EHF000400T0_04;
	}
	public void setEHF000400T0_04(String eHF000400T0_04) {
		EHF000400T0_04 = eHF000400T0_04;
	}	
	
	public String getEHF000400T0_05() {
		return EHF000400T0_05;
	}
	public void setEHF000400T0_05(String eHF000400T0_05) {
		EHF000400T0_05 = eHF000400T0_05;
	}
	public String getEHF000400T0_05_HH() {
		return EHF000400T0_05_HH;
	}
	public void setEHF000400T0_05_HH(String eHF000400T0_05HH) {
		EHF000400T0_05_HH = eHF000400T0_05HH;
	}
	public String getEHF000400T0_05_MM() {
		return EHF000400T0_05_MM;
	}
	public void setEHF000400T0_05_MM(String eHF000400T0_05MM) {
		EHF000400T0_05_MM = eHF000400T0_05MM;
	}
	public String getEHF000400T0_06() {
		return EHF000400T0_06;
	}
	public void setEHF000400T0_06(String eHF000400T0_06) {
		EHF000400T0_06 = eHF000400T0_06;
	}
	public String getEHF000400T0_06_HH() {
		return EHF000400T0_06_HH;
	}
	public void setEHF000400T0_06_HH(String eHF000400T0_06HH) {
		EHF000400T0_06_HH = eHF000400T0_06HH;
	}
	public String getEHF000400T0_06_MM() {
		return EHF000400T0_06_MM;
	}
	public void setEHF000400T0_06_MM(String eHF000400T0_06MM) {
		EHF000400T0_06_MM = eHF000400T0_06MM;
	}
	public String getEHF000400T0_NFLG() {
		return EHF000400T0_NFLG;
	}
	public void setEHF000400T0_NFLG(String eHF000400T0NFLG) {
		EHF000400T0_NFLG = eHF000400T0NFLG;
	}
	public String getEHF000400T0_07() {
		return EHF000400T0_07;
	}
	public void setEHF000400T0_07(String eHF000400T0_07) {
		EHF000400T0_07 = eHF000400T0_07;
	}
	public String getEHF000400T0_07_HH() {
		return EHF000400T0_07_HH;
	}
	public void setEHF000400T0_07_HH(String eHF000400T0_07HH) {
		EHF000400T0_07_HH = eHF000400T0_07HH;
	}
	public String getEHF000400T0_07_MM() {
		return EHF000400T0_07_MM;
	}
	public void setEHF000400T0_07_MM(String eHF000400T0_07MM) {
		EHF000400T0_07_MM = eHF000400T0_07MM;
	}
	public String getEHF000400T0_08() {
		return EHF000400T0_08;
	}
	public void setEHF000400T0_08(String eHF000400T0_08) {
		EHF000400T0_08 = eHF000400T0_08;
	}
	public String getEHF000400T0_08_HH() {
		return EHF000400T0_08_HH;
	}
	public void setEHF000400T0_08_HH(String eHF000400T0_08HH) {
		EHF000400T0_08_HH = eHF000400T0_08HH;
	}
	public String getEHF000400T0_08_MM() {
		return EHF000400T0_08_MM;
	}
	public void setEHF000400T0_08_MM(String eHF000400T0_08MM) {
		EHF000400T0_08_MM = eHF000400T0_08MM;
	}
	public String getEHF000400T0_05_FLG() {
		return EHF000400T0_05_FLG;
	}
	public void setEHF000400T0_05_FLG(String eHF000400T0_05FLG) {
		EHF000400T0_05_FLG = eHF000400T0_05FLG;
	}
	public String getEHF000400T0_05_VAL() {
		return EHF000400T0_05_VAL;
	}
	public void setEHF000400T0_05_VAL(String eHF000400T0_05VAL) {
		EHF000400T0_05_VAL = eHF000400T0_05VAL;
	}
	public String getEHF000400T0_06_FLG() {
		return EHF000400T0_06_FLG;
	}
	public void setEHF000400T0_06_FLG(String eHF000400T0_06FLG) {
		EHF000400T0_06_FLG = eHF000400T0_06FLG;
	}
	public String getEHF000400T0_06_VAL() {
		return EHF000400T0_06_VAL;
	}
	public void setEHF000400T0_06_VAL(String eHF000400T0_06VAL) {
		EHF000400T0_06_VAL = eHF000400T0_06VAL;
	}
	public String getEHF000400T0_09() {
		return EHF000400T0_09;
	}
	public void setEHF000400T0_09(String eHF000400T0_09) {
		EHF000400T0_09 = eHF000400T0_09;
	}
	public String getEHF000400T0_10() {
		return EHF000400T0_10;
	}
	public void setEHF000400T0_10(String eHF000400T0_10) {
		EHF000400T0_10 = eHF000400T0_10;
	}
	public String getEHF000400T0_11() {
		return EHF000400T0_11;
	}
	public void setEHF000400T0_11(String eHF000400T0_11) {
		EHF000400T0_11 = eHF000400T0_11;
	}
	public String getEHF000400T0_12() {
		return EHF000400T0_12;
	}
	public void setEHF000400T0_12(String eHF000400T0_12) {
		EHF000400T0_12 = eHF000400T0_12;
	}
	public String getEHF000400T0_13() {
		return EHF000400T0_13;
	}
	public void setEHF000400T0_13(String eHF000400T0_13) {
		EHF000400T0_13 = eHF000400T0_13;
	}
	public String getEHF000400T0_14() {
		return EHF000400T0_14;
	}
	public void setEHF000400T0_14(String eHF000400T0_14) {
		EHF000400T0_14 = eHF000400T0_14;
	}
	public String getEHF000400T0_15() {
		return EHF000400T0_15;
	}
	public void setEHF000400T0_15(String eHF000400T0_15) {
		EHF000400T0_15 = eHF000400T0_15;
	}
	public String getEHF000400T0_16() {
		return EHF000400T0_16;
	}
	public void setEHF000400T0_16(String eHF000400T0_16) {
		EHF000400T0_16 = eHF000400T0_16;
	}
	public String[] getEHF000400T0_VAC() {
		return EHF000400T0_VAC;
	}
	public void setEHF000400T0_VAC(String[] eHF000400T0VAC) {
		EHF000400T0_VAC = eHF000400T0VAC;
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
	public List getEHF000400C() {
		return EHF000400C;
	}
	public void setEHF000400C(List eHF000400C) {
		EHF000400C = eHF000400C;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setEHF000400T0_17(String eHF000400T0_17) {
		EHF000400T0_17 = eHF000400T0_17;
	}
	public String getEHF000400T0_17() {
		return EHF000400T0_17;
	}
	public void setEHF000400T0_18(String eHF000400T0_18) {
		EHF000400T0_18 = eHF000400T0_18;
	}
	public String getEHF000400T0_18() {
		return EHF000400T0_18;
	}

}
