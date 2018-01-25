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

public class EHF030600M0F extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	private boolean CHECKED;
	private boolean CHANGED;
	
	private String EHF030600T0_01;
	private String EHF030600T0_U;
	private String EHF030600T0_M;
	private String EHF030600T0_02;
	private String EHF030600T0_03;
	private String EHF030600T0_03_TXT;
	private String EHF030600T0_04;
	private String EHF030600T0_05;
	private String EHF030600T0_06;
	private String EHF030600T0_07;
	private String EHF030600T0_08;
	private String EHF030600T0_09;
	private String EHF030600T0_10;
	private String EHF030600T0_11;
	private String EHF030600T0_12;
	private String EHF030600T0_13;
	private String EHF030600T0_14;
	private String EHF030600T0_C;
	private String EHF030600T0_AM;
	private String EHF030600T0_SCP;
	private String EHF030600T0_AFK;
	
	private String TESTID;
	private String LOGIN_DATE;
	private String DO_TRANSFER_DATE;
	
	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_CREATE;
	private String DATE_UPDATE;
	private String print_list;
	
	public String getPrint_list() {
		return print_list;
	}

	public void setPrint_list(String printList) {
		print_list = printList;
	}

	private List EHF030600C = new ArrayList();
	private List EHF030600C1 = new ArrayList();
	
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;
	}
	
	public ActionErrors validate(ActionMapping mapping,	HttpServletRequest request, Connection conn) {
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(l_actionErrors, EHF030600T0_M, "EHF030600T0_M", "不可空白");
		ve.isADYYMM(l_actionErrors, EHF030600T0_M, "EHF030600T0_M", "yyy/MM 格式錯誤");
		ve.isEmpty(l_actionErrors, EHF030600T0_02, "EHF030600T0_02", "不可空白");
		ve.isADYYMM(l_actionErrors, EHF030600T0_02, "EHF030600T0_02", "yyy/MM 格式錯誤");
		ve.isEmpty(l_actionErrors, EHF030600T0_03, "EHF030600T0_03", "不可空白");
		ve.isEmpty(l_actionErrors, EHF030600T0_04, "EHF030600T0_04", "不可空白");
		ve.isEmpty(l_actionErrors, EHF030600T0_07, "EHF030600T0_07", "不可空白");
		
		if(EHF030600T0_04.equals("01")){
//			ve.isEmpty(l_actionErrors, EHF030600T0_05, "EHF030600T0_05", "不可空白");
			
			if(EHF030600T0_05.equals("01") || EHF030600T0_05.equals("02")){
//				ve.isNumEmpty(l_actionErrors, EHF030600T0_06, "EHF030600T0_05", "天數不可空白", true);
//				ve.isEmpty(l_actionErrors, EHF030600T0_06, "EHF030600T0_05", "  天數不可空白");
			}
		}
		
		if(EHF030600T0_07.equals("01")){
//			ve.isNumEmpty(l_actionErrors, EHF030600T0_08, "EHF030600T0_07", "本薪比例不可空白", true);
//			ve.isEmpty(l_actionErrors, EHF030600T0_08, "EHF030600T0_07", "  本薪比例不可空白");
		}else if(EHF030600T0_07.equals("02")){
//			ve.isNumEmpty(l_actionErrors, EHF030600T0_09, "EHF030600T0_07", "固定金額不可空白", true);
//			ve.isEmpty(l_actionErrors, EHF030600T0_09, "EHF030600T0_07", "  固定金額不可空白");
		}
		
		
		if (l_actionErrors.isEmpty()) {
			
			if ( request.getAttribute("action").equals("addData") || request.getAttribute("action").equals("saveData") ) {
				try{
					Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
					
					//更改程式邏輯：同一個薪資年月只能產生一筆薪資計算資料, 但不同的薪資年月可以在同一個入扣帳年月進行扣帳 by joe 2012/03/12
					String sql = "" +
					" SELECT EHF030600T0_01, EHF030600T0_U " +
					" FROM EHF030600T0 " +
					" WHERE 1=1 " +
					" AND EHF030600T0_01 <> '"+EHF030600T0_01+"' " + //薪資計算序號
					//" AND EHF030600T0_M = '"+EHF030600T0_M+"' " +  //入扣帳年月
					" AND EHF030600T0_02 = '"+EHF030600T0_02+"' " +  //薪資年月
					" AND EHF030600T0_04 = '"+EHF030600T0_04+"' " +  //發放類別
					" AND EHF030600T0_14 = '"+request.getAttribute("compid")+"' ";  //公司代碼
					
					ResultSet rs = stmt.executeQuery(sql);
					
					if(rs.next()){
						
						//l_actionErrors.add("EHF030600T0_M",new ActionMessage(""));  //入扣帳年月
						l_actionErrors.add("EHF030600T0_02",new ActionMessage(""));  //薪資年月
						request.setAttribute("ErrMSG",
								(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
								" 該薪資年月已有薪資計算資料,請確認後再建立資料!! ");
					}
					
					rs.close();
					stmt.close();
				
				}catch(Exception e){
					System.out.println(" EHF030600M0F.validate() "+e);
					e.printStackTrace();
				}
			}
			
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
			//計算薪資時判斷條件
			if (request.getAttribute("action").equals("countSalary")) {
				countSalary_validate(l_actionErrors, request, conn);
			}
			
		}

		return l_actionErrors;
	}
	
	private void countSalary_validate(ActionErrors errors,HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		try{
			if(!this.chack(request, conn)){
				errors.add("EHF030600T0_02",new ActionMessage(""));  //薪資年月
				request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+ " 請確認"+EHF030600T0_02+"考勤資料是否有確認!! ");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void saveData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
	}
	private void addData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		try{
			if(!this.chack(request, conn)){
				errors.add("EHF030600T0_02",new ActionMessage(""));  //薪資年月
				request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+ " 請確認"+EHF030600T0_02+"考勤資料是否有確認!! ");
			}
		
		}catch(Exception e){
			System.out.println(" EHF030600M0F.validate() "+e);
			e.printStackTrace();
		}
	}
	
	
	private void addDetailData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub

	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("utf-8");
			
			EHF030600C = ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new EHF030600M0F();
				            }
			 });
			
			EHF030600C1 = ListUtils.lazyList(new ArrayList(),new Factory() {
	            public Object create() {
	                return new EHF030600M0F();
	            }
			});
			
		} catch (Exception e) {
		}

	}
	/**
	 * 搜尋該薪資年月的考勤是否確認
	 * @param request
	 * @param conn
	 */
	private boolean chack(HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		boolean check=false;
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

			String sql = "" +
			" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02 " +
			" FROM EHF020900T0 " +
			" WHERE 1=1 " +
			" AND EHF020900T0_M2 = '"+EHF030600T0_02+"'"+	//薪資年月
			" AND EHF020900T0_04 = '"+request.getAttribute("compid")+"'"; //公司代碼
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				//有考勤資料
				if(Integer.valueOf((String)rs.getString("EHF020900T0_02"))==2||Integer.valueOf((String)rs.getString("EHF020900T0_02"))==3){
					check=true;
				}	
			}
			rs.close();
			stmt.close();
		
		}catch(Exception e){
			System.out.println(" EHF030600M0F.validate() "+e);
			e.printStackTrace();
		}
		return check;
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

	public String getEHF030600T0_01() {
		return EHF030600T0_01;
	}

	public void setEHF030600T0_01(String eHF030600T0_01) {
		EHF030600T0_01 = eHF030600T0_01;
	}

	public String getEHF030600T0_U() {
		return EHF030600T0_U;
	}

	public void setEHF030600T0_U(String eHF030600T0U) {
		EHF030600T0_U = eHF030600T0U;
	}

	public String getEHF030600T0_M() {
		return EHF030600T0_M;
	}

	public void setEHF030600T0_M(String eHF030600T0M) {
		EHF030600T0_M = eHF030600T0M;
	}

	public String getEHF030600T0_02() {
		return EHF030600T0_02;
	}

	public void setEHF030600T0_02(String eHF030600T0_02) {
		EHF030600T0_02 = eHF030600T0_02;
	}

	public String getEHF030600T0_03() {
		return EHF030600T0_03;
	}

	public void setEHF030600T0_03(String eHF030600T0_03) {
		EHF030600T0_03 = eHF030600T0_03;
	}

	public String getEHF030600T0_03_TXT() {
		return EHF030600T0_03_TXT;
	}

	public void setEHF030600T0_03_TXT(String eHF030600T0_03TXT) {
		EHF030600T0_03_TXT = eHF030600T0_03TXT;
	}

	public String getEHF030600T0_04() {
		return EHF030600T0_04;
	}

	public void setEHF030600T0_04(String eHF030600T0_04) {
		EHF030600T0_04 = eHF030600T0_04;
	}

	public String getEHF030600T0_05() {
		return EHF030600T0_05;
	}

	public void setEHF030600T0_05(String eHF030600T0_05) {
		EHF030600T0_05 = eHF030600T0_05;
	}

	public String getEHF030600T0_06() {
		return EHF030600T0_06;
	}

	public void setEHF030600T0_06(String eHF030600T0_06) {
		EHF030600T0_06 = eHF030600T0_06;
	}

	public String getEHF030600T0_07() {
		return EHF030600T0_07;
	}

	public void setEHF030600T0_07(String eHF030600T0_07) {
		EHF030600T0_07 = eHF030600T0_07;
	}

	public String getEHF030600T0_08() {
		return EHF030600T0_08;
	}

	public void setEHF030600T0_08(String eHF030600T0_08) {
		EHF030600T0_08 = eHF030600T0_08;
	}

	public String getEHF030600T0_09() {
		return EHF030600T0_09;
	}

	public void setEHF030600T0_09(String eHF030600T0_09) {
		EHF030600T0_09 = eHF030600T0_09;
	}

	public String getEHF030600T0_10() {
		return EHF030600T0_10;
	}

	public void setEHF030600T0_10(String eHF030600T0_10) {
		EHF030600T0_10 = eHF030600T0_10;
	}

	public String getEHF030600T0_11() {
		return EHF030600T0_11;
	}

	public void setEHF030600T0_11(String eHF030600T0_11) {
		EHF030600T0_11 = eHF030600T0_11;
	}

	public String getEHF030600T0_12() {
		return EHF030600T0_12;
	}

	public void setEHF030600T0_12(String eHF030600T0_12) {
		EHF030600T0_12 = eHF030600T0_12;
	}

	public String getEHF030600T0_13() {
		return EHF030600T0_13;
	}

	public void setEHF030600T0_13(String eHF030600T0_13) {
		EHF030600T0_13 = eHF030600T0_13;
	}

	public String getEHF030600T0_14() {
		return EHF030600T0_14;
	}

	public void setEHF030600T0_14(String eHF030600T0_14) {
		EHF030600T0_14 = eHF030600T0_14;
	}

	public String getEHF030600T0_C() {
		return EHF030600T0_C;
	}

	public void setEHF030600T0_C(String eHF030600T0C) {
		EHF030600T0_C = eHF030600T0C;
	}

	public String getEHF030600T0_AM() {
		return EHF030600T0_AM;
	}

	public void setEHF030600T0_AM(String eHF030600T0AM) {
		EHF030600T0_AM = eHF030600T0AM;
	}

	public String getEHF030600T0_SCP() {
		return EHF030600T0_SCP;
	}

	public void setEHF030600T0_SCP(String eHF030600T0SCP) {
		EHF030600T0_SCP = eHF030600T0SCP;
	}

	public String getEHF030600T0_AFK() {
		return EHF030600T0_AFK;
	}

	public void setEHF030600T0_AFK(String eHF030600T0AFK) {
		EHF030600T0_AFK = eHF030600T0AFK;
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

	public List getEHF030600C() {
		return EHF030600C;
	}

	public void setEHF030600C(List eHF030600C) {
		EHF030600C = eHF030600C;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List getEHF030600C1() {
		return EHF030600C1;
	}

	public void setEHF030600C1(List eHF030600C1) {
		EHF030600C1 = eHF030600C1;
	}

	public String getTESTID() {
		return TESTID;
	}

	public void setTESTID(String tESTID) {
		TESTID = tESTID;
	}

	public String getLOGIN_DATE() {
		return LOGIN_DATE;
	}

	public void setLOGIN_DATE(String lOGINDATE) {
		LOGIN_DATE = lOGINDATE;
	}

	public String getDO_TRANSFER_DATE() {
		return DO_TRANSFER_DATE;
	}

	public void setDO_TRANSFER_DATE(String dOTRANSFERDATE) {
		DO_TRANSFER_DATE = dOTRANSFERDATE;
	}

	
	
	
}
