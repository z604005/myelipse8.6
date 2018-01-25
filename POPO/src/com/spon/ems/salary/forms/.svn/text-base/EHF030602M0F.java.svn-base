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

public class EHF030602M0F extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	private boolean CHECKED;
	private boolean CHANGED;
	
	private String EHF030602T0_U;
	private String EHF030602T0_01;
	private String EHF030602T0_01_TXT;
	private String EHF030602T0_02;
	private String EHF030602T0_02_TXT;
	private String EHF030602T0_M;
	private String EHF030602T0_M1;
	private String EHF030602T0_DS;
	private String EHF030602T0_03;
	private String EHF030602T0_SCP;
	private String EHF030602T0_07;
	private String EHF030602T0_SCU;
	private String EHF030602T0_TYPE;
	
	private String EHF030602T1_U;
	private String EHF030602T1_DATE;
	private String EHF030602T1_TYPE;
	private String EHF030602T1_TYPE_DESC;
	private String EHF030602T1_01;
	private String EHF030602T1_01_DATE;
	private String EHF030602T1_01_HH;
	private String EHF030602T1_01_MM;
	private String EHF030602T1_02;
	private String EHF030602T1_02_DATE;
	private String EHF030602T1_02_HH;
	private String EHF030602T1_02_MM;
	private float EHF030602T1_03;
	private int EHF030602T1_04;
	private String EHF030602T1_05;
	private String EHF030602T1_DS;
	private String EHF030602T1_06;
	private String EHF030602T1_07;
	
	private float HOUR_SUM;
	private int MONEY_SUM;

	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_CREATE;
	private String DATE_UPDATE;
	

	
	private List EHF030602C = new ArrayList();
	private List EHF030602C2 = new ArrayList();
	private List EHF030602C3 = new ArrayList();
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;
	}
	
	public ActionErrors validate(ActionMapping mapping,	HttpServletRequest request, Connection conn) {
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(l_actionErrors, EHF030602T0_01, "EHF030602T0_01", "不可空白");
		ve.isEmpty(l_actionErrors, EHF030602T0_02, "EHF030602T0_02", "不可空白");
		ve.isEmpty(l_actionErrors, EHF030602T0_M, "EHF030602T0_M", "不可空白");
		ve.isADYYMM(l_actionErrors, EHF030602T0_M, "EHF030602T0_M", "yyy/MM 格式錯誤");
		ve.isEmpty(l_actionErrors, EHF030602T0_M1, "EHF030602T0_M1", "不可空白");
		ve.isADYYMM(l_actionErrors, EHF030602T0_M1, "EHF030602T0_M1", "yyy/MM 格式錯誤");

		
		
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
		
		//檢核是否已有重複加班費資料
		this.chkaddDataExist(errors, request, conn);

	}
	
	
	private void addDetailData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		BA_Vaildate ve = BA_Vaildate.getInstance();
		try{
			
			
			ve.isEmpty(errors, EHF030602T1_DATE, "EHF030602T1_DATE", "不可空白");
			ve.isEmpty(errors, EHF030602T1_TYPE, "EHF030602T1_TYPE", "不可空白");
			ve.isEmpty(errors, EHF030602T1_01_DATE, "EHF030602T1_01_DATE", "不可空白");
			ve.isEmpty(errors, EHF030602T1_02_DATE, "EHF030602T1_02_DATE", "不可空白");
			ve.isNumEmpty(errors, String.valueOf(EHF030602T1_03), "EHF030602T1_03", "不可空白", true);
			ve.isNumEmpty(errors, String.valueOf(EHF030602T1_04), "EHF030602T1_04", "不可空白", true);
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = "" +
			" SELECT EHF030602T1_U, EHF030602T1_01, EHF030602T1_02 " +
			" FROM EHF030602T1 " +
			" WHERE 1=1 " +
			" AND EHF030602T1_U = '"+EHF030602T0_U+"' " +  //加班費明細UID
			" AND EHF030602T1_DATE = '"+EHF030602T1_DATE+"' " +  //加班日期
			" AND EHF030602T1_TYPE = '"+EHF030602T1_TYPE+"' " +  //加班類型
			" AND EHF030602T1_07 = '"+request.getAttribute("compid")+"' ";  //公司代碼
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				errors.add("EHF030602T1_DATE",new ActionMessage(""));
				errors.add("EHF030602T1_TYPE",new ActionMessage(""));
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						" 該加班日期的加班類型, 員工已有加班費資料,請確認後再建立資料!! ");
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			System.out.println(" EHF030602M0F.validate() "+e);
			e.printStackTrace();
		}

	}
	
	private void chkaddDataExist(ActionErrors errors, HttpServletRequest request, Connection conn){
		
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = "" +
			" SELECT EHF030602T0_U " +
			" FROM EHF030602T0 " +
			" WHERE 1=1 " +
			" AND EHF030602T0_M = '"+EHF030602T0_M+"' " +  //入扣帳年月
			" AND EHF030602T0_M1 = '"+EHF030602T0_M1+"' " +  //薪資年月
			" AND EHF030602T0_01 = '"+EHF030602T0_01+"' " +  //員工工號
			" AND EHF030602T0_07 = '"+request.getAttribute("compid")+"' ";  //公司代碼
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				errors.add("EHF030602T0_M",new ActionMessage(""));
				errors.add("EHF030602T0_M1",new ActionMessage(""));
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						" 該入扣帳年月、薪資年月已有該員工的加班費資料,請確認後再建立資料!! ");
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			System.out.println(" EHF030602M0F.validate() "+e);
			e.printStackTrace();
		}
		
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("utf-8");
			
			EHF030602C = ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new EHF030602M0F();
				            }
			 });
			
			EHF030602C2 = ListUtils.lazyList(new ArrayList(),new Factory() {
	            public Object create() {
	                return new EHF030602M0F();
	            }
			});
			
			EHF030602C3 = ListUtils.lazyList(new ArrayList(),new Factory() {
	            public Object create() {
	                return new EHF030602M0F();
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

	public String getEHF030602T0_U() {
		return EHF030602T0_U;
	}

	public void setEHF030602T0_U(String eHF030602T0U) {
		EHF030602T0_U = eHF030602T0U;
	}

	public String getEHF030602T0_01() {
		return EHF030602T0_01;
	}

	public void setEHF030602T0_01(String eHF030602T0_01) {
		EHF030602T0_01 = eHF030602T0_01;
	}

	public String getEHF030602T0_01_TXT() {
		return EHF030602T0_01_TXT;
	}

	public void setEHF030602T0_01_TXT(String eHF030602T0_01TXT) {
		EHF030602T0_01_TXT = eHF030602T0_01TXT;
	}

	public String getEHF030602T0_02() {
		return EHF030602T0_02;
	}

	public void setEHF030602T0_02(String eHF030602T0_02) {
		EHF030602T0_02 = eHF030602T0_02;
	}

	public String getEHF030602T0_02_TXT() {
		return EHF030602T0_02_TXT;
	}

	public void setEHF030602T0_02_TXT(String eHF030602T0_02TXT) {
		EHF030602T0_02_TXT = eHF030602T0_02TXT;
	}

	public String getEHF030602T0_M() {
		return EHF030602T0_M;
	}

	public void setEHF030602T0_M(String eHF030602T0M) {
		EHF030602T0_M = eHF030602T0M;
	}

	public String getEHF030602T0_M1() {
		return EHF030602T0_M1;
	}

	public void setEHF030602T0_M1(String eHF030602T0M1) {
		EHF030602T0_M1 = eHF030602T0M1;
	}

	public String getEHF030602T0_DS() {
		return EHF030602T0_DS;
	}

	public void setEHF030602T0_DS(String eHF030602T0DS) {
		EHF030602T0_DS = eHF030602T0DS;
	}

	public String getEHF030602T0_03() {
		return EHF030602T0_03;
	}

	public void setEHF030602T0_03(String eHF030602T0_03) {
		EHF030602T0_03 = eHF030602T0_03;
	}

	public String getEHF030602T0_SCP() {
		return EHF030602T0_SCP;
	}

	public void setEHF030602T0_SCP(String eHF030602T0SCP) {
		EHF030602T0_SCP = eHF030602T0SCP;
	}

	public String getEHF030602T0_07() {
		return EHF030602T0_07;
	}

	public void setEHF030602T0_07(String eHF030602T0_07) {
		EHF030602T0_07 = eHF030602T0_07;
	}

	public String getEHF030602T0_SCU() {
		return EHF030602T0_SCU;
	}

	public void setEHF030602T0_SCU(String eHF030602T0SCU) {
		EHF030602T0_SCU = eHF030602T0SCU;
	}

	public String getEHF030602T1_U() {
		return EHF030602T1_U;
	}

	public void setEHF030602T1_U(String eHF030602T1U) {
		EHF030602T1_U = eHF030602T1U;
	}

	public String getEHF030602T1_01() {
		return EHF030602T1_01;
	}

	public void setEHF030602T1_01(String eHF030602T1_01) {
		EHF030602T1_01 = eHF030602T1_01;
	}

	public String getEHF030602T1_01_DATE() {
		return EHF030602T1_01_DATE;
	}

	public void setEHF030602T1_01_DATE(String eHF030602T1_01DATE) {
		EHF030602T1_01_DATE = eHF030602T1_01DATE;
	}

	public String getEHF030602T1_01_HH() {
		return EHF030602T1_01_HH;
	}

	public void setEHF030602T1_01_HH(String eHF030602T1_01HH) {
		EHF030602T1_01_HH = eHF030602T1_01HH;
	}

	public String getEHF030602T1_01_MM() {
		return EHF030602T1_01_MM;
	}

	public void setEHF030602T1_01_MM(String eHF030602T1_01MM) {
		EHF030602T1_01_MM = eHF030602T1_01MM;
	}

	public String getEHF030602T1_02() {
		return EHF030602T1_02;
	}

	public void setEHF030602T1_02(String eHF030602T1_02) {
		EHF030602T1_02 = eHF030602T1_02;
	}

	public String getEHF030602T1_02_DATE() {
		return EHF030602T1_02_DATE;
	}

	public void setEHF030602T1_02_DATE(String eHF030602T1_02DATE) {
		EHF030602T1_02_DATE = eHF030602T1_02DATE;
	}

	public String getEHF030602T1_02_HH() {
		return EHF030602T1_02_HH;
	}

	public void setEHF030602T1_02_HH(String eHF030602T1_02HH) {
		EHF030602T1_02_HH = eHF030602T1_02HH;
	}

	public String getEHF030602T1_02_MM() {
		return EHF030602T1_02_MM;
	}

	public void setEHF030602T1_02_MM(String eHF030602T1_02MM) {
		EHF030602T1_02_MM = eHF030602T1_02MM;
	}

	public float getEHF030602T1_03() {
		return EHF030602T1_03;
	}

	public void setEHF030602T1_03(float eHF030602T1_03) {
		EHF030602T1_03 = eHF030602T1_03;
	}

	public int getEHF030602T1_04() {
		return EHF030602T1_04;
	}

	public void setEHF030602T1_04(int eHF030602T1_04) {
		EHF030602T1_04 = eHF030602T1_04;
	}

	public String getEHF030602T1_05() {
		return EHF030602T1_05;
	}

	public void setEHF030602T1_05(String eHF030602T1_05) {
		EHF030602T1_05 = eHF030602T1_05;
	}

	public String getEHF030602T1_DS() {
		return EHF030602T1_DS;
	}

	public void setEHF030602T1_DS(String eHF030602T1DS) {
		EHF030602T1_DS = eHF030602T1DS;
	}

	public String getEHF030602T1_06() {
		return EHF030602T1_06;
	}

	public void setEHF030602T1_06(String eHF030602T1_06) {
		EHF030602T1_06 = eHF030602T1_06;
	}

	public String getEHF030602T1_07() {
		return EHF030602T1_07;
	}

	public void setEHF030602T1_07(String eHF030602T1_07) {
		EHF030602T1_07 = eHF030602T1_07;
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

	public List getEHF030602C() {
		return EHF030602C;
	}

	public void setEHF030602C(List eHF030602C) {
		EHF030602C = eHF030602C;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getEHF030602T1_DATE() {
		return EHF030602T1_DATE;
	}

	public void setEHF030602T1_DATE(String eHF030602T1DATE) {
		EHF030602T1_DATE = eHF030602T1DATE;
	}

	public float getHOUR_SUM() {
		return HOUR_SUM;
	}

	public void setHOUR_SUM(float hOURSUM) {
		HOUR_SUM = hOURSUM;
	}

	public int getMONEY_SUM() {
		return MONEY_SUM;
	}

	public void setMONEY_SUM(int mONEYSUM) {
		MONEY_SUM = mONEYSUM;
	}

	public String getEHF030602T1_TYPE() {
		return EHF030602T1_TYPE;
	}

	public void setEHF030602T1_TYPE(String eHF030602T1TYPE) {
		EHF030602T1_TYPE = eHF030602T1TYPE;
	}

	public String getEHF030602T1_TYPE_DESC() {
		return EHF030602T1_TYPE_DESC;
	}

	public void setEHF030602T1_TYPE_DESC(String eHF030602T1TYPEDESC) {
		EHF030602T1_TYPE_DESC = eHF030602T1TYPEDESC;
	}

	public List getEHF030602C2() {
		return EHF030602C2;
	}

	public void setEHF030602C2(List eHF030602C2) {
		EHF030602C2 = eHF030602C2;
	}

	public List getEHF030602C3() {
		return EHF030602C3;
	}

	public void setEHF030602C3(List eHF030602C3) {
		EHF030602C3 = eHF030602C3;
	}

	public void setEHF030602T0_TYPE(String eHF030602T0_TYPE) {
		EHF030602T0_TYPE = eHF030602T0_TYPE;
	}

	public String getEHF030602T0_TYPE() {
		return EHF030602T0_TYPE;
	}
	
	
	
	
}
