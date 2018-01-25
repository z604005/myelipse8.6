package com.spon.ems.vacation.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.spon.ems.hr.forms.EHF010100M0F;
import com.spon.utils.util.BA_Vaildate;

public class EHF020401M0F extends EHF010100M0F  {
	
	private static final long serialVersionUID = 1L;
	private boolean CHECKED;
	private boolean CHANGED;
	
	protected  String  EHF020401T0_UID;
	protected  String  EHF020401T0_SUID;
	protected  String  EHF020401T0_TYPE;
	protected  String  EHF020401T0_01;
	protected  String  EHF020401T0_02;
	protected  String  EHF020401T0_02_EMP;
	protected  String  EHF020401T0_03;
	protected  String  EHF020401T0_04;
	protected  String  EHF020401T0_05;
	protected  String  EHF020401T0_05_DATE;
	protected  String  EHF020401T0_06;
	protected  String  EHF020401T0_07;
	protected  String  EHF020401T0_07_HH;
	protected  String  EHF020401T0_07_MM;
	protected  String  EHF020401T0_08;
	protected  String  EHF020401T0_08_FLE;
	protected  String  EHF020401T0_08_STATUS;
	protected  String  EHF020401T0_09;
	protected  String  EHF020401T0_09_DESC;
	protected  String  EHF020401T0_10;
	protected  String  EHF020401T0_11;
	protected  String  EHF020401T0_FIX;
	protected  String  EHF020401T0_FDS;
	protected  String  EHF020401T0_FID;
	protected  String  EHF020401T0_DATE;
	protected  String  EHF020401T0_PS;


	protected  String  USER_CREATE;
	protected  String  USER_UPDATE;
	protected   int  VERSION;
	protected  String  DATE_UPDATE;

	protected List EHF020401C=new ArrayList();

