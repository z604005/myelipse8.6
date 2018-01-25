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
import org.apache.struts.upload.FormFile;

import com.spon.utils.util.BA_Vaildate;

public class EHF030500M0F extends ActionForm {
	
	private int EHF030500T0_01;
	private String EHF030500T0_02;		//員工
	private String EHF030500T0_02_TXT;	//員工
	private String EHF030500T0_02_UID;	//員工
	private String EHF030500T0_03;		//部門
	private String EHF030500T0_03_TXT;	//部門
	private String EHF030500T0_03_UID;	//部門
	private String EHF030500T0_04;
	private String EHF030500T0_05;
	private String EHF030500T0_05_YY;
	private String EHF030500T0_05_MM;
	private String EHF030500T0_06;
	private String EHF030500T0_07;
	private String EHF030500T0_08;
	private int EHF030500T0_09;
	private String EHF030500T0_10;
	private int EHF030500T0_11;
	private String EHF030500T0_12;
	private String EHF030500T0_13;
	private String EHF030500T0_14;
	private String EHF030500T0_15;
	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_UPDATE;
	
	private String EHF030500T0_16;
	private String EHF030500T0_17;
	private String EHF030500T0_18;
	
	private String EHF030500T0_U;
	private String EHF030500T0_SCU;
	private String EHF030500T0_SCP;
	
	private List EHF030500C = new ArrayList();
	
	private  String  EHF030500T0_19;//員工在職狀況
	private  String  EHF030500T0_20;//在職
	private  String  EHF030500T0_21;//離職
	private  String  EHF030500T0_22;//留職停薪
	
