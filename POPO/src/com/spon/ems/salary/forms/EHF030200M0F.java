package com.spon.ems.salary.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;


import com.spon.utils.util.BA_Vaildate;

public class EHF030200M0F extends ActionForm {
	
	private static final long serialVersionUID = 1L;
	private boolean CHECKED;
	private boolean CHANGED;
	
	private String EHF030200T0_01;
	private String EHF030200T0_01_TXT;
	private String EHF030200T0_02;
	private String EHF030200T0_02_TXT;
	private String EHF030200T0_03;
	private int EHF030200T0_04;
	private String EHF030200T0_05;
	private String EHF030200T0_06;
	private String EHF030200T0_06_AC;
	private String EHF030200T0_07;
	private String EHF030200T0_08;
	private int EHF030200T0_09;
	private String EHF030200T0_10;
	private String EHF030200T0_10_TXT;
	private int EHF030200T0_11;
	private String EHF030200T0_12;
	private String EHF030200T0_13;
	private String EHF030200T0_14;
	private String EHF030200T0_14_TYPE;
	private float EHF030200T0_15;
	private int EHF030200T0_16;
	private String EHF030200T0_17;
	private String EHF030200T0_18;
	private int EHF030200T0_19;
	private String EHF030200T0_20;
	private String EHF030200T0_21;
	
	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_UPDATE;

	//薪資項目
	private String EHF030200T1_01;
	private String EHF030200T1_02;
	private String EHF030200T1_02_TXT;
	private String EHF030200T1_02_MONEY;
	private String EHF030200T1_03;

	//津貼項目
	private String EHF030200T1_02_01;
	private String EHF030200T1_02_TXT_01;
	private String EHF030200T1_02_MONEY_01;
	private String EHF030200T1_03_01;
	
	private String EHF030200T1_04_01;//津貼項目-可用班別代號
	private String EHF030200T1_04_01_TXT;//津貼項目-可用班別名稱
	private String EHF030200T1_04_01_NUMBER;//津貼項目-可用班別序號
	
	
	
	private String type="";
	
	private List EHF030200C = new ArrayList();
	private List EHF030200D = new ArrayList();
	
