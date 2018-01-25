package com.spon.ems.vacation.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.ems.vacation.tools.EMS_CheckVacation;
import com.spon.utils.util.BA_Vaildate;

public class EHF020404M0F extends ActionForm implements ValidateForm{
	private	String	EHF020404T0_01;//刷卡資料序號
	private	String	EHF020404T0_02;//門禁系統序號
	private	String	EHF020404T0_03;//感應卡號
	private	String	EHF020404T0_04;//打卡日期
	private	String	EHF020404T0_05;//打卡時間
	private	String	EHF020404T0_06;//打卡時間日期
	private	String	EHF020404T0_07;//門禁機器代號
	private	String	EHF020404T0_08;//動作代碼
	private	String	EHF020404T0_09;//公司代碼
	private	String	EHF020404T0_10;//資料來源
	private	String	EHF020404T0_11;//暫存考勤時間(手動匯入資料用)
	private	String	EHF020404T0_FLAG;//確認狀態   2013/10/03 賴泓錡
	//private	int	EHF020404T0_SFK;//薪資處理狀態
	private	String	EHF020404T0_dep_UID;//組織單位(系統編號)
	private	String	EHF020404T0_dep;//組織單位
	private	String	EHF020404T0_dep_TXT;//組織單位(中文)
	
	private	String	EHF020404T0_emp_UID;//員工(系統編號)
	private	String	EHF020404T0_emp;//員工
	private	String	EHF020404T0_emp_TXT;//員工(中文)
	private	String	EHF020404T0_05_MM;//分
	private	String	EHF020404T0_05_HH;//時
	private	String	USER_CREATE;//建立人員
	private	String	USER_UPDATE;//修改人員
	private	int	VERSION;//版本
	private	String	DATE_UPDATE;//最後修改日期
	private List EHF020404T0_LIST = new ArrayList();
	
	
	private  String  EHF020404T0_12;//員工在職狀況
	private  String  EHF020404T0_13;//在職
	private  String  EHF020404T0_14;//離職
	private  String  EHF020404T0_15;//留職停薪

	
	
	private FormFile UPLOADFILE;
	
