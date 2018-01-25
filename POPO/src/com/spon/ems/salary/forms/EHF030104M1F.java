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

public class EHF030104M1F extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	private boolean CHECKED;
	private boolean CHANGED;

	private  String  EHF030104T0_01;		//勞保等級序號 	修改於20140120 BY 賴泓錡 
	private  String  EHF030104T0_02;		//勞保版本代碼 	修改於20140120 BY 賴泓錡 
	private  String  EHF030104T0_02_VERSION;//勞保版本名稱 	修改於20140120 BY 賴泓錡 
	private  String  EHF030104T0_03;		//公司組織(代號)修改於20140120 BY 賴泓錡 
	private  String  EHF030104T0_03_TXT;	//公司組織(名稱)修改於20140120 BY 賴泓錡 
	private  String  EHF030104T0_04;		//備註
	private  String  EHF030104T0_05;
	
	private  String  EHF030104T1_01;		//勞保等級序號
	private  String  EHF030104T1_02;		//勞保等級明細序號
	private  String  EHF030104T1_03;		//勞保投保級距
	private  String  EHF030104T1_04;		//個人負擔
	private  String  EHF030104T1_05;		//雇主負擔
	private  String  EHF030104T1_06;		//合計金額
	private  String  EHF030104T1_07;		//備註
	private  String  EHF030104T1_08;		//公司代碼
	private  int  EHF030104T1_09;			//勞保投保等級(顯示序號)


	private  String  USER_CREATE;
	private  String  USER_UPDATE;
	private   int  VERSION;
	private  String  DATE_UPDATE;
	
	private FormFile IMP_EHF030104;
	
	private List EHF030104C=new ArrayList();

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
			ve.isEmpty(l_actionErrors, EHF030104T0_02, "EHF030104T0_02", "不可空白");
			ve.isEmpty(l_actionErrors, EHF030104T0_02_VERSION, "EHF030104T0_02_VERSION", "不可空白");
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
	private void addData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		try{
			Statement stmt = conn.createStatement();
			String sql = "";
			
			sql += " SELECT * FROM EHF030104T0 " +
				   " WHERE 1=1 " +
				   " AND EHF030104T0_02 = '"+EHF030104T0_02+"' " +
				   " AND EHF030104T0_05 = '"+request.getAttribute("compid")+"' " ;
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				errors.add("EHF030104T0_02",new ActionMessage("代碼重複,請確認後再建立資料!!"));
				
			}
			
			rs.close();
			stmt.close();
		
		}catch (SQLException e) {
			System.out.println("EHF030101M0F.addData_validate()" + e);
		}

	}
	
	
	private void addDetailData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		BA_Vaildate ve=BA_Vaildate.getInstance();
		
		//ve.isEmpty(errors, EHF030104T1_02, "EHF030104T1_02", "不可空白");
		ve.isEmpty(errors, EHF030104T1_03, "EHF030104T1_03", "不可空白");
		
		//ve.isEmpty(errors, EHF030104T1_04, "EHF030104T1_04", "不可空白");
		//ve.isEmpty(errors, EHF030104T1_05, "EHF030104T1_05", "不可空白");
		//ve.isEmpty(errors, EHF030104T1_06, "EHF030104T1_06", "不可空白");
		
		if(errors.isEmpty()){
			try{
				Statement stmt = conn.createStatement();
				String sql = "";
			
				sql += " SELECT * FROM EHF030104T1 " +
				   	   " WHERE 1=1 " +
				   	   " AND EHF030104T1_01 = '"+EHF030104T0_01+"' " +
				   	   " AND EHF030104T1_03 = '"+EHF030104T1_03+"' " +
				   	   " AND EHF030104T1_08 = '"+request.getAttribute("compid")+"' " ;
			
				ResultSet rs = stmt.executeQuery(sql);
			
				if(rs.next()){
					errors.add("EHF030104T1_03",new ActionMessage("投保等級資料重複,請確認後再建立資料!!"));
					/*request.setAttribute("ErrMSG",
							(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
							"投保等級資料重複,請確認後再建立資料!! ");*/
				}
			
				rs.close();
				stmt.close();
		
			}catch (SQLException e) {
				System.out.println("EHF030104M1F.addData_validate()" + e);
			}
		}

	}
	
	
	private void delDataData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		try{
			Statement stmt = conn.createStatement();
			String sql = "";
			
			sql += " SELECT * FROM EHF030300T0 LEFT JOIN EHF030104T0 ON EHF030300T0_05_VERSION = EHF030104T0_01" +
				   " WHERE 1=1 " +
				   " AND EHF030300T0_07_VERSION = '"+EHF030104T0_01+"' " +
				   " AND EHF030300T0_14 = '"+request.getAttribute("compid")+"' " ;
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				errors.add("",new ActionMessage(""));
				request.setAttribute("MSG",
					(request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+
					"健保等級代碼："+rs.getString("EHF030104T0_02")+"已有員工使用，請勿刪除!! ");
			}
			
			rs.close();
			stmt.close();
		
		}catch (SQLException e) {
			System.out.println("EHF030104M1F.delDataData_validate()" + e);
		}
	}
	
	private void delDetailData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		int Listcount=0;
		
		
		List arrid = new ArrayList();
		
		for (int i = 0; i < EHF030104C.size(); i++) {
			EHF030103M1F ListForm = (EHF030103M1F) EHF030104C.get(i);
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
				EHF030104M1F ListForm = (EHF030104M1F) arrid.get(i);
				ListForm.getEHF030104T0_01();
				
				
				try{
					Statement stmt = conn.createStatement();
					String sql = "";
					
					sql += " SELECT * FROM EHF030300T0 " +
						   " WHERE 1=1 " +
						   " AND EHF030300T0_07 = '"+ListForm.getEHF030104T1_02()+"' " +
						   " AND EHF030300T0_07_VERSION = '"+EHF030104T0_01+"' " +
						   " AND EHF030300T0_14 = '"+request.getAttribute("compid")+"' " ;
					
					ResultSet rs = stmt.executeQuery(sql);
					
					if(rs.next()){
						errors.add("",new ActionMessage(""));
						request.setAttribute("ErrMSG",
							(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
							"投保等級："+ListForm.getEHF030104T1_02()+"已有員工使用，請勿刪除!! ");
					}
					
					rs.close();
					stmt.close();
				
				}catch (SQLException e) {
					System.out.println("EHF030104M1F.delDetailData_validate()" + e);
				}
			}	
		}
	}
	
	
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("utf-8");
			EHF030104C = ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new EHF030104M1F();
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
	public String getEHF030104T0_01() {
		return EHF030104T0_01;
	}
	public void setEHF030104T0_01(String eHF030104T0_01) {
		EHF030104T0_01 = eHF030104T0_01;
	}
	public String getEHF030104T0_02() {
		return EHF030104T0_02;
	}
	public void setEHF030104T0_02(String eHF030104T0_02) {
		EHF030104T0_02 = eHF030104T0_02;
	}
	public String getEHF030104T0_02_VERSION() {
		return EHF030104T0_02_VERSION;
	}
	public void setEHF030104T0_02_VERSION(String eHF030104T0_02VERSION) {
		EHF030104T0_02_VERSION = eHF030104T0_02VERSION;
	}
	public String getEHF030104T0_03() {
		return EHF030104T0_03;
	}
	public void setEHF030104T0_03(String eHF030104T0_03) {
		EHF030104T0_03 = eHF030104T0_03;
	}
	public String getEHF030104T0_03_TXT() {
		return EHF030104T0_03_TXT;
	}
	public void setEHF030104T0_03_TXT(String eHF030104T0_03TXT) {
		EHF030104T0_03_TXT = eHF030104T0_03TXT;
	}
	public String getEHF030104T0_04() {
		return EHF030104T0_04;
	}
	public void setEHF030104T0_04(String eHF030104T0_04) {
		EHF030104T0_04 = eHF030104T0_04;
	}
	public String getEHF030104T0_05() {
		return EHF030104T0_05;
	}
	public void setEHF030104T0_05(String eHF030104T0_05) {
		EHF030104T0_05 = eHF030104T0_05;
	}
	public String getEHF030104T1_01() {
		return EHF030104T1_01;
	}
	public void setEHF030104T1_01(String eHF030104T1_01) {
		EHF030104T1_01 = eHF030104T1_01;
	}
	public String getEHF030104T1_02() {
		return EHF030104T1_02;
	}
	public void setEHF030104T1_02(String eHF030104T1_02) {
		EHF030104T1_02 = eHF030104T1_02;
	}
	public String getEHF030104T1_03() {
		return EHF030104T1_03;
	}
	public void setEHF030104T1_03(String eHF030104T1_03) {
		EHF030104T1_03 = eHF030104T1_03;
	}
	public String getEHF030104T1_04() {
		return EHF030104T1_04;
	}
	public void setEHF030104T1_04(String eHF030104T1_04) {
		EHF030104T1_04 = eHF030104T1_04;
	}
	public String getEHF030104T1_05() {
		return EHF030104T1_05;
	}
	public void setEHF030104T1_05(String eHF030104T1_05) {
		EHF030104T1_05 = eHF030104T1_05;
	}
	public String getEHF030104T1_06() {
		return EHF030104T1_06;
	}
	public void setEHF030104T1_06(String eHF030104T1_06) {
		EHF030104T1_06 = eHF030104T1_06;
	}
	public String getEHF030104T1_07() {
		return EHF030104T1_07;
	}
	public void setEHF030104T1_07(String eHF030104T1_07) {
		EHF030104T1_07 = eHF030104T1_07;
	}
	public String getEHF030104T1_08() {
		return EHF030104T1_08;
	}
	public void setEHF030104T1_08(String eHF030104T1_08) {
		EHF030104T1_08 = eHF030104T1_08;
	}
	public int getEHF030104T1_09() {
		return EHF030104T1_09;
	}
	public void setEHF030104T1_09(int eHF030104T1_09) {
		EHF030104T1_09 = eHF030104T1_09;
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
	public List getEHF030104C() {
		return EHF030104C;
	}
	public void setEHF030104C(List eHF030104C) {
		EHF030104C = eHF030104C;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public FormFile getIMP_EHF030104() {
		return IMP_EHF030104;
	}
	public void setIMP_EHF030104(FormFile iMPEHF030104) {
		IMP_EHF030104 = iMPEHF030104;
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