	private  String  EHF030200T0_22;//員工在職狀況
	private  String  EHF030200T0_23;//在職
	private  String  EHF030200T0_24;//離職
	private  String  EHF030200T0_25;//留職停薪
	
	
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;
	}
	
	public ActionErrors validate(ActionMapping mapping,	HttpServletRequest request, Connection conn) {
		ActionErrors l_actionErrors = new ActionErrors();
		
		//檢查不可為空白
		BA_Vaildate ve=BA_Vaildate.getInstance();
		
		ve.isEmpty(l_actionErrors, EHF030200T0_01, "EHF030200T0_01", "不可空白");
		ve.isEmpty(l_actionErrors, EHF030200T0_02, "EHF030200T0_02", "不可空白");
		
		//ve.isEmpty(l_actionErrors, EHF030200T0_03, "EHF030200T0_03", "不可空白");
		
		ve.isEmpty(l_actionErrors, EHF030200T0_05, "EHF030200T0_05", "不可空白");
		ve.isEmpty(l_actionErrors, EHF030200T0_07, "EHF030200T0_07", "不可空白");
		ve.isEmpty(l_actionErrors, EHF030200T0_08, "EHF030200T0_08", "不可空白");
		
		//ve.isEmpty(l_actionErrors, EHF030200T0_10, "EHF030200T0_10", "不可空白");

		if (l_actionErrors.isEmpty()) {
			
			//新增時判斷條件
			if (request.getAttribute("action").equals("addData")) {
				addData_validate(l_actionErrors, request, conn);
			}

			//修改時判斷條件
			if (request.getAttribute("action").equals("saveData")) {
				saveData_validate(l_actionErrors, request, conn);
			}
			
			//新增薪資明細時判斷條件
			if (request.getAttribute("action").equals("addDetailData")) {
				addDetailData_validate(l_actionErrors, request, conn);
			}

			//新增津貼明細
			if (request.getAttribute("action").equals("addDetailData_Allowance")) {
				addDetailData_validate_Allowance(l_actionErrors, request, conn);
			}
		}
		return l_actionErrors;
	}
	
	/**
	 * 新增津貼明細
	 * @param errors
	 * @param request
	 * @param conn
	 */
	private void addDetailData_validate_Allowance(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢查是否有未出帳薪資
		this.chk_validate(errors, request, conn,"新增津貼項目明細");
		
		
		if (errors.isEmpty()) {
			BA_Vaildate ve=BA_Vaildate.getInstance();
		
			ve.isEmpty(errors, EHF030200T1_02_01, "EHF030200T1_02_01", "不可空白");
			ve.isEmpty(errors, EHF030200T1_04_01, "EHF030200T1_04_01", "不可空白");
		}
		if (errors.isEmpty()) {
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = null;
				String sql = "";

				sql += " SELECT * FROM EHF030200T1 " +
			   	   " WHERE 1=1 " +
			   	   " AND EHF030200T1_01 = '"+EHF030200T0_01+"' " +
			   	   " AND EHF030200T1_02 = '"+EHF030200T1_02_01+"' " +
			   	   " AND EHF030200T1_05 = '"+EHF030200T1_04_01_NUMBER+"'"+
			   	   " AND EHF030200T1_06 = '2' " +  //發放類別-津貼
			   	   " AND EHF030200T1_04 = '"+request.getAttribute("compid")+"' " ;  //公司代碼	
				rs = stmt.executeQuery(sql);
				

				if (rs.next()) {

					// lc_errors.add("EHF030200T1_02_01",new
					// ActionMessage("津貼資料重複!!"));
					errors.add("EHF030200T1_02_01", new ActionMessage(""));
					errors.add("EHF030200T1_04_01", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG") == null ? "" : 
						request.getAttribute("ErrMSG")+ "<br>")+ "該員工津貼項目:"+ EHF030200T1_02_TXT_01+ "已有相同可使用班別,請確認後再建立資料!! ");

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}

	private void saveData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		this.chk_validate(errors, request, conn,"修改員工薪資基本資料");
	}
	private void addData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢查是否有未出帳薪資
		this.chk_validate(errors, request, conn,"新增員工薪資基本資料");
		
		if (errors.isEmpty()) {
			try {
				Statement stmt = conn.createStatement();
				String sql = "";

				sql += " SELECT * " +
					   " FROM EHF030200T0 " + 
					   " WHERE 1=1 " + 
					   " AND EHF030200T0_01 = '" + EHF030200T0_01 + "' " + 
					   " AND EHF030200T0_13 = '" + request.getAttribute("compid") + "' "; // 公司代碼

				ResultSet rs = stmt.executeQuery(sql);

				if (rs.next()) {
					errors.add("EHF030200T0_01", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG") == null ? "" : 
						request.getAttribute("ErrMSG") + "<br>")+ "該員工已有薪資基本資料設定,請確認後再建立資料!! ");
				}

				rs.close();
				stmt.close();

			} catch (SQLException e) {
				System.out.println("EHF030200M0F.addData_validate()" + e);
			}
		}
		

	}
	
	
	private void addDetailData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢查是否有未出帳薪資
		this.chk_validate(errors, request, conn,"新增薪資項目明細");
		
		
		if(errors.isEmpty()){
			BA_Vaildate ve=BA_Vaildate.getInstance();
			ve.isEmpty(errors, EHF030200T1_02, "EHF030200T1_02", "不可空白");
		}

		if(errors.isEmpty()){
			try{
				Statement stmt = conn.createStatement();
				String sql = "";
			
				sql += " SELECT * FROM EHF030200T1 " +
				   	   " WHERE 1=1 " +
				   	   " AND EHF030200T1_01 = '"+EHF030200T0_01+"' " +
				   	   " AND EHF030200T1_02 = '"+EHF030200T1_02+"' " +
				   	   " AND EHF030200T1_06 = '1' " +  //發放類別-津貼
				   	   " AND EHF030200T1_04 = '"+request.getAttribute("compid")+"' " ;  //公司代碼
			
				ResultSet rs = stmt.executeQuery(sql);
			
				if(rs.next()){
					errors.add("EHF030200T1_02",new ActionMessage(""));
					request.setAttribute("ErrMSG",
							(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
							"薪資項目重複,請確認後再建立資料!! ");
				}
			
				rs.close();
				stmt.close();
		
			}catch (SQLException e) {
				System.out.println("EHF030200M0F.addData_validate()" + e);
			}
			
			
		}

	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("utf-8");
			EHF030200C = ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new EHF030200M0F();
				            }
			 });
		} catch (Exception e) {
		}

	}
	
	public String getEHF030200T0_01(){
		return EHF030200T0_01;
	}
	public void setEHF030200T0_01(String ehf030200t0_01){
		EHF030200T0_01 = ehf030200t0_01;
	}

	public String getEHF030200T0_02(){
		return EHF030200T0_02;
	}
	public void setEHF030200T0_02(String ehf030200t0_02){
		EHF030200T0_02 = ehf030200t0_02;
	}
	
	public String getEHF030200T0_03(){
		return EHF030200T0_03;
	}
	public void setEHF030200T0_03(String ehf030200t0_03){
		EHF030200T0_03 = ehf030200t0_03;
	}
	
	public int getEHF030200T0_04(){
		return EHF030200T0_04;
	}
	public void setEHF030200T0_04(int ehf030200t0_04){
		EHF030200T0_04 = ehf030200t0_04;
	}
	
	public String getEHF030200T0_05(){
		return EHF030200T0_05;
	}
	public void setEHF030200T0_05(String ehf030200t0_05){
		EHF030200T0_05 = ehf030200t0_05;
	}
	
	public String getEHF030200T0_06(){
		return EHF030200T0_06;
	}
	public void setEHF030200T0_06(String ehf030200t0_06){
		EHF030200T0_06 = ehf030200t0_06;
	}
	
	public String getEHF030200T0_07(){
		return EHF030200T0_07;
	}
	public void setEHF030200T0_07(String ehf030200t0_07){
		EHF030200T0_07 = ehf030200t0_07;
	}
	
	public String getEHF030200T0_08(){
		return EHF030200T0_08;
	}
	public void setEHF030200T0_08(String ehf030200t0_08){
		EHF030200T0_08 = ehf030200t0_08;
	}
	
	public int getEHF030200T0_09(){
		return EHF030200T0_09;
	}
	public void setEHF030200T0_09(int ehf030200t0_09){
		EHF030200T0_09 = ehf030200t0_09;
	}
	
	public String getEHF030200T0_10(){
		return EHF030200T0_10;
	}
	public void setEHF030200T0_10(String ehf030200t0_10){
		EHF030200T0_10 = ehf030200t0_10;
	}
	
	public int getEHF030200T0_11(){
		return EHF030200T0_11;
	}
	public void setEHF030200T0_11(int ehf030200t0_11){
		EHF030200T0_11 = ehf030200t0_11;
	}
	
	public String getEHF030200T0_12(){
		return EHF030200T0_12;
	}
	public void setEHF030200T0_12(String ehf030200t0_12){
		EHF030200T0_12 = ehf030200t0_12;
	}
	
	public String getEHF030200T0_13(){
		return EHF030200T0_13;
	}
	public void setEHF030200T0_13(String ehf030200t0_13){
		EHF030200T0_13 = ehf030200t0_13;
	}
	
	public String getUSER_CREATE(){
		return USER_CREATE;
	}
	public void setUSER_CREATE(String user_create){
		USER_CREATE = user_create;
	}
	
	public String getUSER_UPDATE(){
		return USER_UPDATE;
	}
	public void setUSER_UPDATE(String user_update){
		USER_UPDATE = user_update;
	}
	
	public int getVERSION(){
		return VERSION;
	}
	public void setVERSION(int version){
		VERSION = version;
	}
	
	public String getDATE_UPDATE(){
		return DATE_UPDATE;
	}
	public void setDATE_UPDATE(String date_update){
		DATE_UPDATE = date_update;
	}
	
	public String getEHF030200T0_14(){
		return EHF030200T0_14;
	}
	public void setEHF030200T0_14(String ehf030200t0_14){
		EHF030200T0_14 = ehf030200t0_14;
	}

	public String getEHF030200T0_06_AC() {
		return EHF030200T0_06_AC;
	}

	public void setEHF030200T0_06_AC(String eHF030200T0_06AC) {
		EHF030200T0_06_AC = eHF030200T0_06AC;
	}

	public String getEHF030200T1_01() {
		return EHF030200T1_01;
	}

	public void setEHF030200T1_01(String eHF030200T1_01) {
		EHF030200T1_01 = eHF030200T1_01;
	}

	public String getEHF030200T1_02() {
		return EHF030200T1_02;
	}

	public void setEHF030200T1_02(String eHF030200T1_02) {
		EHF030200T1_02 = eHF030200T1_02;
	}

	public String getEHF030200T1_03() {
		return EHF030200T1_03;
	}

	public void setEHF030200T1_03(String eHF030200T1_03) {
		EHF030200T1_03 = eHF030200T1_03;
	}

	public String getEHF030200T0_01_TXT() {
		return EHF030200T0_01_TXT;
	}

	public void setEHF030200T0_01_TXT(String eHF030200T0_01TXT) {
		EHF030200T0_01_TXT = eHF030200T0_01TXT;
	}

	public String getEHF030200T0_02_TXT() {
		return EHF030200T0_02_TXT;
	}

	public void setEHF030200T0_02_TXT(String eHF030200T0_02TXT) {
		EHF030200T0_02_TXT = eHF030200T0_02TXT;
	}

	public String getEHF030200T1_02_TXT() {
		return EHF030200T1_02_TXT;
	}

	public void setEHF030200T1_02_TXT(String eHF030200T1_02TXT) {
		EHF030200T1_02_TXT = eHF030200T1_02TXT;
	}

	public String getEHF030200T1_02_MONEY() {
		return EHF030200T1_02_MONEY;
	}

	public void setEHF030200T1_02_MONEY(String eHF030200T1_02MONEY) {
		EHF030200T1_02_MONEY = eHF030200T1_02MONEY;
	}

	public List getEHF030200C() {
		return EHF030200C;
	}
	
	public List getEHF030200D() {
		return EHF030200D;
	}

	public void setEHF030200D(List eHF030200D) {
		EHF030200D = eHF030200D;
	}

	public void setEHF030200C(List eHF030200C) {
		EHF030200C = eHF030200C;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getEHF030200T0_10_TXT() {
		return EHF030200T0_10_TXT;
	}

	public void setEHF030200T0_10_TXT(String eHF030200T0_10TXT) {
		EHF030200T0_10_TXT = eHF030200T0_10TXT;
	}

	public String getEHF030200T0_14_TYPE() {
		return EHF030200T0_14_TYPE;
	}

	public void setEHF030200T0_14_TYPE(String eHF030200T0_14TYPE) {
		EHF030200T0_14_TYPE = eHF030200T0_14TYPE;
	}

	public float getEHF030200T0_15() {
		return EHF030200T0_15;
	}

	public void setEHF030200T0_15(float eHF030200T0_15) {
		EHF030200T0_15 = eHF030200T0_15;
	}

	public int getEHF030200T0_16() {
		return EHF030200T0_16;
	}

	public void setEHF030200T0_16(int eHF030200T0_16) {
		EHF030200T0_16 = eHF030200T0_16;
	}

	public String getEHF030200T0_17() {
		return EHF030200T0_17;
	}

	public void setEHF030200T0_17(String eHF030200T0_17) {
		EHF030200T0_17 = eHF030200T0_17;
	}

	public String getEHF030200T0_18() {
		return EHF030200T0_18;
	}

	public void setEHF030200T0_18(String eHF030200T0_18) {
		EHF030200T0_18 = eHF030200T0_18;
	}

	public int getEHF030200T0_19() {
		return EHF030200T0_19;
	}

	public void setEHF030200T0_19(int eHF030200T0_19) {
		EHF030200T0_19 = eHF030200T0_19;
	}

	public String getEHF030200T0_20() {
		return EHF030200T0_20;
	}

	public void setEHF030200T0_20(String eHF030200T0_20) {
		EHF030200T0_20 = eHF030200T0_20;
	}

	public String getEHF030200T0_21() {
		return EHF030200T0_21;
	}

	public void setEHF030200T0_21(String eHF030200T0_21) {
		EHF030200T0_21 = eHF030200T0_21;
	}

	public String getEHF030200T1_02_TXT_01() {
		return EHF030200T1_02_TXT_01;
	}

	public void setEHF030200T1_02_TXT_01(String eHF030200T1_02TXT_01) {
		EHF030200T1_02_TXT_01 = eHF030200T1_02TXT_01;
	}

	public String getEHF030200T1_02_MONEY_01() {
		return EHF030200T1_02_MONEY_01;
	}

	public void setEHF030200T1_02_MONEY_01(String eHF030200T1_02MONEY_01) {
		EHF030200T1_02_MONEY_01 = eHF030200T1_02MONEY_01;
	}

	public String getEHF030200T1_03_01() {
		return EHF030200T1_03_01;
	}

	public void setEHF030200T1_03_01(String eHF030200T1_03_01) {
		EHF030200T1_03_01 = eHF030200T1_03_01;
	}

	public String getEHF030200T1_02_01() {
		return EHF030200T1_02_01;
	}

	public void setEHF030200T1_02_01(String eHF030200T1_02_01) {
		EHF030200T1_02_01 = eHF030200T1_02_01;
	}
	
	public String getEHF030200T1_04_01() {
		return EHF030200T1_04_01;
	}

	public void setEHF030200T1_04_01(String eHF030200T1_04_01) {
		EHF030200T1_04_01 = eHF030200T1_04_01;
	}
	
	public String getEHF030200T1_04_01_TXT() {
		return EHF030200T1_04_01_TXT;
	}

	public void setEHF030200T1_04_01_TXT(String eHF030200T1_04_01TXT) {
		EHF030200T1_04_01_TXT = eHF030200T1_04_01TXT;
	}

	public String getEHF030200T1_04_01_NUMBER() {
		return EHF030200T1_04_01_NUMBER;
	}

	public void setEHF030200T1_04_01_NUMBER(String eHF030200T1_04_01NUMBER) {
		EHF030200T1_04_01_NUMBER = eHF030200T1_04_01NUMBER;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getEHF030200T0_22() {
		return EHF030200T0_22;
	}

	public void setEHF030200T0_22(String eHF030200T0_22) {
		EHF030200T0_22 = eHF030200T0_22;
	}

	public String getEHF030200T0_23() {
		return EHF030200T0_23;
	}

	public void setEHF030200T0_23(String eHF030200T0_23) {
		EHF030200T0_23 = eHF030200T0_23;
	}

	public String getEHF030200T0_24() {
		return EHF030200T0_24;
	}

	public void setEHF030200T0_24(String eHF030200T0_24) {
		EHF030200T0_24 = eHF030200T0_24;
	}

	public String getEHF030200T0_25() {
		return EHF030200T0_25;
	}

	public void setEHF030200T0_25(String eHF030200T0_25) {
		EHF030200T0_25 = eHF030200T0_25;
	}

	/**
	 * 檢查是否有未出帳的薪資
	 * @param errors
	 * @param request
	 * @param conn
	 */
	private void chk_validate(ActionErrors errors, HttpServletRequest request, Connection conn,String type) {
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "";
		
			sql = "" +
				" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_03" +
				" FROM EHF020900T0 " +
				" WHERE 1=1 " +
				" AND EHF020900T0_04 = '"+request.getAttribute("compid")+"'" +//公司代碼
				" AND (EHF020900T0_03 = '02' OR EHF020900T0_03 = '03')";

    	
			ResultSet rs = stmt.executeQuery(sql);
		
			if(rs.next()){
				errors.add("",new ActionMessage(""));
				request.setAttribute("ERR_MSG",(request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"修改失敗，尚有薪資未出帳，請勿"+type+"。 ");
				//request.setAttribute("ErrMSG","尚有薪資未出帳，請勿刪除員工薪資基本資料。");
			}
			rs.close();
			stmt.close();
		}catch (SQLException e) {
			System.out.println("EHF030102M0F.saveData_validate()" + e);
		}
	}
}