	public ActionErrors validate(ActionMapping mapping,	HttpServletRequest request, Connection conn) {
		ActionErrors l_actionErrors = new ActionErrors();
		
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		if (l_actionErrors.isEmpty()) {
			
			//新增時判斷條件
			if (request.getAttribute("action").equals("addData")) {
				addData_validate(l_actionErrors, request, conn);
			}

			//修改時判斷條件
			if (request.getAttribute("action").equals("saveData")) {
				saveData_validate(l_actionErrors, request, conn);
			}
			
			//刪除時判斷條件
			if (request.getAttribute("action").equals("delData")) {
				delData_validate(l_actionErrors, request, conn);
			}
			//查詢時判斷條件
			if (request.getAttribute("action").equals("queryData")) {
				queryData_validate(l_actionErrors, request, conn);
			}
			
		}

		return l_actionErrors;
	}
	//新增時的驗證
	private void addData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		try{
			//檢查不可為空白
			BA_Vaildate ve = BA_Vaildate.getInstance();
			ve.isEmpty(errors, EHF020404T0_dep, "EHF020404T0_dep", "不可空白");  //組織單位
			ve.isEmpty(errors, EHF020404T0_emp, "EHF020404T0_emp", "不可空白");  //員工
			ve.isEmpty(errors, EHF020404T0_03, "EHF020404T0_03", "不可空白");  //感應卡號
			ve.isEmpty(errors, EHF020404T0_11, "EHF020404T0_11", "不可空白");  //考勤日期
			ve.isEmpty(errors, EHF020404T0_06, "EHF020404T0_06", "不可空白");  //打卡日期時間	
			
			String EHF020404T0_01 = (EHF020404T0_06).replaceAll("/", "").trim()+ (EHF020404T0_05_HH).trim()+ (EHF020404T0_05_MM).trim()+ "00"+ (EHF020404T0_03).trim();
			
			String sql = "" +
			" SELECT * " +
			" FROM EHF020404t0" +
			" WHERE EHF020404T0_01='"+EHF020404T0_01+"'";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				//有找到跟本次衝突的門禁資料
				errors.add("EHF020404T0_06",new ActionMessage(""));
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						"時間重複,請再次檢查!! ");
			}
			
			rs.close();
			stmt.close();
			
			if(!"".equals(EHF020404T0_03) || EHF020404T0_03 != null){}
				
				String sql1 = " SELECT EHF020403T1_01,EHF020403T1_02,EHF020403T1_04_START,EHF020403T1_04_END " +
						     " FROM EHF020403T1 " +
						     " WHERE 1=1 " +
						     " AND EHF020403T1_02 = '"+EHF020404T0_03+"' " +
						     " AND '"+EHF020404T0_06+" "+EHF020404T0_05_HH+":"+EHF020404T0_05_MM+":00'"+" " +
						     " BETWEEN DATE_FORMAT(EHF020403T1_04_START, '%Y/%m/%d %H:%i:%s') AND DATE_FORMAT(EHF020403T1_04_END, '%Y/%m/%d %H:%i:%s')";
				
				try {
					Statement stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
					ResultSet rs1 = stmt1.executeQuery(sql1);
				
					if(rs1.next()){
						//有資料 符合條件 考勤日期在感應卡使用期間內
					}else{
						//無資料 不符合條件  考勤日期不在感應卡使用期間內
						errors.add("EHF020404T0_06",new ActionMessage("考勤日期不在感應卡使用期間內，請確認！！"));
					}
					rs1.close();
					stmt1.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	//查詢時的驗證
	private void queryData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
	}
	//儲存時判斷條件
	private void saveData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub	
		try{
			String sql = "" +
			" SELECT * " +
			" FROM EHF020404t0" +
			" WHERE EHF020401T0_01="+EHF020404T0_01+"";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				//有找到跟本次衝突的門禁資料
				errors.add("EHF020404T0_06",new ActionMessage(""));
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						"時間重複,請再次檢查!! ");
			}
			
			rs.close();
			stmt.close();
	}catch(Exception e){
		e.printStackTrace();
	}
	}
	//刪除時判斷條件
	private void delData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub

	}
	public String getEHF020404T0_01() {
		return EHF020404T0_01;
	}
	public void setEHF020404T0_01(String eHF020404T0_01) {
		EHF020404T0_01 = eHF020404T0_01;
	}
	public String getEHF020404T0_02() {
		return EHF020404T0_02;
	}
	public void setEHF020404T0_02(String eHF020404T0_02) {
		EHF020404T0_02 = eHF020404T0_02;
	}
	public String getEHF020404T0_03() {
		return EHF020404T0_03;
	}
	public void setEHF020404T0_03(String eHF020404T0_03) {
		EHF020404T0_03 = eHF020404T0_03;
	}
	public String getEHF020404T0_04() {
		return EHF020404T0_04;
	}
	public void setEHF020404T0_04(String eHF020404T0_04) {
		EHF020404T0_04 = eHF020404T0_04;
	}
	public String getEHF020404T0_05() {
		return EHF020404T0_05;
	}
	public void setEHF020404T0_05(String eHF020404T0_05) {
		EHF020404T0_05 = eHF020404T0_05;
	}
	public String getEHF020404T0_06() {
		return EHF020404T0_06;
	}
	public void setEHF020404T0_06(String eHF020404T0_06) {
		EHF020404T0_06 = eHF020404T0_06;
	}
	public String getEHF020404T0_07() {
		return EHF020404T0_07;
	}
	public void setEHF020404T0_07(String eHF020404T0_07) {
		EHF020404T0_07 = eHF020404T0_07;
	}
	public String getEHF020404T0_08() {
		return EHF020404T0_08;
	}
	public void setEHF020404T0_08(String eHF020404T0_08) {
		EHF020404T0_08 = eHF020404T0_08;
	}
	public String getEHF020404T0_09() {
		return EHF020404T0_09;
	}
	public void setEHF020404T0_09(String eHF020404T0_09) {
		EHF020404T0_09 = eHF020404T0_09;
	}
	public String getEHF020404T0_10() {
		return EHF020404T0_10;
	}
	public void setEHF020404T0_10(String eHF020404T0_10) {
		EHF020404T0_10 = eHF020404T0_10;
	}
	public String getEHF020404T0_11() {
		return EHF020404T0_11;
	}
	public void setEHF020404T0_11(String eHF020404T0_11) {
		EHF020404T0_11 = eHF020404T0_11;
	}
	//public int getEHF020404T0_AFK() {
	//	return EHF020404T0_AFK;
	//}
	//public void setEHF020404T0_AFK(int eHF020404T0AFK) {
	//	EHF020404T0_AFK = eHF020404T0AFK;
	//}
	//public int getEHF020404T0_SFK() {
	//	return EHF020404T0_SFK;
	//}
	//public void setEHF020404T0_SFK(int eHF020404T0SFK) {
	//	EHF020404T0_SFK = eHF020404T0SFK;
	//}
	public String getEHF020404T0_dep() {
		return EHF020404T0_dep;
	}
	public void setEHF020404T0_dep(String eHF020404T0Dep) {
		EHF020404T0_dep = eHF020404T0Dep;
	}
	public String getEHF020404T0_dep_TXT() {
		return EHF020404T0_dep_TXT;
	}
	public void setEHF020404T0_dep_TXT(String eHF020404T0DepTXT) {
		EHF020404T0_dep_TXT = eHF020404T0DepTXT;
	}
	public String getEHF020404T0_emp() {
		return EHF020404T0_emp;
	}
	public void setEHF020404T0_emp(String eHF020404T0Emp) {
		EHF020404T0_emp = eHF020404T0Emp;
	}
	public String getEHF020404T0_emp_TXT() {
		return EHF020404T0_emp_TXT;
	}
	public void setEHF020404T0_emp_TXT(String eHF020404T0EmpTXT) {
		EHF020404T0_emp_TXT = eHF020404T0EmpTXT;
	}
	public String getEHF020404T0_05_MM() {
		return EHF020404T0_05_MM;
	}
	public void setEHF020404T0_05_MM(String eHF020404T0_05MM) {
		EHF020404T0_05_MM = eHF020404T0_05MM;
	}
	public String getEHF020404T0_05_HH() {
		return EHF020404T0_05_HH;
	}
	public void setEHF020404T0_05_HH(String eHF020404T0_05HH) {
		EHF020404T0_05_HH = eHF020404T0_05HH;
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
	public List getEHF020404T0_LIST() {
		return EHF020404T0_LIST;
	}
	public void setEHF020404T0_LIST(List eHF020404T0LIST) {
		EHF020404T0_LIST = eHF020404T0LIST;
	}
	
	public FormFile getUPLOADFILE() {
		return UPLOADFILE;
	}
	public void setUPLOADFILE(FormFile uPLOADFILE) {
		UPLOADFILE = uPLOADFILE;
	}
	//2013/10/03 賴泓錡
	public String getEHF020404T0_FLAG() {
		return EHF020404T0_FLAG;
	}
	public void setEHF020404T0_FLAG(String ehf020404T0_FLAG) {
		EHF020404T0_FLAG = ehf020404T0_FLAG;
	}
	public String getEHF020404T0_12() {
		return EHF020404T0_12;
	}
	public void setEHF020404T0_12(String eHF020404T0_12) {
		EHF020404T0_12 = eHF020404T0_12;
	}
	public String getEHF020404T0_13() {
		return EHF020404T0_13;
	}
	public void setEHF020404T0_13(String eHF020404T0_13) {
		EHF020404T0_13 = eHF020404T0_13;
	}
	public String getEHF020404T0_14() {
		return EHF020404T0_14;
	}
	public void setEHF020404T0_14(String eHF020404T0_14) {
		EHF020404T0_14 = eHF020404T0_14;
	}
	public String getEHF020404T0_15() {
		return EHF020404T0_15;
	}
	public void setEHF020404T0_15(String eHF020404T0_15) {
		EHF020404T0_15 = eHF020404T0_15;
	}
	public String getEHF020404T0_dep_UID() {
		return EHF020404T0_dep_UID;
	}
	public void setEHF020404T0_dep_UID(String eHF020404T0DepUID) {
		EHF020404T0_dep_UID = eHF020404T0DepUID;
	}
	public String getEHF020404T0_emp_UID() {
		return EHF020404T0_emp_UID;
	}
	public void setEHF020404T0_emp_UID(String eHF020404T0EmpUID) {
		EHF020404T0_emp_UID = eHF020404T0EmpUID;
	}	
	

	
}
