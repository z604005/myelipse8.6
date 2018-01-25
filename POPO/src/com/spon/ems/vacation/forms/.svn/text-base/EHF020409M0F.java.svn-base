package com.spon.ems.vacation.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.spon.utils.util.BA_Vaildate;

public class EHF020409M0F extends ActionForm {
	
	
	

	private  String EHF020409T0_01;
	private  String EHF020409T0_02;
	private  String EHF020409T0_03;
	private  String EHF020409T0_04;
	private  String EHF020409T0_05;
	private  String EHF020409T0_06;
	private  String EHF020409T0_07;
	private  String EHF020409T0_08;
	private  String EHF020409T0_09;
	private  String EHF020409T0_10;
	private  String EHF020409T0_11;
	private  String EHF020409T0_12;
	private  String EHF020409T0_13;
	private  String EHF020409T0_14;
	private  String EHF020409T0_15;
	private  String EHF020409T0_16;
	private  String EHF020409T0_17;
	private  String EHF020409T0_18;
	private  String EHF020409T0_19;
	private  String EHF020409T0_20;
	private  String EHF020409T0_21;
	private  String EHF020409T0_22;
	private  String EHF020409T0_23;
	private  String EHF020409T0_24;
	private  String EHF020409T0_25;
	
	
	
	private  String EHF020409T0_26;	//INT	10	遲到額外扣除_區段		"此四個欄位是提供給戴養菌做額外扣費用的
									//			當遲到扣薪類型選擇第三種""依區段條件""
									//			就會使用到"		
	private  String EHF020409T0_27;	//INT	10	遲到額外扣除_倍數				
	private  String EHF020409T0_28;	//BIT		遲到額外扣除_是否有不計費區段				
	private  String EHF020409T0_29;	//INT	10	遲到不計費區段				
	private  String EHF020409T0_30;	//INT	10	早退額外扣除_區段		"此四個欄位是提供給戴養菌做額外扣費用的
									//			當早退扣薪類型選擇第三種""依區段條件""
									//			就會使用到"		
	private  String EHF020409T0_31;	//INT	10	早退額外扣除_倍數				
	private  String EHF020409T0_32;	//BIT		早退額外扣除_是否有不計費區段				
	private  String EHF020409T0_33;	//INT	10	早退不計費區段				
	private  String EHF020409T0_34;	//INT	10	曠職特殊條件			此欄位提供給戴養菌做額外扣費用的		
	
	private  String EHF020409T0_35;	//INT	10	遲到扣薪金額(依區段條件)	此欄位提供給戴養菌做額外扣費用的		
	private  String EHF020409T0_36;	//INT	10	早退扣薪金額(依區段條件)	此欄位提供給戴養菌做額外扣費用的		
	private  String EHF020409T0_37;	//INT	10	曠職扣薪金額(依倍率條件)	此欄位提供給戴養菌做額外扣費用的		

	private  String  USER_CREATE;
	private  String  USER_UPDATE;
	private   int  VERSION;
	private  String  DATE_CREATE;
	private  String  DATE_UPDATE;
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
		
		//ve.isEmpty(l_actionErrors, EHF010102T0_02, "EHF010102T0_02", "不可空白");
		