	protected String OVERTIME_DATA_FLAG = "";  //顯示加班考勤
	
	
	private  String  EHF010300T0_06;//假別名稱
	private  String  EHF010300T0_09;//日期區間(起)
	private  String  EHF010300T0_10;//日期區間(迄)
	private  String  EHF010300T0_11;//給薪休假原因
	
	
	
	
	protected  String  EHF020401T0_12;//員工在職狀況
	protected  String  EHF020401T0_13;//在職
	protected  String  EHF020401T0_14;//離職
	protected  String  EHF020401T0_15;//留職停薪
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public ActionErrors validate(ActionMapping mapping,	HttpServletRequest request, Connection conn) {
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(l_actionErrors, EHF020401T0_05, "EHF020401T0_05", "不可空白");
		//ve.isADYYMM(l_actionErrors, EHF020401T0_05, "EHF020401T0_05", "yyy/MM 格式錯誤");
		
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
	protected void saveData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
	}
	protected void addData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub	
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("utf-8");
			EHF020401C = ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new EHF020401M0F();
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
	public String getEHF020401T0_01() {
		return EHF020401T0_01;
	}
	public void setEHF020401T0_01(String eHF020401T0_01) {
		EHF020401T0_01 = eHF020401T0_01;
	}
	public String getEHF020401T0_02() {
		return EHF020401T0_02;
	}
	public void setEHF020401T0_02(String eHF020401T0_02) {
		EHF020401T0_02 = eHF020401T0_02;
	}
	public String getEHF020401T0_03() {
		return EHF020401T0_03;
	}
	public void setEHF020401T0_03(String eHF020401T0_03) {
		EHF020401T0_03 = eHF020401T0_03;
	}
	public String getEHF020401T0_04() {
		return EHF020401T0_04;
	}
	public void setEHF020401T0_04(String eHF020401T0_04) {
		EHF020401T0_04 = eHF020401T0_04;
	}
	public String getEHF020401T0_05() {
		return EHF020401T0_05;
	}
	public void setEHF020401T0_05(String eHF020401T0_05) {
		EHF020401T0_05 = eHF020401T0_05;
	}
	public String getEHF020401T0_06() {
		return EHF020401T0_06;
	}
	public void setEHF020401T0_06(String eHF020401T0_06) {
		EHF020401T0_06 = eHF020401T0_06;
	}
	public String getEHF020401T0_07() {
		return EHF020401T0_07;
	}
	public void setEHF020401T0_07(String eHF020401T0_07) {
		EHF020401T0_07 = eHF020401T0_07;
	}
	public String getEHF020401T0_08() {
		return EHF020401T0_08;
	}
	public void setEHF020401T0_08(String eHF020401T0_08) {
		EHF020401T0_08 = eHF020401T0_08;
	}
	public String getEHF020401T0_08_FLE() {
		return EHF020401T0_08_FLE;
	}
	public void setEHF020401T0_08_FLE(String eHF020401T0_08FLE) {
		EHF020401T0_08_FLE = eHF020401T0_08FLE;
	}
	public String getEHF020401T0_09() {
		return EHF020401T0_09;
	}
	public void setEHF020401T0_09(String eHF020401T0_09) {
		EHF020401T0_09 = eHF020401T0_09;
	}
	public String getEHF020401T0_09_DESC() {
		return EHF020401T0_09_DESC;
	}
	public void setEHF020401T0_09_DESC(String eHF020401T0_09DESC) {
		EHF020401T0_09_DESC = eHF020401T0_09DESC;
	}
	public String getEHF020401T0_10() {
		return EHF020401T0_10;
	}
	public void setEHF020401T0_10(String eHF020401T0_10) {
		EHF020401T0_10 = eHF020401T0_10;
	}
	public String getEHF020401T0_11() {
		return EHF020401T0_11;
	}
	public void setEHF020401T0_11(String eHF020401T0_11) {
		EHF020401T0_11 = eHF020401T0_11;
	}
	public String getEHF020401T0_FIX() {
		return EHF020401T0_FIX;
	}
	public void setEHF020401T0_FIX(String eHF020401T0FIX) {
		EHF020401T0_FIX = eHF020401T0FIX;
	}
	public String getEHF020401T0_FDS() {
		return EHF020401T0_FDS;
	}
	public void setEHF020401T0_FDS(String eHF020401T0FDS) {
		EHF020401T0_FDS = eHF020401T0FDS;
	}
	public String getEHF020401T0_FID() {
		return EHF020401T0_FID;
	}
	public void setEHF020401T0_FID(String eHF020401T0FID) {
		EHF020401T0_FID = eHF020401T0FID;
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
	public List getEHF020401C() {
		return EHF020401C;
	}
	public void setEHF020401C(List eHF020401C) {
		EHF020401C = eHF020401C;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getEHF020401T0_08_STATUS() {
		return EHF020401T0_08_STATUS;
	}
	public void setEHF020401T0_08_STATUS(String eHF020401T0_08STATUS) {
		EHF020401T0_08_STATUS = eHF020401T0_08STATUS;
	}
	public String getEHF020401T0_DATE() {
		return EHF020401T0_DATE;
	}
	public void setEHF020401T0_DATE(String eHF020401T0DATE) {
		EHF020401T0_DATE = eHF020401T0DATE;
	}
	public String getEHF020401T0_UID() {
		return EHF020401T0_UID;
	}
	public void setEHF020401T0_UID(String eHF020401T0UID) {
		EHF020401T0_UID = eHF020401T0UID;
	}
	public String getEHF020401T0_TYPE() {
		return EHF020401T0_TYPE;
	}
	public void setEHF020401T0_TYPE(String eHF020401T0TYPE) {
		EHF020401T0_TYPE = eHF020401T0TYPE;
	}
	public String getEHF020401T0_SUID() {
		return EHF020401T0_SUID;
	}
	public void setEHF020401T0_SUID(String eHF020401T0SUID) {
		EHF020401T0_SUID = eHF020401T0SUID;
	}
	public String getEHF020401T0_PS() {
		return EHF020401T0_PS;
	}
	public void setEHF020401T0_PS(String eHF020401T0PS) {
		EHF020401T0_PS = eHF020401T0PS;
	}
	public String getEHF020401T0_05_DATE() {
		return EHF020401T0_05_DATE;
	}
	public void setEHF020401T0_05_DATE(String eHF020401T0_05DATE) {
		EHF020401T0_05_DATE = eHF020401T0_05DATE;
	}
	public String getEHF020401T0_07_HH() {
		return EHF020401T0_07_HH;
	}
	public void setEHF020401T0_07_HH(String eHF020401T0_07HH) {
		EHF020401T0_07_HH = eHF020401T0_07HH;
	}
	public String getEHF020401T0_07_MM() {
		return EHF020401T0_07_MM;
	}
	public void setEHF020401T0_07_MM(String eHF020401T0_07MM) {
		EHF020401T0_07_MM = eHF020401T0_07MM;
	}

	public String getEHF020401T0_02_EMP() {
		return EHF020401T0_02_EMP;
	}

	public void setEHF020401T0_02_EMP(String eHF020401T0_02EMP) {
		EHF020401T0_02_EMP = eHF020401T0_02EMP;
	}

	public String getOVERTIME_DATA_FLAG() {
		return OVERTIME_DATA_FLAG;
	}

	public void setOVERTIME_DATA_FLAG(String oVERTIMEDATAFLAG) {
		OVERTIME_DATA_FLAG = oVERTIMEDATAFLAG;
	}

	public String getEHF010300T0_06() {
		return EHF010300T0_06;
	}

	public void setEHF010300T0_06(String eHF010300T0_06) {
		EHF010300T0_06 = eHF010300T0_06;
	}

	public String getEHF010300T0_09() {
		return EHF010300T0_09;
	}

	public void setEHF010300T0_09(String eHF010300T0_09) {
		EHF010300T0_09 = eHF010300T0_09;
	}

	public String getEHF010300T0_10() {
		return EHF010300T0_10;
	}

	public void setEHF010300T0_10(String eHF010300T0_10) {
		EHF010300T0_10 = eHF010300T0_10;
	}

	public String getEHF010300T0_11() {
		return EHF010300T0_11;
	}

	public void setEHF010300T0_11(String eHF010300T0_11) {
		EHF010300T0_11 = eHF010300T0_11;
	}

	public String getEHF020401T0_12() {
		return EHF020401T0_12;
	}

	public void setEHF020401T0_12(String eHF020401T0_12) {
		EHF020401T0_12 = eHF020401T0_12;
	}

	public String getEHF020401T0_13() {
		return EHF020401T0_13;
	}

	public void setEHF020401T0_13(String eHF020401T0_13) {
		EHF020401T0_13 = eHF020401T0_13;
	}

	public String getEHF020401T0_14() {
		return EHF020401T0_14;
	}

	public void setEHF020401T0_14(String eHF020401T0_14) {
		EHF020401T0_14 = eHF020401T0_14;
	}

	public String getEHF020401T0_15() {
		return EHF020401T0_15;
	}

	public void setEHF020401T0_15(String eHF020401T0_15) {
		EHF020401T0_15 = eHF020401T0_15;
	}

}