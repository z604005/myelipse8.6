package com.spon.ems.vacation.forms;

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
import org.apache.struts.upload.FormFile;

import com.spon.utils.util.BA_Vaildate;

public class EHF020402M0F extends ActionForm {
	private static final long serialVersionUID = 1L;
	private boolean CHECKED;
	private boolean CHANGED;

	private  String  EHF020402T0_01;		//截取序號
	private  String  EHF020402T0_02;		//截取資料時間
	private  String  EHF020402T0_02_HH;
	private  String  EHF020402T0_02_MM;
	private  String  EHF020402T0_03;		//檔案名稱
	private  String  EHF020402T0_04;		//截取檔案路徑
	private  String  EHF020402T0_05;		//備註
	private  String  EHF020402T0_06;		//公司代碼
	private  String  EHF020402T0_07;		//資料來源類型
	private  String  EHF020402T0_07_FLG;	//是否啟用
	private  String  EHF020402T0_08;		//資料格式
	
	
	private  String  EHF020402T0_08_01;//紀錄感應卡號的		開始位置
	private  String  EHF020402T0_08_02;//紀錄感應卡號的		結束位置
	
	private  String  EHF020402T0_08_03;//紀錄卡機來源的		開始位置
	private  String  EHF020402T0_08_04;//紀錄卡機來源的		結束位置
	
	private  String  EHF020402T0_08_05;//紀錄刷卡類別的		開始位置
	private  String  EHF020402T0_08_06;//紀錄刷卡類別的		結束位置
	
	private  String  EHF020402T0_08_07;//紀錄考勤日期的年		開始位置
	private  String  EHF020402T0_08_08;//紀錄考勤日期的年		結束位置
	
	private  String  EHF020402T0_08_09;//紀錄考勤日期的月		開始位置
	private  String  EHF020402T0_08_10;//紀錄考勤日期的月		結束位置
	
	private  String  EHF020402T0_08_11;//紀錄考勤日期的日		開始位置
	private  String  EHF020402T0_08_12;//紀錄考勤日期的日		結束位置
	
	private  String  EHF020402T0_08_13;//紀錄刷卡日期的年		開始位置
	private  String  EHF020402T0_08_14;//紀錄刷卡日期的年		結束位置
	
	private  String  EHF020402T0_08_15;//紀錄刷卡日期的月		開始位置
	private  String  EHF020402T0_08_16;//紀錄刷卡日期的月		結束位置
	
	private  String  EHF020402T0_08_17;//紀錄刷卡日期的日		開始位置
	private  String  EHF020402T0_08_18;//紀錄刷卡日期的日 	結束位置
	
	private  String  EHF020402T0_08_19;//紀錄刷卡時間的時		開始位置
	private  String  EHF020402T0_08_20;//紀錄刷卡時間的時		結束位置
	
	private  String  EHF020402T0_08_21;//紀錄刷卡時間的分		開始位置
	private  String  EHF020402T0_08_22;//紀錄刷卡時間的分		結束位置
	
	private  String  EHF020402T0_08_23;//紀錄刷卡時間的秒 	開始位置
	private  String  EHF020402T0_08_24;//紀錄刷卡時間的秒		結束位置
	
	private  String  EHF020402T0_08_25;//檔案日期格式
	private  String  EHF020402T0_08_26;//擷取參數
	private  String  EHF020402T0_08_27;//副檔名
	private  String  EHF020402T0_08_28;//擷取日期
	
	private FormFile UPLOADFILE;

	private  String  USER_CREATE;
	private  String  USER_UPDATE;
	private   int  VERSION;
	private  String  DATE_UPDATE;

	private List EHF020402C=new ArrayList();

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
		
