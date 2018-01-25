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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.spon.utils.util.BA_Vaildate;

public class EHF030103M1F extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	private boolean CHECKED;
	private boolean CHANGED;

	private  String  EHF030103T0_01;		//健保等級序號
	private  String  EHF030103T0_02;		//健保版本代碼
	private  String  EHF030103T0_02_VERSION;//健保版本名稱
	private  String  EHF030103T0_03;		//公司組織(代號)
	private  String  EHF030103T0_03_TXT;	//公司組織(名稱)
	private  String  EHF030103T0_04;		//備註
	private  String  EHF030103T0_05;
	
	private  String  EHF030103T1_01;		//健保等級序號
	private  String  EHF030103T1_02;		//健保等級明細序號
	private  String  EHF030103T1_03;		//健保投保級距
	private  String  EHF030103T1_04;		//個人負擔
	private  String  EHF030103T1_05;		//雇主負擔
	private  String  EHF030103T1_06;		//合計金額
	private  String  EHF030103T1_07;		//備註
	private  String  EHF030103T1_08;		//公司代碼
	private  int  EHF030103T1_09;			//健保投保等級(顯示序號)


	private  String  USER_CREATE;
	private  String  USER_UPDATE;
	private   int  VERSION;
	private  String  DATE_UPDATE;
	
	private FormFile IMP_EHF030103;
	
	private List EHF030103C=new ArrayList();
	
	private   int  SHOW;//存放前端是否開放修改(0.不可修改、1.可修改)
	
	private  String  ERROR;		//存放匯入錯誤訊息
	
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
		
		if (!request.getAttribute("action").equals("delData")) {
			ve.isEmpty(l_actionErrors, EHF030103T0_02, "EHF030103T0_02", "不可空白");
			ve.isEmpty(l_actionErrors, EHF030103T0_02_VERSION, "EHF030103T0_02_VERSION", "不可空白");
		}
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
			//刪除明細判斷
			if (request.getAttribute("action").equals("delDetailData")) {
				delDetailData_validate(l_actionErrors, request, conn);
			}
			//刪除判斷
			if (request.getAttribute("action").equals("delData")) {
				delDataData_validate(l_actionErrors, request, conn);
			}
		}

		return l_actionErrors;
	}
	private void saveData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
	}
	
	
	private void delDataData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		try{
			Statement stmt = conn.createStatement();
			String sql = "";
			
			sql += " SELECT * FROM EHF030300T0 LEFT JOIN EHF030103T0 ON EHF030300T0_05_VERSION = EHF030103T0_01" +
				   " WHERE 1=1 " +
				   " AND EHF030300T0_05_VERSION = '"+EHF030103T0_01+"' " +
				   " AND EHF030300T0_14 = '"+request.getAttribute("compid")+"' " ;
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				errors.add("",new ActionMessage(""));
				request.setAttribute("MSG",
					(request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+
					"健保等級代碼："+rs.getString("EHF030103T0_02")+"已有員工使用，請勿刪除!! ");
			}
			
			rs.close();
			stmt.close();
		
		}catch (SQLException e) {
			System.out.println("EHF030103M1F.addData_validate()" + e);
		}
	}
	
	private void delDetailData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		int Listcount=0;
		
		
		List arrid = new ArrayList();
		
		for (int i = 0; i < EHF030103C.size(); i++) {
			EHF030103M1F ListForm = (EHF030103M1F) EHF030103C.get(i);
			if (ListForm.isCHECKED()) {
				Listcount++;
				arrid.add(ListForm);
			}
		}
		if(Listcount>=2){
			errors.add("",new ActionMessage(""));
			request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"請勿刪除多筆資料 ");
		}else if(Listcount==0){
			errors.add("",new ActionMessage(""));
			request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"請選擇一筆要刪除的資料。 ");
		}else{
			for (int i = 0; i < arrid.size(); i++) {
				EHF030103M1F ListForm = (EHF030103M1F) arrid.get(i);
				ListForm.getEHF030103T0_01();
				try{
					Statement stmt = conn.createStatement();
					String sql = "";
					
					sql += " SELECT * FROM EHF030300T0 " +
						   " WHERE 1=1 " +
						   " AND EHF030300T0_05 = '"+ListForm.getEHF030103T1_02()+"' " +
						   " AND EHF030300T0_05_VERSION = '"+EHF030103T0_01+"' " +
						   " AND EHF030300T0_14 = '"+request.getAttribute("compid")+"' " ;
					
					ResultSet rs = stmt.executeQuery(sql);
					
					if(rs.next()){
						errors.add("",new ActionMessage(""));
						request.setAttribute("ErrMSG",
							(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
							"投保等級："+ListForm.getEHF030103T1_02()+"已有員工使用，請勿刪除!! ");
					}
					
					rs.close();
					stmt.close();
				
				}catch (SQLException e) {
					System.out.println("EHF030103M1F.addData_validate()" + e);
				}
			}	
		}
	}
	private void addData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		try{
			Statement stmt = conn.createStatement();
			String sql = "";
			
			sql += " SELECT * FROM EHF030103T0 " +
				   " WHERE 1=1 " +
				   " AND EHF030103T0_02 = '"+EHF030103T0_02+"' " +
				   " AND EHF030103T0_05 = '"+request.getAttribute("compid")+"' " ;
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				errors.add("EHF030103T0_02",new ActionMessage("代碼重複,請確認後再建立資料!!"));
			//	request.setAttribute("ErrMSG",
			//			(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
			//			"該年度已有健保設定,請確認後再建立資料!! ");
			}
			
			rs.close();
			stmt.close();
		
		}catch (SQLException e) {
			System.out.println("EHF030103M1F.addData_validate()" + e);
		}

	}
	
	
	private void addDetailData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		BA_Vaildate ve=BA_Vaildate.getInstance();
		
		//ve.isEmpty(errors, EHF030103T1_02, "EHF030103T1_02", "不可空白");
		ve.isEmpty(errors, EHF030103T1_03, "EHF030103T1_03", "不可空白");
		
		//ve.isEmpty(errors, EHF030103T1_04, "EHF030103T1_04", "不可空白");
		//ve.isEmpty(errors, EHF030103T1_05, "EHF030103T1_05", "不可空白");
		//ve.isEmpty(errors, EHF030103T1_06, "EHF030103T1_06", "不可空白");
		
		if(errors.isEmpty()){
			try{
				Statement stmt = conn.createStatement();
				String sql = "";
			
				sql += " SELECT * FROM EHF030103T1 " +
				   	   " WHERE 1=1 " +
				   	   " AND EHF030103T1_01 = '"+EHF030103T0_01+"' " +
				   	   " AND EHF030103T1_03 = '"+EHF030103T1_03+"' " +
				   	   " AND EHF030103T1_08 = '"+request.getAttribute("compid")+"' " ;
			
				ResultSet rs = stmt.executeQuery(sql);
			
				if(rs.next()){
					errors.add("EHF030103T1_03",new ActionMessage("投保等級資料重複,請確認後再建立資料!!"));
					//request.setAttribute("ErrMSG",
					//		(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
					//		"投保等級資料重複,請確認後再建立資料!! ");
				}
			
				rs.close();
				stmt.close();
		
			}catch (SQLException e) {
				System.out.println("EHF030103M1F.addData_validate()" + e);
			}
		}

	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("utf-8");
			EHF030103C = ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new EHF030103M1F();
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
	public String getEHF030103T0_01() {
		return EHF030103T0_01;
	}
	public void setEHF030103T0_01(String eHF030103T0_01) {
		EHF030103T0_01 = eHF030103T0_01;
	}
	public String getEHF030103T0_02() {
		return EHF030103T0_02;
	}
	public void setEHF030103T0_02(String eHF030103T0_02) {
		EHF030103T0_02 = eHF030103T0_02;
	}
	public String getEHF030103T0_02_VERSION() {
		return EHF030103T0_02_VERSION;
	}
	public void setEHF030103T0_02_VERSION(String eHF030103T0_02VERSION) {
		EHF030103T0_02_VERSION = eHF030103T0_02VERSION;
	}
	public String getEHF030103T0_03() {
		return EHF030103T0_03;
	}
	public void setEHF030103T0_03(String eHF030103T0_03) {
		EHF030103T0_03 = eHF030103T0_03;
	}
	public String getEHF030103T0_03_TXT() {
		return EHF030103T0_03_TXT;
	}
	public void setEHF030103T0_03_TXT(String eHF030103T0_03TXT) {
		EHF030103T0_03_TXT = eHF030103T0_03TXT;
	}
	public String getEHF030103T0_04() {
		return EHF030103T0_04;
	}
	public void setEHF030103T0_04(String eHF030103T0_04) {
		EHF030103T0_04 = eHF030103T0_04;
	}
	public String getEHF030103T0_05() {
		return EHF030103T0_05;
	}
	public void setEHF030103T0_05(String eHF030103T0_05) {
		EHF030103T0_05 = eHF030103T0_05;
	}
	public String getEHF030103T1_01() {
		return EHF030103T1_01;
	}
	public void setEHF030103T1_01(String eHF030103T1_01) {
		EHF030103T1_01 = eHF030103T1_01;
	}
	public String getEHF030103T1_02() {
		return EHF030103T1_02;
	}
	public void setEHF030103T1_02(String eHF030103T1_02) {
		EHF030103T1_02 = eHF030103T1_02;
	}
	public String getEHF030103T1_03() {
		return EHF030103T1_03;
	}
	public void setEHF030103T1_03(String eHF030103T1_03) {
		EHF030103T1_03 = eHF030103T1_03;
	}
	public String getEHF030103T1_04() {
		return EHF030103T1_04;
	}
	public void setEHF030103T1_04(String eHF030103T1_04) {
		EHF030103T1_04 = eHF030103T1_04;
	}
	public String getEHF030103T1_05() {
		return EHF030103T1_05;
	}
	public void setEHF030103T1_05(String eHF030103T1_05) {
		EHF030103T1_05 = eHF030103T1_05;
	}
	public String getEHF030103T1_06() {
		return EHF030103T1_06;
	}
	public void setEHF030103T1_06(String eHF030103T1_06) {
		EHF030103T1_06 = eHF030103T1_06;
	}
	public String getEHF030103T1_07() {
		return EHF030103T1_07;
	}
	public void setEHF030103T1_07(String eHF030103T1_07) {
		EHF030103T1_07 = eHF030103T1_07;
	}
	public String getEHF030103T1_08() {
		return EHF030103T1_08;
	}
	public void setEHF030103T1_08(String eHF030103T1_08) {
		EHF030103T1_08 = eHF030103T1_08;
	}
	
	public int getEHF030103T1_09() {
		return EHF030103T1_09;
	}
	public void setEHF030103T1_09(int eHF030103T1_09) {
		EHF030103T1_09 = eHF030103T1_09;
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
	public List getEHF030103C() {
		return EHF030103C;
	}
	public void setEHF030103C(List eHF030103C) {
		EHF030103C = eHF030103C;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public FormFile getIMP_EHF030103() {
		return IMP_EHF030103;
	}
	public void setIMP_EHF030103(FormFile iMPEHF030103) {
		IMP_EHF030103 = iMPEHF030103;
	}
	public int getSHOW() {
		return SHOW;
	}
	public void setSHOW(int sHOW) {
		SHOW = sHOW;
	}
	public String getERROR() {
		return ERROR;
	}
	public void setERROR(String eRROR) {
		ERROR = eRROR;
	}
}