		if (l_actionErrors.isEmpty()) {
			
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
			
		} catch (Exception e) {
		}

	}
	
	
	public String getEHF020409T0_01() {
		return EHF020409T0_01;
	}
	public void setEHF020409T0_01(String eHF020409T0_01) {
		EHF020409T0_01 = eHF020409T0_01;
	}
	public String getEHF020409T0_02() {
		return EHF020409T0_02;
	}
	public void setEHF020409T0_02(String eHF020409T0_02) {
		EHF020409T0_02 = eHF020409T0_02;
	}
	public String getEHF020409T0_03() {
		return EHF020409T0_03;
	}
	public void setEHF020409T0_03(String eHF020409T0_03) {
		EHF020409T0_03 = eHF020409T0_03;
	}
	public String getEHF020409T0_04() {
		return EHF020409T0_04;
	}
	public void setEHF020409T0_04(String eHF020409T0_04) {
		EHF020409T0_04 = eHF020409T0_04;
	}
	public String getEHF020409T0_05() {
		return EHF020409T0_05;
	}
	public void setEHF020409T0_05(String eHF020409T0_05) {
		EHF020409T0_05 = eHF020409T0_05;
	}
	public String getEHF020409T0_06() {
		return EHF020409T0_06;
	}
	public void setEHF020409T0_06(String eHF020409T0_06) {
		EHF020409T0_06 = eHF020409T0_06;
	}
	public String getEHF020409T0_07() {
		return EHF020409T0_07;
	}
	public void setEHF020409T0_07(String eHF020409T0_07) {
		EHF020409T0_07 = eHF020409T0_07;
	}
	public String getEHF020409T0_08() {
		return EHF020409T0_08;
	}
	public void setEHF020409T0_08(String eHF020409T0_08) {
		EHF020409T0_08 = eHF020409T0_08;
	}
	public String getEHF020409T0_09() {
		return EHF020409T0_09;
	}
	public void setEHF020409T0_09(String eHF020409T0_09) {
		EHF020409T0_09 = eHF020409T0_09;
	}
	public String getEHF020409T0_10() {
		return EHF020409T0_10;
	}
	public void setEHF020409T0_10(String eHF020409T0_10) {
		EHF020409T0_10 = eHF020409T0_10;
	}
	public String getEHF020409T0_11() {
		return EHF020409T0_11;
	}
	public void setEHF020409T0_11(String eHF020409T0_11) {
		EHF020409T0_11 = eHF020409T0_11;
	}
	public String getEHF020409T0_12() {
		return EHF020409T0_12;
	}
	public void setEHF020409T0_12(String eHF020409T0_12) {
		EHF020409T0_12 = eHF020409T0_12;
	}
	public String getEHF020409T0_13() {
		return EHF020409T0_13;
	}
	public void setEHF020409T0_13(String eHF020409T0_13) {
		EHF020409T0_13 = eHF020409T0_13;
	}
	public String getEHF020409T0_14() {
		return EHF020409T0_14;
	}
	public void setEHF020409T0_14(String eHF020409T0_14) {
		EHF020409T0_14 = eHF020409T0_14;
	}
	public String getEHF020409T0_15() {
		return EHF020409T0_15;
	}
	public void setEHF020409T0_15(String eHF020409T0_15) {
		EHF020409T0_15 = eHF020409T0_15;
	}
	public String getEHF020409T0_16() {
		return EHF020409T0_16;
	}
	public void setEHF020409T0_16(String eHF020409T0_16) {
		EHF020409T0_16 = eHF020409T0_16;
	}
	public String getEHF020409T0_17() {
		return EHF020409T0_17;
	}
	public void setEHF020409T0_17(String eHF020409T0_17) {
		EHF020409T0_17 = eHF020409T0_17;
	}
	public String getEHF020409T0_18() {
		return EHF020409T0_18;
	}
	public void setEHF020409T0_18(String eHF020409T0_18) {
		EHF020409T0_18 = eHF020409T0_18;
	}
	public String getEHF020409T0_19() {
		return EHF020409T0_19;
	}
	public void setEHF020409T0_19(String eHF020409T0_19) {
		EHF020409T0_19 = eHF020409T0_19;
	}
	public String getEHF020409T0_20() {
		return EHF020409T0_20;
	}
	public void setEHF020409T0_20(String eHF020409T0_20) {
		EHF020409T0_20 = eHF020409T0_20;
	}
	public String getEHF020409T0_21() {
		return EHF020409T0_21;
	}
	public void setEHF020409T0_21(String eHF020409T0_21) {
		EHF020409T0_21 = eHF020409T0_21;
	}
	public String getEHF020409T0_22() {
		return EHF020409T0_22;
	}
	public void setEHF020409T0_22(String eHF020409T0_22) {
		EHF020409T0_22 = eHF020409T0_22;
	}
	public String getEHF020409T0_23() {
		return EHF020409T0_23;
	}
	public void setEHF020409T0_23(String eHF020409T0_23) {
		EHF020409T0_23 = eHF020409T0_23;
	}
	public String getEHF020409T0_24() {
		return EHF020409T0_24;
	}
	public void setEHF020409T0_24(String eHF020409T0_24) {
		EHF020409T0_24 = eHF020409T0_24;
	}
	public String getEHF020409T0_25() {
		return EHF020409T0_25;
	}
	public void setEHF020409T0_25(String eHF020409T0_25) {
		EHF020409T0_25 = eHF020409T0_25;
	}
	public String getEHF020409T0_26() {
		return EHF020409T0_26;
	}
	public void setEHF020409T0_26(String eHF020409T0_26) {
		EHF020409T0_26 = eHF020409T0_26;
	}
	public String getEHF020409T0_27() {
		return EHF020409T0_27;
	}
	public void setEHF020409T0_27(String eHF020409T0_27) {
		EHF020409T0_27 = eHF020409T0_27;
	}
	public String getEHF020409T0_28() {
		return EHF020409T0_28;
	}
	public void setEHF020409T0_28(String eHF020409T0_28) {
		EHF020409T0_28 = eHF020409T0_28;
	}
	public String getEHF020409T0_29() {
		return EHF020409T0_29;
	}
	public void setEHF020409T0_29(String eHF020409T0_29) {
		EHF020409T0_29 = eHF020409T0_29;
	}
	public String getEHF020409T0_30() {
		return EHF020409T0_30;
	}
	public void setEHF020409T0_30(String eHF020409T0_30) {
		EHF020409T0_30 = eHF020409T0_30;
	}
	public String getEHF020409T0_31() {
		return EHF020409T0_31;
	}
	public void setEHF020409T0_31(String eHF020409T0_31) {
		EHF020409T0_31 = eHF020409T0_31;
	}
	public String getEHF020409T0_32() {
		return EHF020409T0_32;
	}
	public void setEHF020409T0_32(String eHF020409T0_32) {
		EHF020409T0_32 = eHF020409T0_32;
	}
	public String getEHF020409T0_33() {
		return EHF020409T0_33;
	}
	public void setEHF020409T0_33(String eHF020409T0_33) {
		EHF020409T0_33 = eHF020409T0_33;
	}
	public String getEHF020409T0_34() {
		return EHF020409T0_34;
	}
	public void setEHF020409T0_34(String eHF020409T0_34) {
		EHF020409T0_34 = eHF020409T0_34;
	}
	public String getEHF020409T0_35() {
		return EHF020409T0_35;
	}
	public void setEHF020409T0_35(String eHF020409T0_35) {
		EHF020409T0_35 = eHF020409T0_35;
	}
	public String getEHF020409T0_36() {
		return EHF020409T0_36;
	}
	public void setEHF020409T0_36(String eHF020409T0_36) {
		EHF020409T0_36 = eHF020409T0_36;
	}
	
	public String getEHF020409T0_37() {
		return EHF020409T0_37;
	}
	public void setEHF020409T0_37(String eHF020409T0_37) {
		EHF020409T0_37 = eHF020409T0_37;
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
	
	
	
}