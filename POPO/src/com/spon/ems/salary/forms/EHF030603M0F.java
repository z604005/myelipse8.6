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

public class EHF030603M0F extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	private boolean CHECKED;
	private boolean CHANGED;
	
	private String EHF030603T0_U;
	private String EHF030603T0_01;
	private String EHF030603T0_01_TXT;
	private String EHF030603T0_02;
	private String EHF030603T0_02_TXT;
	private String EHF030603T0_M;
	private String EHF030603T0_M1;
	private String EHF030603T0_DS;
	private String EHF030603T0_03;
	private String EHF030603T0_SCP;
	private String EHF030603T0_07;
	private String EHF030603T0_SCU;
	
	private String EHF030603T1_U;
	private String EHF030603T1_DATE;
	private String EHF030603T1_01;
	private String EHF030603T1_01_DATE;
	private String EHF030603T1_01_HH;
	private String EHF030603T1_01_MM;
	private String EHF030603T1_02;
	private String EHF030603T1_02_DATE;
	private String EHF030603T1_02_HH;
	private String EHF030603T1_02_MM;
	private float EHF030603T1_03;
	private int EHF030603T1_04;
	private String EHF030603T1_05;
	private String EHF030603T1_05_OV;
	private String EHF030603T1_DS;
	private String EHF030603T1_06;
	private String EHF030603T1_07;
	
	private float HOUR_SUM;
	private int MONEY_SUM;

	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_CREATE;
	private String DATE_UPDATE;
	

	
	private List EHF030603C = new ArrayList();
	private List EHF030603C2 = new ArrayList();
	private List EHF030603C3 = new ArrayList();
	private List EHF030603C4 = new ArrayList();
	private List EHF030603C5 = new ArrayList();
	private List EHF030603C6 = new ArrayList();
	private List EHF030603C7 = new ArrayList();
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;
	}
	
	public ActionErrors validate(ActionMapping mapping,	HttpServletRequest request, Connection conn) {
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(l_actionErrors, EHF030603T0_01, "EHF030603T0_01", "不可空白");
		ve.isEmpty(l_actionErrors, EHF030603T0_02, "EHF030603T0_02", "不可空白");
		ve.isEmpty(l_actionErrors, EHF030603T0_M, "EHF030603T0_M", "不可空白");
		ve.isADYYMM(l_actionErrors, EHF030603T0_M, "EHF030603T0_M", "yyy/MM 格式錯誤");
		ve.isEmpty(l_actionErrors, EHF030603T0_M1, "EHF030603T0_M1", "不可空白");
		ve.isADYYMM(l_actionErrors, EHF030603T0_M1, "EHF030603T0_M1", "yyy/MM 格式錯誤");

		
		
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
			
			
			ve.isEmpty(errors, EHF030603T1_DATE, "EHF030603T1_DATE", "不可空白");
			ve.isNumEmpty(errors, String.valueOf(EHF030603T1_03), "EHF030603T1_03", "不可空白", true);
			ve.isNumEmpty(errors, String.valueOf(EHF030603T1_04), "EHF030603T1_04", "不可空白", true);
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = "" +
			" SELECT EHF030603T1_U, EHF030603T1_01, EHF030603T1_02 " +
			" FROM EHF030603T1 " +
			" WHERE 1=1 " +
			" AND EHF030603T1_U = '"+EHF030603T0_U+"' " +  //不休假加班費明細UID
			" AND EHF030603T1_DATE = '"+EHF030603T1_DATE+"' " +  //加班日期
			" AND EHF030603T1_07 = '"+request.getAttribute("compid")+"' ";  //公司代碼
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				errors.add("EHF030603T1_DATE",new ActionMessage(""));
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						" 該加班日期員工已有不休假加班費資料,請確認後再建立資料!! ");
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			System.out.println(" EHF030603M0F.validate() "+e);
			e.printStackTrace();
		}

	}
	
	private void chkaddDataExist(ActionErrors errors, HttpServletRequest request, Connection conn){
		
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = "" +
			" SELECT EHF030603T0_U " +
			" FROM EHF030603T0 " +
			" WHERE 1=1 " +
			" AND EHF030603T0_M = '"+EHF030603T0_M+"' " +  //入扣帳年月
			" AND EHF030603T0_M1 = '"+EHF030603T0_M1+"' " +  //薪資年月
			" AND EHF030603T0_01 = '"+EHF030603T0_01+"' " +  //員工工號
			" AND EHF030603T0_07 = '"+request.getAttribute("compid")+"' ";  //公司代碼
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				errors.add("EHF030603T0_M",new ActionMessage(""));
				errors.add("EHF030603T0_M1",new ActionMessage(""));
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						" 該入扣帳年月、薪資年月已有該員工的不休假加班費資料,請確認後再建立資料!! ");
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			System.out.println(" EHF030603M0F.validate() "+e);
			e.printStackTrace();
		}
		
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("utf-8");
			
			EHF030603C = ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new EHF030603M0F();
				            }
			 });
			
			EHF030603C2 = ListUtils.lazyList(new ArrayList(),new Factory() {
	            public Object create() {
	                return new EHF030603M0F();
	            }
			});
			
			EHF030603C3 = ListUtils.lazyList(new ArrayList(),new Factory() {
	            public Object create() {
	                return new EHF030603M0F();
	            }
			});
			
			EHF030603C4 = ListUtils.lazyList(new ArrayList(),new Factory() {
	            public Object create() {
	                return new EHF030603M0F();
	            }
			});
			
			EHF030603C5 = ListUtils.lazyList(new ArrayList(),new Factory() {
	            public Object create() {
	                return new EHF030603M0F();
	            }
			});
			
			EHF030603C6 = ListUtils.lazyList(new ArrayList(),new Factory() {
	            public Object create() {
	                return new EHF030603M0F();
	            }
			});
			
			EHF030603C7 = ListUtils.lazyList(new ArrayList(),new Factory() {
	            public Object create() {
	                return new EHF030603M0F();
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

	public String getEHF030603T0_U() {
		return EHF030603T0_U;
	}

	public void setEHF030603T0_U(String eHF030603T0U) {
		EHF030603T0_U = eHF030603T0U;
	}

	public String getEHF030603T0_01() {
		return EHF030603T0_01;
	}

	public void setEHF030603T0_01(String eHF030603T0_01) {
		EHF030603T0_01 = eHF030603T0_01;
	}

	public String getEHF030603T0_01_TXT() {
		return EHF030603T0_01_TXT;
	}

	public void setEHF030603T0_01_TXT(String eHF030603T0_01TXT) {
		EHF030603T0_01_TXT = eHF030603T0_01TXT;
	}

	public String getEHF030603T0_02() {
		return EHF030603T0_02;
	}

	public void setEHF030603T0_02(String eHF030603T0_02) {
		EHF030603T0_02 = eHF030603T0_02;
	}

	public String getEHF030603T0_02_TXT() {
		return EHF030603T0_02_TXT;
	}

	public void setEHF030603T0_02_TXT(String eHF030603T0_02TXT) {
		EHF030603T0_02_TXT = eHF030603T0_02TXT;
	}

	public String getEHF030603T0_M() {
		return EHF030603T0_M;
	}

	public void setEHF030603T0_M(String eHF030603T0M) {
		EHF030603T0_M = eHF030603T0M;
	}

	public String getEHF030603T0_M1() {
		return EHF030603T0_M1;
	}

	public void setEHF030603T0_M1(String eHF030603T0M1) {
		EHF030603T0_M1 = eHF030603T0M1;
	}

	public String getEHF030603T0_DS() {
		return EHF030603T0_DS;
	}

	public void setEHF030603T0_DS(String eHF030603T0DS) {
		EHF030603T0_DS = eHF030603T0DS;
	}

	public String getEHF030603T0_03() {
		return EHF030603T0_03;
	}

	public void setEHF030603T0_03(String eHF030603T0_03) {
		EHF030603T0_03 = eHF030603T0_03;
	}

	public String getEHF030603T0_SCP() {
		return EHF030603T0_SCP;
	}

	public void setEHF030603T0_SCP(String eHF030603T0SCP) {
		EHF030603T0_SCP = eHF030603T0SCP;
	}

	public String getEHF030603T0_07() {
		return EHF030603T0_07;
	}

	public void setEHF030603T0_07(String eHF030603T0_07) {
		EHF030603T0_07 = eHF030603T0_07;
	}

	public String getEHF030603T0_SCU() {
		return EHF030603T0_SCU;
	}

	public void setEHF030603T0_SCU(String eHF030603T0SCU) {
		EHF030603T0_SCU = eHF030603T0SCU;
	}

	public String getEHF030603T1_U() {
		return EHF030603T1_U;
	}

	public void setEHF030603T1_U(String eHF030603T1U) {
		EHF030603T1_U = eHF030603T1U;
	}

	public String getEHF030603T1_DATE() {
		return EHF030603T1_DATE;
	}

	public void setEHF030603T1_DATE(String eHF030603T1DATE) {
		EHF030603T1_DATE = eHF030603T1DATE;
	}

	public String getEHF030603T1_01() {
		return EHF030603T1_01;
	}

	public void setEHF030603T1_01(String eHF030603T1_01) {
		EHF030603T1_01 = eHF030603T1_01;
	}

	public String getEHF030603T1_01_DATE() {
		return EHF030603T1_01_DATE;
	}

	public void setEHF030603T1_01_DATE(String eHF030603T1_01DATE) {
		EHF030603T1_01_DATE = eHF030603T1_01DATE;
	}

	public String getEHF030603T1_01_HH() {
		return EHF030603T1_01_HH;
	}

	public void setEHF030603T1_01_HH(String eHF030603T1_01HH) {
		EHF030603T1_01_HH = eHF030603T1_01HH;
	}

	public String getEHF030603T1_01_MM() {
		return EHF030603T1_01_MM;
	}

	public void setEHF030603T1_01_MM(String eHF030603T1_01MM) {
		EHF030603T1_01_MM = eHF030603T1_01MM;
	}

	public String getEHF030603T1_02() {
		return EHF030603T1_02;
	}

	public void setEHF030603T1_02(String eHF030603T1_02) {
		EHF030603T1_02 = eHF030603T1_02;
	}

	public String getEHF030603T1_02_DATE() {
		return EHF030603T1_02_DATE;
	}

	public void setEHF030603T1_02_DATE(String eHF030603T1_02DATE) {
		EHF030603T1_02_DATE = eHF030603T1_02DATE;
	}

	public String getEHF030603T1_02_HH() {
		return EHF030603T1_02_HH;
	}

	public void setEHF030603T1_02_HH(String eHF030603T1_02HH) {
		EHF030603T1_02_HH = eHF030603T1_02HH;
	}

	public String getEHF030603T1_02_MM() {
		return EHF030603T1_02_MM;
	}

	public void setEHF030603T1_02_MM(String eHF030603T1_02MM) {
		EHF030603T1_02_MM = eHF030603T1_02MM;
	}

	public float getEHF030603T1_03() {
		return EHF030603T1_03;
	}

	public void setEHF030603T1_03(float eHF030603T1_03) {
		EHF030603T1_03 = eHF030603T1_03;
	}

	public int getEHF030603T1_04() {
		return EHF030603T1_04;
	}

	public void setEHF030603T1_04(int eHF030603T1_04) {
		EHF030603T1_04 = eHF030603T1_04;
	}

	public String getEHF030603T1_05() {
		return EHF030603T1_05;
	}

	public void setEHF030603T1_05(String eHF030603T1_05) {
		EHF030603T1_05 = eHF030603T1_05;
	}

	public String getEHF030603T1_DS() {
		return EHF030603T1_DS;
	}

	public void setEHF030603T1_DS(String eHF030603T1DS) {
		EHF030603T1_DS = eHF030603T1DS;
	}

	public String getEHF030603T1_06() {
		return EHF030603T1_06;
	}

	public void setEHF030603T1_06(String eHF030603T1_06) {
		EHF030603T1_06 = eHF030603T1_06;
	}

	public String getEHF030603T1_07() {
		return EHF030603T1_07;
	}

	public void setEHF030603T1_07(String eHF030603T1_07) {
		EHF030603T1_07 = eHF030603T1_07;
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

	public List getEHF030603C() {
		return EHF030603C;
	}

	public void setEHF030603C(List eHF030603C) {
		EHF030603C = eHF030603C;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getEHF030603T1_05_OV() {
		return EHF030603T1_05_OV;
	}

	public void setEHF030603T1_05_OV(String eHF030603T1_05OV) {
		EHF030603T1_05_OV = eHF030603T1_05OV;
	}

	public List getEHF030603C2() {
		return EHF030603C2;
	}

	public void setEHF030603C2(List eHF030603C2) {
		EHF030603C2 = eHF030603C2;
	}

	public List getEHF030603C3() {
		return EHF030603C3;
	}

	public void setEHF030603C3(List eHF030603C3) {
		EHF030603C3 = eHF030603C3;
	}

	public List getEHF030603C4() {
		return EHF030603C4;
	}

	public void setEHF030603C4(List eHF030603C4) {
		EHF030603C4 = eHF030603C4;
	}

	public List getEHF030603C5() {
		return EHF030603C5;
	}

	public void setEHF030603C5(List eHF030603C5) {
		EHF030603C5 = eHF030603C5;
	}

	public List getEHF030603C6() {
		return EHF030603C6;
	}

	public void setEHF030603C6(List eHF030603C6) {
		EHF030603C6 = eHF030603C6;
	}

	public List getEHF030603C7() {
		return EHF030603C7;
	}

	public void setEHF030603C7(List eHF030603C7) {
		EHF030603C7 = eHF030603C7;
	}
	
	
	
	
}
