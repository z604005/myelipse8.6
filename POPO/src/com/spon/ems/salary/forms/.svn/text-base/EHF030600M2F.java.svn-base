package com.spon.ems.salary.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class EHF030600M2F extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	private boolean CHECKED;
	private boolean CHANGED;
	
	private String EHF030600T2_01;
	private String EHF030600T2_02;
	private String EHF030600T2_03;
	private String EHF030600T2_04;
	private String EHF030600T2_M;
	private String EHF030600T2_05;
	private int EHF030600T2_06;
	private String EHF030600T2_07;
	private String EHF030600T2_08;
	private String EHF030600T2_09;
	private String EHF030600T2_10;
	private String EHF030600T2_11;
	private String EHF030600T2_12;
	private String EHF030600T2_INS_TYPE;
	private String EHF030600T2_13;
	private String EHF030600T2_13_VERSION;
	private int EHF030600T2_14;
	private int EHF030600T2_15;
	private int EHF030600T2_16;
	private String EHF030600T2_17;
	private String EHF030600T2_17_VERSION;
	private int EHF030600T2_18;
	private int EHF030600T2_19;
	private int EHF030600T2_20;
	private int EHF030600T2_21;
	private int EHF030600T2_22;
	private String EHF030600T2_23;
	private String EHF030600T2_24;
	private String EHF030600T2_24_TYPE;
	private float EHF030600T2_25;
	private int EHF030600T2_26;
	private float EHF030600T2_27;
	private int EHF030600T2_28;
	private float EHF030600T2_29;
	private int EHF030600T2_30;
	private float EHF030600T2_31;
	private String EHF030600T2_SCU;

	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_CREATE;
	private String DATE_UPDATE;
	
	private List EHF030600T3 = new ArrayList();
	
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;
	}
	
	public ActionErrors validate(ActionMapping mapping,	HttpServletRequest request, Connection conn) {
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		
		
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

	}
	
	
	private void addDetailData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub

	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("utf-8");
			
			EHF030600T3 = ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new EHF030600M2F();
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

	public String getEHF030600T2_01() {
		return EHF030600T2_01;
	}

	public void setEHF030600T2_01(String eHF030600T2_01) {
		EHF030600T2_01 = eHF030600T2_01;
	}

	public String getEHF030600T2_02() {
		return EHF030600T2_02;
	}

	public void setEHF030600T2_02(String eHF030600T2_02) {
		EHF030600T2_02 = eHF030600T2_02;
	}

	public String getEHF030600T2_03() {
		return EHF030600T2_03;
	}

	public void setEHF030600T2_03(String eHF030600T2_03) {
		EHF030600T2_03 = eHF030600T2_03;
	}

	public String getEHF030600T2_04() {
		return EHF030600T2_04;
	}

	public void setEHF030600T2_04(String eHF030600T2_04) {
		EHF030600T2_04 = eHF030600T2_04;
	}

	public String getEHF030600T2_M() {
		return EHF030600T2_M;
	}

	public void setEHF030600T2_M(String eHF030600T2M) {
		EHF030600T2_M = eHF030600T2M;
	}

	public String getEHF030600T2_05() {
		return EHF030600T2_05;
	}

	public void setEHF030600T2_05(String eHF030600T2_05) {
		EHF030600T2_05 = eHF030600T2_05;
	}

	public int getEHF030600T2_06() {
		return EHF030600T2_06;
	}

	public void setEHF030600T2_06(int eHF030600T2_06) {
		EHF030600T2_06 = eHF030600T2_06;
	}

	public String getEHF030600T2_07() {
		return EHF030600T2_07;
	}

	public void setEHF030600T2_07(String eHF030600T2_07) {
		EHF030600T2_07 = eHF030600T2_07;
	}

	public String getEHF030600T2_08() {
		return EHF030600T2_08;
	}

	public void setEHF030600T2_08(String eHF030600T2_08) {
		EHF030600T2_08 = eHF030600T2_08;
	}

	public String getEHF030600T2_09() {
		return EHF030600T2_09;
	}

	public void setEHF030600T2_09(String eHF030600T2_09) {
		EHF030600T2_09 = eHF030600T2_09;
	}

	public String getEHF030600T2_10() {
		return EHF030600T2_10;
	}

	public void setEHF030600T2_10(String eHF030600T2_10) {
		EHF030600T2_10 = eHF030600T2_10;
	}

	public String getEHF030600T2_11() {
		return EHF030600T2_11;
	}

	public void setEHF030600T2_11(String eHF030600T2_11) {
		EHF030600T2_11 = eHF030600T2_11;
	}

	public String getEHF030600T2_12() {
		return EHF030600T2_12;
	}

	public void setEHF030600T2_12(String eHF030600T2_12) {
		EHF030600T2_12 = eHF030600T2_12;
	}

	public String getEHF030600T2_13() {
		return EHF030600T2_13;
	}

	public void setEHF030600T2_13(String eHF030600T2_13) {
		EHF030600T2_13 = eHF030600T2_13;
	}

	public String getEHF030600T2_13_VERSION() {
		return EHF030600T2_13_VERSION;
	}

	public void setEHF030600T2_13_VERSION(String eHF030600T2_13VERSION) {
		EHF030600T2_13_VERSION = eHF030600T2_13VERSION;
	}

	public int getEHF030600T2_14() {
		return EHF030600T2_14;
	}

	public void setEHF030600T2_14(int eHF030600T2_14) {
		EHF030600T2_14 = eHF030600T2_14;
	}

	public int getEHF030600T2_15() {
		return EHF030600T2_15;
	}

	public void setEHF030600T2_15(int eHF030600T2_15) {
		EHF030600T2_15 = eHF030600T2_15;
	}

	public int getEHF030600T2_16() {
		return EHF030600T2_16;
	}

	public void setEHF030600T2_16(int eHF030600T2_16) {
		EHF030600T2_16 = eHF030600T2_16;
	}

	public String getEHF030600T2_17() {
		return EHF030600T2_17;
	}

	public String getEHF030600T2_17_VERSION() {
		return EHF030600T2_17_VERSION;
	}

	public void setEHF030600T2_17_VERSION(String eHF030600T2_17VERSION) {
		EHF030600T2_17_VERSION = eHF030600T2_17VERSION;
	}

	public void setEHF030600T2_17(String eHF030600T2_17) {
		EHF030600T2_17 = eHF030600T2_17;
	}

	public int getEHF030600T2_18() {
		return EHF030600T2_18;
	}

	public void setEHF030600T2_18(int eHF030600T2_18) {
		EHF030600T2_18 = eHF030600T2_18;
	}

	public int getEHF030600T2_19() {
		return EHF030600T2_19;
	}

	public void setEHF030600T2_19(int eHF030600T2_19) {
		EHF030600T2_19 = eHF030600T2_19;
	}

	public int getEHF030600T2_20() {
		return EHF030600T2_20;
	}

	public void setEHF030600T2_20(int eHF030600T2_20) {
		EHF030600T2_20 = eHF030600T2_20;
	}

	public int getEHF030600T2_21() {
		return EHF030600T2_21;
	}

	public void setEHF030600T2_21(int eHF030600T2_21) {
		EHF030600T2_21 = eHF030600T2_21;
	}

	public int getEHF030600T2_22() {
		return EHF030600T2_22;
	}

	public void setEHF030600T2_22(int eHF030600T2_22) {
		EHF030600T2_22 = eHF030600T2_22;
	}

	public String getEHF030600T2_23() {
		return EHF030600T2_23;
	}

	public void setEHF030600T2_23(String eHF030600T2_23) {
		EHF030600T2_23 = eHF030600T2_23;
	}

	public String getEHF030600T2_SCU() {
		return EHF030600T2_SCU;
	}

	public void setEHF030600T2_SCU(String eHF030600T2SCU) {
		EHF030600T2_SCU = eHF030600T2SCU;
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

	public List getEHF030600T3() {
		return EHF030600T3;
	}

	public void setEHF030600T3(List eHF030600T3) {
		EHF030600T3 = eHF030600T3;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getEHF030600T2_24() {
		return EHF030600T2_24;
	}

	public void setEHF030600T2_24(String eHF030600T2_24) {
		EHF030600T2_24 = eHF030600T2_24;
	}

	public String getEHF030600T2_24_TYPE() {
		return EHF030600T2_24_TYPE;
	}

	public void setEHF030600T2_24_TYPE(String eHF030600T2_24TYPE) {
		EHF030600T2_24_TYPE = eHF030600T2_24TYPE;
	}

	public float getEHF030600T2_25() {
		return EHF030600T2_25;
	}

	public void setEHF030600T2_25(float eHF030600T2_25) {
		EHF030600T2_25 = eHF030600T2_25;
	}

	public int getEHF030600T2_26() {
		return EHF030600T2_26;
	}

	public void setEHF030600T2_26(int eHF030600T2_26) {
		EHF030600T2_26 = eHF030600T2_26;
	}

	public float getEHF030600T2_27() {
		return EHF030600T2_27;
	}

	public void setEHF030600T2_27(float eHF030600T2_27) {
		EHF030600T2_27 = eHF030600T2_27;
	}

	public int getEHF030600T2_28() {
		return EHF030600T2_28;
	}

	public void setEHF030600T2_28(int eHF030600T2_28) {
		EHF030600T2_28 = eHF030600T2_28;
	}

	public float getEHF030600T2_29() {
		return EHF030600T2_29;
	}

	public void setEHF030600T2_29(float eHF030600T2_29) {
		EHF030600T2_29 = eHF030600T2_29;
	}

	public int getEHF030600T2_30() {
		return EHF030600T2_30;
	}

	public void setEHF030600T2_30(int eHF030600T2_30) {
		EHF030600T2_30 = eHF030600T2_30;
	}

	public float getEHF030600T2_31() {
		return EHF030600T2_31;
	}

	public void setEHF030600T2_31(float eHF030600T2_31) {
		EHF030600T2_31 = eHF030600T2_31;
	}

	public String getEHF030600T2_INS_TYPE() {
		return EHF030600T2_INS_TYPE;
	}

	public void setEHF030600T2_INS_TYPE(String eHF030600T2INSTYPE) {
		EHF030600T2_INS_TYPE = eHF030600T2INSTYPE;
	}
	
	
	
	
}