		if("CSV".equals(EHF020402T0_07)){
			ve.isEmpty(l_actionErrors, EHF020402T0_03, "EHF020402T0_03", "不可空白");
			ve.isEmpty(l_actionErrors, EHF020402T0_04, "EHF020402T0_04", "不可空白");
			
		}

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
			EHF020402C = ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new EHF020402M0F();
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
	public String getEHF020402T0_01() {
		return EHF020402T0_01;
	}
	public void setEHF020402T0_01(String eHF020402T0_01) {
		EHF020402T0_01 = eHF020402T0_01;
	}
	public String getEHF020402T0_02() {
		return EHF020402T0_02;
	}
	public void setEHF020402T0_02(String eHF020402T0_02) {
		EHF020402T0_02 = eHF020402T0_02;
	}
	public String getEHF020402T0_02_HH() {
		return EHF020402T0_02_HH;
	}
	public void setEHF020402T0_02_HH(String eHF020402T0_02HH) {
		EHF020402T0_02_HH = eHF020402T0_02HH;
	}
	public String getEHF020402T0_02_MM() {
		return EHF020402T0_02_MM;
	}
	public void setEHF020402T0_02_MM(String eHF020402T0_02MM) {
		EHF020402T0_02_MM = eHF020402T0_02MM;
	}
	public String getEHF020402T0_03() {
		return EHF020402T0_03;
	}
	public void setEHF020402T0_03(String eHF020402T0_03) {
		EHF020402T0_03 = eHF020402T0_03;
	}
	public String getEHF020402T0_04() {
		return EHF020402T0_04;
	}
	public void setEHF020402T0_04(String eHF020402T0_04) {
		EHF020402T0_04 = eHF020402T0_04;
	}
	public String getEHF020402T0_05() {
		return EHF020402T0_05;
	}
	public void setEHF020402T0_05(String eHF020402T0_05) {
		EHF020402T0_05 = eHF020402T0_05;
	}
	public String getEHF020402T0_06() {
		return EHF020402T0_06;
	}
	public void setEHF020402T0_06(String eHF020402T0_06) {
		EHF020402T0_06 = eHF020402T0_06;
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
	public List getEHF020402C() {
		return EHF020402C;
	}
	public void setEHF020402C(List eHF020402C) {
		EHF020402C = eHF020402C;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getEHF020402T0_07() {
		return EHF020402T0_07;
	}
	public void setEHF020402T0_07(String eHF020402T0_07) {
		EHF020402T0_07 = eHF020402T0_07;
	}
	public String getEHF020402T0_08() {
		return EHF020402T0_08;
	}
	public void setEHF020402T0_08(String eHF020402T0_08) {
		EHF020402T0_08 = eHF020402T0_08;
	}
	public String getEHF020402T0_07_FLG() {
		return EHF020402T0_07_FLG;
	}
	public void setEHF020402T0_07_FLG(String eHF020402T0_07FLG) {
		EHF020402T0_07_FLG = eHF020402T0_07FLG;
	}
	public String getEHF020402T0_08_01() {
		return EHF020402T0_08_01;
	}
	public void setEHF020402T0_08_01(String eHF020402T0_08_01) {
		EHF020402T0_08_01 = eHF020402T0_08_01;
	}
	public String getEHF020402T0_08_02() {
		return EHF020402T0_08_02;
	}
	public void setEHF020402T0_08_02(String eHF020402T0_08_02) {
		EHF020402T0_08_02 = eHF020402T0_08_02;
	}
	public String getEHF020402T0_08_03() {
		return EHF020402T0_08_03;
	}
	public void setEHF020402T0_08_03(String eHF020402T0_08_03) {
		EHF020402T0_08_03 = eHF020402T0_08_03;
	}
	public String getEHF020402T0_08_04() {
		return EHF020402T0_08_04;
	}
	public void setEHF020402T0_08_04(String eHF020402T0_08_04) {
		EHF020402T0_08_04 = eHF020402T0_08_04;
	}
	public String getEHF020402T0_08_05() {
		return EHF020402T0_08_05;
	}
	public void setEHF020402T0_08_05(String eHF020402T0_08_05) {
		EHF020402T0_08_05 = eHF020402T0_08_05;
	}
	public String getEHF020402T0_08_06() {
		return EHF020402T0_08_06;
	}
	public void setEHF020402T0_08_06(String eHF020402T0_08_06) {
		EHF020402T0_08_06 = eHF020402T0_08_06;
	}
	public String getEHF020402T0_08_07() {
		return EHF020402T0_08_07;
	}
	public void setEHF020402T0_08_07(String eHF020402T0_08_07) {
		EHF020402T0_08_07 = eHF020402T0_08_07;
	}
	public String getEHF020402T0_08_08() {
		return EHF020402T0_08_08;
	}
	public void setEHF020402T0_08_08(String eHF020402T0_08_08) {
		EHF020402T0_08_08 = eHF020402T0_08_08;
	}
	public String getEHF020402T0_08_09() {
		return EHF020402T0_08_09;
	}
	public void setEHF020402T0_08_09(String eHF020402T0_08_09) {
		EHF020402T0_08_09 = eHF020402T0_08_09;
	}
	public String getEHF020402T0_08_10() {
		return EHF020402T0_08_10;
	}
	public void setEHF020402T0_08_10(String eHF020402T0_08_10) {
		EHF020402T0_08_10 = eHF020402T0_08_10;
	}
	public String getEHF020402T0_08_11() {
		return EHF020402T0_08_11;
	}
	public void setEHF020402T0_08_11(String eHF020402T0_08_11) {
		EHF020402T0_08_11 = eHF020402T0_08_11;
	}
	public String getEHF020402T0_08_12() {
		return EHF020402T0_08_12;
	}
	public void setEHF020402T0_08_12(String eHF020402T0_08_12) {
		EHF020402T0_08_12 = eHF020402T0_08_12;
	}
	public String getEHF020402T0_08_13() {
		return EHF020402T0_08_13;
	}
	public void setEHF020402T0_08_13(String eHF020402T0_08_13) {
		EHF020402T0_08_13 = eHF020402T0_08_13;
	}
	public String getEHF020402T0_08_14() {
		return EHF020402T0_08_14;
	}
	public void setEHF020402T0_08_14(String eHF020402T0_08_14) {
		EHF020402T0_08_14 = eHF020402T0_08_14;
	}
	public String getEHF020402T0_08_15() {
		return EHF020402T0_08_15;
	}
	public void setEHF020402T0_08_15(String eHF020402T0_08_15) {
		EHF020402T0_08_15 = eHF020402T0_08_15;
	}
	public String getEHF020402T0_08_16() {
		return EHF020402T0_08_16;
	}
	public void setEHF020402T0_08_16(String eHF020402T0_08_16) {
		EHF020402T0_08_16 = eHF020402T0_08_16;
	}
	public String getEHF020402T0_08_17() {
		return EHF020402T0_08_17;
	}
	public void setEHF020402T0_08_17(String eHF020402T0_08_17) {
		EHF020402T0_08_17 = eHF020402T0_08_17;
	}
	public String getEHF020402T0_08_18() {
		return EHF020402T0_08_18;
	}
	public void setEHF020402T0_08_18(String eHF020402T0_08_18) {
		EHF020402T0_08_18 = eHF020402T0_08_18;
	}
	public String getEHF020402T0_08_19() {
		return EHF020402T0_08_19;
	}
	public void setEHF020402T0_08_19(String eHF020402T0_08_19) {
		EHF020402T0_08_19 = eHF020402T0_08_19;
	}
	public String getEHF020402T0_08_20() {
		return EHF020402T0_08_20;
	}
	public void setEHF020402T0_08_20(String eHF020402T0_08_20) {
		EHF020402T0_08_20 = eHF020402T0_08_20;
	}
	public String getEHF020402T0_08_21() {
		return EHF020402T0_08_21;
	}
	public void setEHF020402T0_08_21(String eHF020402T0_08_21) {
		EHF020402T0_08_21 = eHF020402T0_08_21;
	}
	public String getEHF020402T0_08_22() {
		return EHF020402T0_08_22;
	}
	public void setEHF020402T0_08_22(String eHF020402T0_08_22) {
		EHF020402T0_08_22 = eHF020402T0_08_22;
	}
	public String getEHF020402T0_08_23() {
		return EHF020402T0_08_23;
	}
	public void setEHF020402T0_08_23(String eHF020402T0_08_23) {
		EHF020402T0_08_23 = eHF020402T0_08_23;
	}
	public String getEHF020402T0_08_24() {
		return EHF020402T0_08_24;
	}
	public void setEHF020402T0_08_24(String eHF020402T0_08_24) {
		EHF020402T0_08_24 = eHF020402T0_08_24;
	}
	public void setUPLOADFILE(FormFile uPLOADFILE) {
		UPLOADFILE = uPLOADFILE;
	}
	public FormFile getUPLOADFILE() {
		return UPLOADFILE;
	}
	public String getEHF020402T0_08_25() {
		return EHF020402T0_08_25;
	}
	public void setEHF020402T0_08_25(String eHF020402T0_08_25) {
		EHF020402T0_08_25 = eHF020402T0_08_25;
	}
	public String getEHF020402T0_08_26() {
		return EHF020402T0_08_26;
	}
	public void setEHF020402T0_08_26(String eHF020402T0_08_26) {
		EHF020402T0_08_26 = eHF020402T0_08_26;
	}
	public String getEHF020402T0_08_27() {
		return EHF020402T0_08_27;
	}
	public void setEHF020402T0_08_27(String eHF020402T0_08_27) {
		EHF020402T0_08_27 = eHF020402T0_08_27;
	}
	public String getEHF020402T0_08_28() {
		return EHF020402T0_08_28;
	}
	public void setEHF020402T0_08_28(String eHF020402T0_08_28) {
		EHF020402T0_08_28 = eHF020402T0_08_28;
	}
	
	
}