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

public class EHF030600M1F extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	private boolean CHECKED;
	private boolean CHANGED;
	
	private String EHF030600T1_01;
	private String EHF030600T1_U;
	private String EHF030600T1_M;
	private String EHF030600T1_M1;
	private String EHF030600T1_CHIYEAR;
	private String EHF030600T1_02;
	private String EHF030600T1_03;
	private int EHF030600T1_04;
	private String EHF030600T1_04_DESC;
	private int EHF030600T1_04_O;
	private int EHF030600T1_041;
	private String EHF030600T1_041_DESC;
	private int EHF030600T1_05;
	private int EHF030600T1_05_D;
	private int EHF030600T1_06;
	private int EHF030600T1_07;
	private int EHF030600T1_071;
	private int EHF030600T1_072;
	private String EHF030600T1_08;
	private int EHF030600T1_09;
	private int EHF030600T1_10;
	private Float EHF030600T1_10_DAY;//加班換錢時數
	private int EHF030600T1_11;
	private int EHF030600T1_12;
	private int EHF030600T1_13;
	private String EHF030600T1_13_NUMBER;//建保使用版本
	private int EHF030600T1_14;
	private int EHF030600T1_15;
	private int EHF030600T1_16;
	private int EHF030600T1_17;
	private String EHF030600T1_17_NUMBER;//勞保使用版本
	private int EHF030600T1_18;
	private int EHF030600T1_19;
	private int EHF030600T1_20;
	private float EHF030600T1_21;
	private float EHF030600T1_22;
	private float EHF030600T1_23;
	private int EHF030600T1_24;
	private int EHF030600T1_25;
	private int EHF030600T1_26;
	private int EHF030600T1_27;
	private int EHF030600T1_28;
	private int EHF030600T1_29;
	private String EHF030600T1_30;
	private String EHF030600T1_DS;
	private String EHF030600T1_SCU;
	private String EHF030600T1_SCP;
	private String EHF030600T1_TYPE;

	
	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_CREATE;
	private String DATE_UPDATE;
	
	private List EHF030601C = new ArrayList();
	
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;
	}
	
	public ActionErrors validate(ActionMapping mapping,	HttpServletRequest request, Connection conn) {
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
//		ve.isEmpty(l_actionErrors, EHF030500T0_02, "EHF030500T0_02", "不可空白");
		
		
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
			
			EHF030601C = ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new EHF030600M1F();
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

	public String getEHF030600T1_01() {
		return EHF030600T1_01;
	}

	public void setEHF030600T1_01(String eHF030600T1_01) {
		EHF030600T1_01 = eHF030600T1_01;
	}

	public String getEHF030600T1_U() {
		return EHF030600T1_U;
	}

	public void setEHF030600T1_U(String eHF030600T1U) {
		EHF030600T1_U = eHF030600T1U;
	}

	public String getEHF030600T1_M() {
		return EHF030600T1_M;
	}

	public void setEHF030600T1_M(String eHF030600T1M) {
		EHF030600T1_M = eHF030600T1M;
	}

	public String getEHF030600T1_M1() {
		return EHF030600T1_M1;
	}

	public void setEHF030600T1_M1(String eHF030600T1M1) {
		EHF030600T1_M1 = eHF030600T1M1;
	}

	public String getEHF030600T1_02() {
		return EHF030600T1_02;
	}

	public void setEHF030600T1_02(String eHF030600T1_02) {
		EHF030600T1_02 = eHF030600T1_02;
	}

	public String getEHF030600T1_03() {
		return EHF030600T1_03;
	}

	public void setEHF030600T1_03(String eHF030600T1_03) {
		EHF030600T1_03 = eHF030600T1_03;
	}

	public int getEHF030600T1_04() {
		return EHF030600T1_04;
	}

	public void setEHF030600T1_04(int eHF030600T1_04) {
		EHF030600T1_04 = eHF030600T1_04;
	}

	public int getEHF030600T1_04_O() {
		return EHF030600T1_04_O;
	}

	public void setEHF030600T1_04_O(int eHF030600T1_04O) {
		EHF030600T1_04_O = eHF030600T1_04O;
	}

	public int getEHF030600T1_041() {
		return EHF030600T1_041;
	}

	public void setEHF030600T1_041(int eHF030600T1_041) {
		EHF030600T1_041 = eHF030600T1_041;
	}

	public int getEHF030600T1_05() {
		return EHF030600T1_05;
	}

	public void setEHF030600T1_05(int eHF030600T1_05) {
		EHF030600T1_05 = eHF030600T1_05;
	}

	public int getEHF030600T1_05_D() {
		return EHF030600T1_05_D;
	}

	public void setEHF030600T1_05_D(int eHF030600T1_05D) {
		EHF030600T1_05_D = eHF030600T1_05D;
	}

	public int getEHF030600T1_06() {
		return EHF030600T1_06;
	}

	public void setEHF030600T1_06(int eHF030600T1_06) {
		EHF030600T1_06 = eHF030600T1_06;
	}

	public int getEHF030600T1_07() {
		return EHF030600T1_07;
	}

	public void setEHF030600T1_07(int eHF030600T1_07) {
		EHF030600T1_07 = eHF030600T1_07;
	}

	public int getEHF030600T1_071() {
		return EHF030600T1_071;
	}

	public void setEHF030600T1_071(int eHF030600T1_071) {
		EHF030600T1_071 = eHF030600T1_071;
	}

	public int getEHF030600T1_072() {
		return EHF030600T1_072;
	}

	public void setEHF030600T1_072(int eHF030600T1_072) {
		EHF030600T1_072 = eHF030600T1_072;
	}

	public String getEHF030600T1_08() {
		return EHF030600T1_08;
	}

	public void setEHF030600T1_08(String eHF030600T1_08) {
		EHF030600T1_08 = eHF030600T1_08;
	}

	public int getEHF030600T1_09() {
		return EHF030600T1_09;
	}

	public void setEHF030600T1_09(int eHF030600T1_09) {
		EHF030600T1_09 = eHF030600T1_09;
	}

	public int getEHF030600T1_10() {
		return EHF030600T1_10;
	}

	public void setEHF030600T1_10(int eHF030600T1_10) {
		EHF030600T1_10 = eHF030600T1_10;
	}

	public Float getEHF030600T1_10_DAY() {
		return EHF030600T1_10_DAY;
	}

	public void setEHF030600T1_10_DAY(Float eEHF030600T1_10_DAY) {
		EHF030600T1_10_DAY = eEHF030600T1_10_DAY;
	}

	public int getEHF030600T1_11() {
		return EHF030600T1_11;
	}

	public void setEHF030600T1_11(int eHF030600T1_11) {
		EHF030600T1_11 = eHF030600T1_11;
	}

	public int getEHF030600T1_12() {
		return EHF030600T1_12;
	}

	public void setEHF030600T1_12(int eHF030600T1_12) {
		EHF030600T1_12 = eHF030600T1_12;
	}

	public int getEHF030600T1_13() {
		return EHF030600T1_13;
	}

	public void setEHF030600T1_13(int eHF030600T1_13) {
		EHF030600T1_13 = eHF030600T1_13;
	}

	public String getEHF030600T1_13_NUMBER() {
		return EHF030600T1_13_NUMBER;
	}

	public void setEHF030600T1_13_NUMBER(String eHF030600T1_13NUMBER) {
		EHF030600T1_13_NUMBER = eHF030600T1_13NUMBER;
	}

	public int getEHF030600T1_14() {
		return EHF030600T1_14;
	}

	public void setEHF030600T1_14(int eHF030600T1_14) {
		EHF030600T1_14 = eHF030600T1_14;
	}

	public int getEHF030600T1_15() {
		return EHF030600T1_15;
	}

	public void setEHF030600T1_15(int eHF030600T1_15) {
		EHF030600T1_15 = eHF030600T1_15;
	}

	public int getEHF030600T1_16() {
		return EHF030600T1_16;
	}

	public void setEHF030600T1_16(int eHF030600T1_16) {
		EHF030600T1_16 = eHF030600T1_16;
	}

	public int getEHF030600T1_17() {
		return EHF030600T1_17;
	}

	public void setEHF030600T1_17(int eHF030600T1_17) {
		EHF030600T1_17 = eHF030600T1_17;
	}

	public String getEHF030600T1_17_NUMBER() {
		return EHF030600T1_17_NUMBER;
	}

	public void setEHF030600T1_17_NUMBER(String eHF030600T1_17NUMBER) {
		EHF030600T1_17_NUMBER = eHF030600T1_17NUMBER;
	}

	public int getEHF030600T1_18() {
		return EHF030600T1_18;
	}

	public void setEHF030600T1_18(int eHF030600T1_18) {
		EHF030600T1_18 = eHF030600T1_18;
	}

	public int getEHF030600T1_19() {
		return EHF030600T1_19;
	}

	public void setEHF030600T1_19(int eHF030600T1_19) {
		EHF030600T1_19 = eHF030600T1_19;
	}

	public String getEHF030600T1_DS() {
		return EHF030600T1_DS;
	}

	public void setEHF030600T1_DS(String eHF030600T1DS) {
		EHF030600T1_DS = eHF030600T1DS;
	}

	public String getEHF030600T1_SCU() {
		return EHF030600T1_SCU;
	}

	public void setEHF030600T1_SCU(String eHF030600T1SCU) {
		EHF030600T1_SCU = eHF030600T1SCU;
	}

	public String getEHF030600T1_SCP() {
		return EHF030600T1_SCP;
	}

	public void setEHF030600T1_SCP(String eHF030600T1SCP) {
		EHF030600T1_SCP = eHF030600T1SCP;
	}

	public String getEHF030600T1_TYPE() {
		return EHF030600T1_TYPE;
	}

	public void setEHF030600T1_TYPE(String eHF030600T1TYPE) {
		EHF030600T1_TYPE = eHF030600T1TYPE;
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

	public String getEHF030600T1_04_DESC() {
		return EHF030600T1_04_DESC;
	}

	public void setEHF030600T1_04_DESC(String eHF030600T1_04DESC) {
		EHF030600T1_04_DESC = eHF030600T1_04DESC;
	}

	public String getEHF030600T1_041_DESC() {
		return EHF030600T1_041_DESC;
	}

	public void setEHF030600T1_041_DESC(String eHF030600T1_041DESC) {
		EHF030600T1_041_DESC = eHF030600T1_041DESC;
	}

	public String getEHF030600T1_CHIYEAR() {
		return EHF030600T1_CHIYEAR;
	}

	public void setEHF030600T1_CHIYEAR(String eHF030600T1CHIYEAR) {
		EHF030600T1_CHIYEAR = eHF030600T1CHIYEAR;
	}

	public int getEHF030600T1_20() {
		return EHF030600T1_20;
	}

	public void setEHF030600T1_20(int eHF030600T1_20) {
		EHF030600T1_20 = eHF030600T1_20;
	}

	public float getEHF030600T1_21() {
		return EHF030600T1_21;
	}

	public void setEHF030600T1_21(float eHF030600T1_21) {
		EHF030600T1_21 = eHF030600T1_21;
	}

	public float getEHF030600T1_22() {
		return EHF030600T1_22;
	}

	public void setEHF030600T1_22(float eHF030600T1_22) {
		EHF030600T1_22 = eHF030600T1_22;
	}

	public float getEHF030600T1_23() {
		return EHF030600T1_23;
	}

	public void setEHF030600T1_23(float eHF030600T1_23) {
		EHF030600T1_23 = eHF030600T1_23;
	}

	public int getEHF030600T1_24() {
		return EHF030600T1_24;
	}

	public void setEHF030600T1_24(int eHF030600T1_24) {
		EHF030600T1_24 = eHF030600T1_24;
	}

	public int getEHF030600T1_25() {
		return EHF030600T1_25;
	}

	public void setEHF030600T1_25(int eHF030600T1_25) {
		EHF030600T1_25 = eHF030600T1_25;
	}

	public int getEHF030600T1_26() {
		return EHF030600T1_26;
	}

	public void setEHF030600T1_26(int eHF030600T1_26) {
		EHF030600T1_26 = eHF030600T1_26;
	}

	public int getEHF030600T1_27() {
		return EHF030600T1_27;
	}

	public void setEHF030600T1_27(int eHF030600T1_27) {
		EHF030600T1_27 = eHF030600T1_27;
	}

	public int getEHF030600T1_28() {
		return EHF030600T1_28;
	}

	public void setEHF030600T1_28(int eHF030600T1_28) {
		EHF030600T1_28 = eHF030600T1_28;
	}

	public int getEHF030600T1_29() {
		return EHF030600T1_29;
	}

	public void setEHF030600T1_29(int eHF030600T1_29) {
		EHF030600T1_29 = eHF030600T1_29;
	}

	public String getEHF030600T1_30() {
		return EHF030600T1_30;
	}

	public void setEHF030600T1_30(String eHF030600T1_30) {
		EHF030600T1_30 = eHF030600T1_30;
	}	
}
