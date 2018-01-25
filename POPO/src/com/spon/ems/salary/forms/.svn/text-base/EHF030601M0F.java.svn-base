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
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.spon.utils.util.BA_Vaildate;

public class EHF030601M0F extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	private boolean CHECKED;
	private boolean CHANGED;
	
	private String EHF030601T0_U;
	private String EHF030601T0_01;
	private String EHF030601T0_01_TXT;
	private String EHF030601T0_02;
	private String EHF030601T0_02_TXT;
	private String EHF030601T0_M;
	private String EHF030601T0_M1;
	private String EHF030601T0_DS;
	private String EHF030601T0_03;
	private String EHF030601T0_SCP;
	private String EHF030601T0_07;
	private String EHF030601T0_SCU;
	
	private String EHF030601T1_U;
	private String EHF030601T1_01;
	private String EHF030601T1_02;
	private String EHF030601T1_03;
	private String EHF030601T1_TAX;
	private int EHF030601T1_04;
	private int EHF030601T1_05;
	private String EHF030601T1_DS;
	private String EHF030601T1_06;
	private String EHF030601T1_07;
	
	private int MONEY_SUM;

	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_CREATE;
	private String DATE_UPDATE;
	

	
	private List EHF030601C = new ArrayList();
	private List EHF030601C2 = new ArrayList();
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;
	}
	
	public ActionErrors validate(ActionMapping mapping,	HttpServletRequest request, Connection conn) {
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(l_actionErrors, EHF030601T0_01, "EHF030601T0_01", "不可空白");
		ve.isEmpty(l_actionErrors, EHF030601T0_02, "EHF030601T0_02", "不可空白");
		ve.isEmpty(l_actionErrors, EHF030601T0_M, "EHF030601T0_M", "不可空白");
		ve.isADYYMM(l_actionErrors, EHF030601T0_M, "EHF030601T0_M", "yyy/MM 格式錯誤");
		ve.isEmpty(l_actionErrors, EHF030601T0_M1, "EHF030601T0_M1", "不可空白");
		ve.isADYYMM(l_actionErrors, EHF030601T0_M1, "EHF030601T0_M1", "yyy/MM 格式錯誤");

		
		
		if (l_actionErrors.isEmpty()) {
			
			//新增時判斷條件
			if (request.getAttribute("action").equals("addData")) {
				
				addData_validate(l_actionErrors, request, conn);
			}
			
			//新增明細時判斷條件
			if (request.getAttribute("action").equals("addDetailData")) {
				
				addDetailData_validate(l_actionErrors, request, conn);
			}

			//修改時判斷條件
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
		
		//檢核是否已有重複津貼資料
		this.chkaddDataExist(errors, request, conn);

	}
	
	
	private void addDetailData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		BA_Vaildate ve = BA_Vaildate.getInstance();
		try{
			ve.isEmpty(errors, EHF030601T1_01, "EHF030601T1_01", "不可空白");
			ve.isEmpty(errors, EHF030601T1_02, "EHF030601T1_02", "不可空白");
			ve.isNumEmpty(errors, String.valueOf(EHF030601T1_05), "EHF030601T1_05", "不可空白", true);
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = "" +
			" SELECT EHF030601T1_U, EHF030601T1_01, EHF030601T1_02 " +
			" FROM EHF030601T1 " +
			" WHERE 1=1 " +
			" AND EHF030601T1_U = '"+EHF030601T0_U+"' " +  //津貼明細UID
			" AND EHF030601T1_01 = '"+EHF030601T1_01+"' " +  //給津貼日期
			" AND EHF030601T1_02 = '"+EHF030601T1_02+"' " +  //津貼資料序號
			" AND EHF030601T1_07 = '"+request.getAttribute("compid")+"' ";  //公司代碼
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				errors.add("EHF030601T1_01",new ActionMessage(""));
				errors.add("EHF030601T1_02",new ActionMessage(""));
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						" 該津貼日期員工的已有同樣的津貼資料,請確認後再建立資料!! ");
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			System.out.println(" EHF030601M0F.validate() "+e);
			e.printStackTrace();
		}

	}
	
	private void chkaddDataExist(ActionErrors errors, HttpServletRequest request, Connection conn){
		
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = "" +
			" SELECT EHF030601T0_U " +
			" FROM EHF030601T0 " +
			" WHERE 1=1 " +
			" AND EHF030601T0_M = '"+EHF030601T0_M+"' " +  //入扣帳年月
			" AND EHF030601T0_M1 = '"+EHF030601T0_M1+"' " +  //薪資年月
			" AND EHF030601T0_01 = '"+EHF030601T0_01+"' " +  //員工工號
			" AND EHF030601T0_07 = '"+request.getAttribute("compid")+"' ";  //公司代碼
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				errors.add("EHF030601T0_M",new ActionMessage(""));
				errors.add("EHF030601T0_M1",new ActionMessage(""));
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						" 該入扣帳年月、薪資年月已有該員工的津貼資料,請確認後再建立資料!! ");
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			System.out.println(" EHF030601M0F.validate() "+e);
			e.printStackTrace();
		}
		
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("utf-8");
			
			EHF030601C = ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new EHF030601M0F();
				            }
			 });
			
			EHF030601C2 = ListUtils.lazyList(new ArrayList(),new Factory() {
	            public Object create() {
	                return new EHF030601M0F();
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

	public String getEHF030601T0_U() {
		return EHF030601T0_U;
	}

	public void setEHF030601T0_U(String eHF030601T0U) {
		EHF030601T0_U = eHF030601T0U;
	}

	public String getEHF030601T0_01() {
		return EHF030601T0_01;
	}

	public void setEHF030601T0_01(String eHF030601T0_01) {
		EHF030601T0_01 = eHF030601T0_01;
	}

	public String getEHF030601T0_01_TXT() {
		return EHF030601T0_01_TXT;
	}

	public void setEHF030601T0_01_TXT(String eHF030601T0_01TXT) {
		EHF030601T0_01_TXT = eHF030601T0_01TXT;
	}

	public String getEHF030601T0_02() {
		return EHF030601T0_02;
	}

	public void setEHF030601T0_02(String eHF030601T0_02) {
		EHF030601T0_02 = eHF030601T0_02;
	}

	public String getEHF030601T0_02_TXT() {
		return EHF030601T0_02_TXT;
	}

	public void setEHF030601T0_02_TXT(String eHF030601T0_02TXT) {
		EHF030601T0_02_TXT = eHF030601T0_02TXT;
	}

	public String getEHF030601T0_M() {
		return EHF030601T0_M;
	}

	public void setEHF030601T0_M(String eHF030601T0M) {
		EHF030601T0_M = eHF030601T0M;
	}

	public String getEHF030601T0_M1() {
		return EHF030601T0_M1;
	}

	public void setEHF030601T0_M1(String eHF030601T0M1) {
		EHF030601T0_M1 = eHF030601T0M1;
	}

	public String getEHF030601T0_DS() {
		return EHF030601T0_DS;
	}

	public void setEHF030601T0_DS(String eHF030601T0DS) {
		EHF030601T0_DS = eHF030601T0DS;
	}

	public String getEHF030601T0_03() {
		return EHF030601T0_03;
	}

	public void setEHF030601T0_03(String eHF030601T0_03) {
		EHF030601T0_03 = eHF030601T0_03;
	}

	public String getEHF030601T0_SCP() {
		return EHF030601T0_SCP;
	}

	public void setEHF030601T0_SCP(String eHF030601T0SCP) {
		EHF030601T0_SCP = eHF030601T0SCP;
	}

	public String getEHF030601T0_07() {
		return EHF030601T0_07;
	}

	public void setEHF030601T0_07(String eHF030601T0_07) {
		EHF030601T0_07 = eHF030601T0_07;
	}

	public String getEHF030601T0_SCU() {
		return EHF030601T0_SCU;
	}

	public void setEHF030601T0_SCU(String eHF030601T0SCU) {
		EHF030601T0_SCU = eHF030601T0SCU;
	}

	public String getEHF030601T1_U() {
		return EHF030601T1_U;
	}

	public void setEHF030601T1_U(String eHF030601T1U) {
		EHF030601T1_U = eHF030601T1U;
	}

	public String getEHF030601T1_01() {
		return EHF030601T1_01;
	}

	public void setEHF030601T1_01(String eHF030601T1_01) {
		EHF030601T1_01 = eHF030601T1_01;
	}

	public String getEHF030601T1_02() {
		return EHF030601T1_02;
	}

	public void setEHF030601T1_02(String eHF030601T1_02) {
		EHF030601T1_02 = eHF030601T1_02;
	}

	public String getEHF030601T1_03() {
		return EHF030601T1_03;
	}

	public void setEHF030601T1_03(String eHF030601T1_03) {
		EHF030601T1_03 = eHF030601T1_03;
	}

	public int getEHF030601T1_04() {
		return EHF030601T1_04;
	}

	public void setEHF030601T1_04(int eHF030601T1_04) {
		EHF030601T1_04 = eHF030601T1_04;
	}

	public int getEHF030601T1_05() {
		return EHF030601T1_05;
	}

	public void setEHF030601T1_05(int eHF030601T1_05) {
		EHF030601T1_05 = eHF030601T1_05;
	}

	public String getEHF030601T1_DS() {
		return EHF030601T1_DS;
	}

	public void setEHF030601T1_DS(String eHF030601T1DS) {
		EHF030601T1_DS = eHF030601T1DS;
	}

	public String getEHF030601T1_06() {
		return EHF030601T1_06;
	}

	public void setEHF030601T1_06(String eHF030601T1_06) {
		EHF030601T1_06 = eHF030601T1_06;
	}

	public String getEHF030601T1_07() {
		return EHF030601T1_07;
	}

	public void setEHF030601T1_07(String eHF030601T1_07) {
		EHF030601T1_07 = eHF030601T1_07;
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

	public List getEHF030601C() {
		return EHF030601C;
	}

	public void setEHF030601C(List eHF030601C) {
		EHF030601C = eHF030601C;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getMONEY_SUM() {
		return MONEY_SUM;
	}

	public void setMONEY_SUM(int mONEYSUM) {
		MONEY_SUM = mONEYSUM;
	}

	public String getEHF030601T1_TAX() {
		return EHF030601T1_TAX;
	}

	public void setEHF030601T1_TAX(String eHF030601T1TAX) {
		EHF030601T1_TAX = eHF030601T1TAX;
	}

	public List getEHF030601C2() {
		return EHF030601C2;
	}

	public void setEHF030601C2(List eHF030601C2) {
		EHF030601C2 = eHF030601C2;
	}
	
	
	
	
}