	private FormFile UPLOADFILE;
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;
	}
	
	public ActionErrors validate(ActionMapping mapping,	HttpServletRequest request, Connection conn) {
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(l_actionErrors, EHF030500T0_02, "EHF030500T0_02", "不可空白");
		ve.isEmpty(l_actionErrors, EHF030500T0_03, "EHF030500T0_03", "不可空白");
		//ve.isEmpty(l_actionErrors, EHF030500T0_04, "EHF030500T0_04", "不可空白");
		ve.isEmpty(l_actionErrors, EHF030500T0_05, "EHF030500T0_05", "不可空白");
		ve.isEmpty(l_actionErrors, EHF030500T0_06, "EHF030500T0_06", "不可空白");
		//ve.isEmpty(l_actionErrors, EHF030500T0_07, "EHF030500T0_07", "不可空白");
		
		if("01".equals(EHF030500T0_06)){  //放款
			
			ve.isEmpty(l_actionErrors, EHF030500T0_08, "EHF030500T0_06", "放款項目不可空白");
			ve.isNumEmpty(l_actionErrors, String.valueOf(EHF030500T0_09), "EHF030500T0_06", "放款金額不可空白", true);
			
		}else if("02".equals(EHF030500T0_06)){  //扣款
			
			ve.isEmpty(l_actionErrors, EHF030500T0_10, "EHF030500T0_06", "扣款項目不可空白");
			ve.isNumEmpty(l_actionErrors, String.valueOf(EHF030500T0_11), "EHF030500T0_06", "扣款金額不可空白", true);
			
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
			EHF030500C = ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new EHF030500M0F();
				            }
			 });
		} catch (Exception e) {
		}

	}
	
	public int getEHF030500T0_01(){
		return EHF030500T0_01;
	}
	public void setEHF030500T0_01(int ehf030500t0_01){
		EHF030500T0_01 = ehf030500t0_01;
	}
	
	public String getEHF030500T0_02(){
		return EHF030500T0_02;
	}
	public void setEHF030500T0_02(String ehf030500t0_02){
		EHF030500T0_02 = ehf030500t0_02;
	}
	
	public String getEHF030500T0_03(){
		return EHF030500T0_03;
	}
	public void setEHF030500T0_03(String ehf030500t0_03){
		EHF030500T0_03 = ehf030500t0_03;
	}
	
	public String getEHF030500T0_04(){
		return EHF030500T0_04;
	}
	public void setEHF030500T0_04(String ehf030500t0_04){
		EHF030500T0_04 = ehf030500t0_04;
	}
	
	public String getEHF030500T0_05(){
		return EHF030500T0_05;
	}
	public void setEHF030500T0_05(String ehf030500t0_05){
		EHF030500T0_05 = ehf030500t0_05;
	}
	
	public String getEHF030500T0_06(){
		return EHF030500T0_06;
	}
	public void setEHF030500T0_06(String ehf030500t0_06){
		EHF030500T0_06 = ehf030500t0_06;
	}
	
	public String getEHF030500T0_07(){
		return EHF030500T0_07;
	}
	public void setEHF030500T0_07(String ehf030500t0_07){
		EHF030500T0_07 = ehf030500t0_07;
	}
	
	public String getEHF030500T0_08(){
		return EHF030500T0_08;
	}
	public void setEHF030500T0_08(String ehf030500t0_08){
		EHF030500T0_08 = ehf030500t0_08;
	}

	public int getEHF030500T0_09(){
		return EHF030500T0_09;
	}
	public void setEHF030500T0_09(int ehf030500t0_09){
		EHF030500T0_09 = ehf030500t0_09;
	}
	
	public String getEHF030500T0_10(){
		return EHF030500T0_10;
	}
	public void setEHF030500T0_10(String ehf030500t0_10){
		EHF030500T0_10 = ehf030500t0_10;
	}
	
	public int getEHF030500T0_11(){
		return EHF030500T0_11;
	}
	public void setEHF030500T0_11(int ehf030500t0_11){
		EHF030500T0_11 = ehf030500t0_11;
	}
	
	public String getEHF030500T0_12(){
		return EHF030500T0_12;
	}
	public void setEHF030500T0_12(String ehf030500t0_12){
		EHF030500T0_12 = ehf030500t0_12;
	}
	
	public String getEHF030500T0_13(){
		return EHF030500T0_13;
	}
	public void setEHF030500T0_13(String ehf030500t0_13){
		EHF030500T0_13 = ehf030500t0_13;
	}
	
	public String getEHF030500T0_14(){
		return EHF030500T0_14;
	}
	public void setEHF030500T0_14(String ehf030500t0_14){
		EHF030500T0_14 = ehf030500t0_14;
	}
	
	public String getEHF030500T0_15(){
		return EHF030500T0_15;
	}
	public void setEHF030500T0_15(String ehf030500t0_15){
		EHF030500T0_15 = ehf030500t0_15;
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
	
	public String getEHF030500T0_16(){
		return EHF030500T0_16;
	}
	public void setEHF030500T0_16(String ehf030500t0_16){
		EHF030500T0_16 = ehf030500t0_16;
	}
	
	public String getEHF030500T0_17(){
		return EHF030500T0_17;
	}
	public void setEHF030500T0_17(String ehf030500t0_17){
		EHF030500T0_17 = ehf030500t0_17;
	}
	
	public String getEHF030500T0_18(){
		return EHF030500T0_18;
	}
	public void setEHF030500T0_18(String data){
		EHF030500T0_18 = data;
	}

	public String getEHF030500T0_02_TXT() {
		return EHF030500T0_02_TXT;
	}

	public void setEHF030500T0_02_TXT(String eHF030500T0_02TXT) {
		EHF030500T0_02_TXT = eHF030500T0_02TXT;
	}

	public String getEHF030500T0_03_TXT() {
		return EHF030500T0_03_TXT;
	}

	public void setEHF030500T0_03_TXT(String eHF030500T0_03TXT) {
		EHF030500T0_03_TXT = eHF030500T0_03TXT;
	}

	public String getEHF030500T0_05_YY() {
		return EHF030500T0_05_YY;
	}

	public void setEHF030500T0_05_YY(String eHF030500T0_05YY) {
		EHF030500T0_05_YY = eHF030500T0_05YY;
	}

	public String getEHF030500T0_05_MM() {
		return EHF030500T0_05_MM;
	}

	public void setEHF030500T0_05_MM(String eHF030500T0_05MM) {
		EHF030500T0_05_MM = eHF030500T0_05MM;
	}

	public String getEHF030500T0_U() {
		return EHF030500T0_U;
	}

	public void setEHF030500T0_U(String eHF030500T0U) {
		EHF030500T0_U = eHF030500T0U;
	}

	public String getEHF030500T0_SCU() {
		return EHF030500T0_SCU;
	}

	public void setEHF030500T0_SCU(String eHF030500T0SCU) {
		EHF030500T0_SCU = eHF030500T0SCU;
	}

	public String getEHF030500T0_SCP() {
		return EHF030500T0_SCP;
	}

	public void setEHF030500T0_SCP(String eHF030500T0SCP) {
		EHF030500T0_SCP = eHF030500T0SCP;
	}

	public List getEHF030500C() {
		return EHF030500C;
	}

	public void setEHF030500C(List eHF030500C) {
		EHF030500C = eHF030500C;
	}

	public String getEHF030500T0_19() {
		return EHF030500T0_19;
	}

	public void setEHF030500T0_19(String eHF030500T0_19) {
		EHF030500T0_19 = eHF030500T0_19;
	}

	public String getEHF030500T0_20() {
		return EHF030500T0_20;
	}

	public void setEHF030500T0_20(String eHF030500T0_20) {
		EHF030500T0_20 = eHF030500T0_20;
	}

	public String getEHF030500T0_21() {
		return EHF030500T0_21;
	}

	public void setEHF030500T0_21(String eHF030500T0_21) {
		EHF030500T0_21 = eHF030500T0_21;
	}

	public String getEHF030500T0_22() {
		return EHF030500T0_22;
	}

	public void setEHF030500T0_22(String eHF030500T0_22) {
		EHF030500T0_22 = eHF030500T0_22;
	}

	public String getEHF030500T0_02_UID() {
		return EHF030500T0_02_UID;
	}

	public void setEHF030500T0_02_UID(String eHF030500T0_02UID) {
		EHF030500T0_02_UID = eHF030500T0_02UID;
	}

	public String getEHF030500T0_03_UID() {
		return EHF030500T0_03_UID;
	}

	public void setEHF030500T0_03_UID(String eHF030500T0_03UID) {
		EHF030500T0_03_UID = eHF030500T0_03UID;
	}

	public void setUPLOADFILE(FormFile uPLOADFILE) {
		UPLOADFILE = uPLOADFILE;
	}

	public FormFile getUPLOADFILE() {
		return UPLOADFILE;
	}
	
	
}
