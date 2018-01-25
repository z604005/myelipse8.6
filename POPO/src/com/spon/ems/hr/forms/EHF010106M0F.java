package com.spon.ems.hr.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.utils.util.BA_Vaildate;

public class EHF010106M0F extends ActionForm implements ValidateForm {
	
	//員工人事基本資料
	private String HR_EmployeeSysNo;
	private String HR_EmployeeNo;
	private String EHF010106T0_01;
	private String EHF010106T0_02;
	private String EHF010106T0_03;
	private String EHF010106T0_04;	//姓名(中)
	private String EHF010106T0_05;
	private String EHF010106T0_06;
	private String EHF010106T0_07;
	private String EHF010106T0_08;
	private String EHF010106T0_09;
	private String EHF010106T0_10;
	private String EHF010106T0_11;
	private String EHF010106T0_1101;
	private String EHF010106T0_1102;
	private String EHF010106T0_12;
	private String EHF010106T0_13;
	private String EHF010106T0_1301;
	private String EHF010106T0_1302;
	private String EHF010106T0_14;
	private String EHF010106T0_15;
	private String EHF010106T0_16;
	private String EHF010106T0_17;
	private String EHF010106T0_18;
	private String EHF010106T0_1801;
	private String EHF010106T0_1802;
	private String EHF010106T0_19;
	private boolean EHF010106T0_20;
	private boolean EHF010106T0_21;
	private String EHF010106T0_22;
	private String EHF010106T0_23;
	private String EHF010106T0_24;
	private String EHF010106T0_25;
	private String HR_JobDate;
	private String HR_OffJobDate;
	private String HR_Seniority;
	private String HR_SeniorityAdjust;	
	private String HR_CompanySysNo;
	
	private String Login_No;	//登入帳號
	private String EHF010106T0_TYPE;
	private List EHF010106T0_LIST = new ArrayList();
	
	private FormFile UPLOADFILE;

	
	//員工狀況明細
	private String EHF010106T1_01;
	private String EHF010106T1_02;
	private String EHF010106T1_0201;
	private String EHF010106T1_0202;
	private String EHF010106T1_0203;
	private String EHF010106T1_0204;
	
	//員工履經歷明細
	private int EHF010106T2_01;
	private String EHF010106T2_02;
	private String EHF010106T2_03;
	private String EHF010106T2_04;
	private String EHF010106T2_05;
	private String EHF010106T2_06;
	private String EHF010106T2_07;
	private String EHF010106T2_08;
	private String EHF010106T2_09;
	private List EHF010106T2_LIST = new ArrayList();
	
	//員工學歷明細
	private int EHF010106T3_01;
	private String EHF010106T3_02;
	private String EHF010106T3_03;
	private String EHF010106T3_04;
	private String EHF010106T3_05;
	private String EHF010106T3_06;
	private String EHF010106T3_07;
	private List EHF010106T3_LIST = new ArrayList();
	
	//證照資料明細
	private int EHF010106T4_01;
	private String EHF010106T4_02;
	private String EHF010106T4_03;
	private String EHF010106T4_04;
	private String EHF010106T4_05;
	private String EHF010106T4_06;
	private String EHF010106T4_07;
	private List EHF010106T4_LIST = new ArrayList();
	
	//家庭關係明細
	private int EHF010106T5_01;
	private String EHF010106T5_02;
	private int EHF010106T5_03;
	private String EHF010106T5_04;
	private String EHF010106T5_05;
	private List EHF010106T5_LIST = new ArrayList();
	
	//職務現況明細	
	private String HR_DepartmentSysNo;
	private String EHF010106T6_01;//部門歸屬區間(起)
	private String EHF010106T6_02;//部門歸屬區間(迄)
	private boolean EHF010106T6_03;//主要歸屬部門
	private String HR_IsManager;//是否為主管
	private String HR_ManagerLevel;//級等
	private String HR_DesignateManager;//指定直屬主管
	private String HR_JobAgent1;//第一順位職務代理人
	private String HR_JobAgent2;//第二順位職務代理人
	private String HR_JobAgent3;//第三順位職務代理人
	private String HR_CheckLeave;//是否請假
	private String HR_LeaveStartTime;//請假開始時間
	private String HR_LeaveEndTime;//請假結束時間
	private String HR_JobName;//職務名稱(使用者自訂)
	private String HR_JobNameNo;//職務編號(使用者自訂)
	private String HR_JobNameSysNo;//職務名稱基本資料_系統編碼(系統給定)
	
	private String HR_DepartmentNo;
	private String HR_DepartmentName;
	
	//員工考核獎懲資料
	
	//員工班別資料
	private int EHF010100T1_01;	//員工班別序號
	private String EHF010100T1_02;	//部門組織(代號)
	private String EHF010100T1_03;	//員工工號
	private String EHF010100T1_04;	//班別序號
	private String EHF010100T1_04_TXT;
	private String EHF010100T1_04_KEY;
	private String EHF010100T1_04_HIDE;
	private String EHF010100T1_05;	//備註
	private String EHF010100T1_06;	//公司代碼
	
	//感應卡設定資料
	private String EHF020403T0_01;	//員工工號
	private String EHF020403T0_02;	//部門代號
	private String EHF020403T0_03;	//備註
	private String EHF020403T0_04;	//公司代碼
	private String EHF020403T0_05;	//是否記錄考勤
	
	private boolean CHECKED;
	
	//感應卡明細
	private String EHF020403T1_01;	//員工工號
	private String EHF020403T1_02;	//感應卡號
	private String EHF020403T1_03;	//班別代號
	private String EHF020403T1_04;	//啟用
	private String EHF020403T1_04_START;	//使用期間(起)
	private String EHF020403T1_04_START_HH;
	private String EHF020403T1_04_START_MM;
	private String EHF020403T1_04_END;	//使用期間(迄)
	private String EHF020403T1_04_END_HH;
	private String EHF020403T1_04_END_MM;
	private String EHF020403T1_05;	//備註
	private String EHF020403T1_06;	//公司代碼
	
	private List EHF020403C = new ArrayList();	//感應卡明細LIST
	
	private String EHF030200T0_01;
	private String EHF030200T0_02;
	private String EHF030200T0_03;
	private int EHF030200T0_04;
	private String EHF030200T0_05;
	private String EHF030200T0_06;
	private String EHF030200T0_06_AC;
	private String EHF030200T0_07;
	private String EHF030200T0_08;
	private int EHF030200T0_09;
	private String EHF030200T0_10;
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
	private String EHF030200T1_04_01;
	private String EHF030200T1_04_01_TXT;
	
	private List EHF030200C = new ArrayList();	//薪資基本資料 - 薪資津貼項目明細LIST
	private String type = "";
	
	private  String EHF030300T0_01;
	private  String EHF030300T0_01_TXT;
	private  String EHF030300T0_02;
	private  String EHF030300T0_02_TXT;
	private  String EHF030300T0_03;
	private  int  	EHF030300T0_04;//基本薪資(本俸)
	private  String EHF030300T0_05;
	private  int  	EHF030300T0_05_HIDE;
	private  String EHF030300T0_05_NUMBER;//勞保規則序號 		新增於20140120 BY 賴泓錡 
	private  String EHF030300T0_05_VERSION;//勞保規則    			新增於20140120 BY 賴泓錡 
	private  String EHF030300T0_05_VERSION_NAME;//勞保規則名稱  	新增於20140120 BY 賴泓錡  
	private  int  	EHF030300T0_06;
	private  String EHF030300T0_07;
	private  int  	EHF030300T0_07_HIDE;
	private  String EHF030300T0_07_NUMBER;//健保規則序號		新增於20140120 BY 賴泓錡 
	private  String EHF030300T0_07_VERSION;//健保規則			新增於20140120 BY 賴泓錡 
	private  String EHF030300T0_07_VERSION_NAME;//健保規則名稱	新增於20140120 BY 賴泓錡 
	private  int  	EHF030300T0_08;
	private  int  	EHF030300T0_09;
	private  int  	EHF030300T0_10;	
	private  int  	EHF030300T0_11;
	private  String EHF030300T0_12;
	private  String EHF030300T0_13;
	private  String EHF030300T0_14;
	private  int  	EHF030300T0_15;
	private  int  	EHF030300T0_16;
	private  String EHF030300T0_05_START;
	private  String EHF030300T0_05_END;
	private  String EHF030300T0_07_START;
	private  String EHF030300T0_07_END;
	
	private  String EHF030300T1_01;
	private  String EHF030300T1_02;
	private  String EHF030300T1_03;
	private  String EHF030300T1_04;
	private  String EHF030300T1_05;
	private  String EHF030300T1_06;
	
	private List EHF030300C = new ArrayList();
	
	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_CREATE;
	private String HR_LastUpdateDate;
		
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
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
			
			//刪除時判斷條件
			if (request.getAttribute("action").equals("delData")) {
				
				delData_validate(l_actionErrors, request, conn);
			}
			
			if (request.getAttribute("action").equals("saveDataATT")) {
				
				saveDataATT_validate(l_actionErrors, request, conn);
			}
			
			if (request.getAttribute("action").equals("saveDataSAL")) {
				
				saveDataSAL_validate(l_actionErrors, request, conn);
			}

			if (request.getAttribute("action").equals("saveDataINS")) {
				
				saveDataINS_validate(l_actionErrors, request, conn);
			}
			
			if (request.getAttribute("action").equals("saveDataEMP")) {
				
				saveDataEMP_validate(l_actionErrors, request, conn);
			}
			
			if (request.getAttribute("action").equals("saveDataEXP")) {
				
				saveDataEXP_validate(l_actionErrors, request, conn);
			}
			
		}
		
		return l_actionErrors;
	}

	private void saveDataEXP_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
	}

	private void saveDataEMP_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(errors, EHF010106T0_04, "EHF010106T0_04", "不可空白");	//姓名(中)
		ve.isEmpty(errors, EHF010106T0_06, "EHF010106T0_06", "不可空白");	//員工類別
		ve.isTWID(errors, EHF010106T0_01, "EHF010106T0_01", "請輸入正確的身分證字號");
		ve.isEmpty(errors, EHF010106T0_12, "EHF010106T0_12", "不可空白");	//
		
		if( ("".equals(getEHF010106T0_01())||getEHF010106T0_01()==null) && 
			("".equals(getEHF010106T0_02())||getEHF010106T0_02()==null) && 
			("".equals(getEHF010106T0_03())||getEHF010106T0_03()==null) ){
			errors.add("EHF010106T0_01", new ActionMessage(""));
			errors.add("EHF010106T0_02", new ActionMessage(""));
			errors.add("EHF010106T0_03", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"身分證字號、護照號碼、居留證號碼請擇一填寫!!");
		}
		
		if( ("".equals(getEHF010106T0_15()) || getEHF010106T0_15()==null) && ("".equals(getEHF010106T0_13()) || getEHF010106T0_13()==null)){
			errors.add("EHF010106T0_13", new ActionMessage(""));
			errors.add("EHF010106T0_15", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"聯絡電話或手機號碼請擇一填寫!!");			
		}
		
	}

	private void saveDataINS_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(errors, EHF030300T0_05_VERSION, "EHF030300T0_05_VERSION", "不可空白");
		ve.isEmpty(errors, EHF030300T0_05, "EHF030300T0_05_HIDE", "不可空白");
		ve.isEmpty(errors, EHF030300T0_05_START, "EHF030300T0_05_START", "不可空白");
		ve.isEmpty(errors, EHF030300T0_07_VERSION, "EHF030300T0_07_VERSION", "不可空白");
		ve.isEmpty(errors, EHF030300T0_07, "EHF030300T0_07_HIDE", "不可空白");
		ve.isEmpty(errors, EHF030300T0_07_START, "EHF030300T0_07_START", "不可空白");
		
		if(!"".equals(String.valueOf(EHF030300T0_11)) && (EHF030300T0_11 > 4 || EHF030300T0_11 < 1 )){
			errors.add("EHF030300T0_11",new ActionMessage(""));
			request.setAttribute("ErrMSG",
					(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
					"口數欄位請輸入 1~4 (至多到 4 口)!! ");
		}
		
	}

	private void saveDataSAL_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(errors, EHF030200T0_05, "EHF030200T0_05", "不可空白");
		ve.isEmpty(errors, EHF030200T0_07, "EHF030200T0_07", "不可空白");
		ve.isNumEmpty(errors, EHF030200T0_04+"", "EHF030200T0_04", "不可空白", true);
		ve.isEmpty(errors, EHF030200T0_21, "EHF030200T0_21", "不可空白");
		ve.isEmpty(errors, EHF030200T0_08, "EHF030200T0_08", "不可空白");
		
		if((!"".equals(getEHF030200T0_12())|| getEHF030200T0_12()==null) && !GenericValidator.maxLength(getEHF030200T0_12(), 50)){//
			errors.add("EHF030200T0_12", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"備註字數不得超過50個字!!");
		}
		
	}

	private void saveDataATT_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(errors, EHF010100T1_04, "EHF010100T1_04", "不可空白");	//
		ve.isEmpty(errors, EHF020403T0_05, "EHF020403T0_05", "不可空白");
		
		if((!"".equals(getEHF020403T1_05())|| getEHF020403T1_05()==null) && !GenericValidator.maxLength(getEHF020403T1_05(), 25)){//
			errors.add("EHF020403T1_05", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"感應卡備註字數不得超過25個字!!");
		}
		
		try{						
			for (int i = 0; i < EHF020403C.size(); i++) {
				EHF010106M0F Form = (EHF010106M0F) EHF020403C.get(i);
				
				
				Statement stmt = conn.createStatement();
				String sql = "";
			
				sql += " SELECT * FROM EHF020403T1 " +
				   	   " WHERE 1=1 " +
				   	   " AND '"+Form.EHF020403T1_04_END+" "+Form.EHF020403T1_04_END_HH+":"+Form.EHF020403T1_04_END_MM+":00"+"' BETWEEN DATE_FORMAT(EHF020403T1_04_START, '%Y/%m/%d %H%i%s') AND DATE_FORMAT(EHF020403T1_04_END, '%Y/%m/%d %H%i%s')"+
				   	   " AND EHF020403T1_01 <> '"+HR_EmployeeSysNo+"' " +  //員工工號
				   	   " AND EHF020403T1_02 = '"+Form.getEHF020403T1_02()+"' " +  //感應卡號
				   	   " AND EHF020403T1_06 = '"+request.getAttribute("compid")+"' " ;
			
				ResultSet rs = stmt.executeQuery(sql);
			
				if(rs.next()){
					errors.add("",new ActionMessage(""));
					request.setAttribute("ErrMSG",
							(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
							Form.getEHF020403T1_02()+"感應卡時間錯誤，已有他人使用，請確認時間後再建立資料!! ");
				}
			
				rs.close();
				stmt.close();
				
			}
			
		}catch (SQLException e) {
			System.out.println("EHF010106M0F.saveDataATT_validate()" + e);
		}
		
	}

	private void delData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
	}

	private void saveData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
	}

	private void addDetailData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		/*BA_EMS_TOOLS ems_tools = BA_EMS_TOOLS.getInstance();*/
		
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(errors, EHF010106T5_02, "EHF010106T5_02", "不可空白");
		ve.isNumEmpty(errors, EHF010106T5_03+"", "EHF010106T5_03", "不可空白", false);
		ve.isEmpty(errors, EHF010106T5_04, "EHF010106T5_04", "不可空白");
		ve.isEmpty(errors, EHF010106T5_05, "EHF010106T5_05", "不可空白");
		
		ve.isEmpty(errors, EHF010106T3_02, "EHF010106T3_02", "不可空白");//
		ve.isEmpty(errors, EHF010106T3_03, "EHF010106T3_03", "不可空白");//
		ve.isEmpty(errors, EHF010106T3_05, "EHF010106T3_05", "不可空白");//
		ve.isEmpty(errors, EHF010106T3_06, "EHF010106T3_06", "不可空白");//
		ve.isEmpty(errors, EHF010106T3_07, "EHF010106T3_07", "不可空白");//
		
		ve.isEmpty(errors, EHF010106T2_02, "EHF010106T2_02", "不可空白");//
		ve.isEmpty(errors, EHF010106T2_03, "EHF010106T2_03", "不可空白");//
		ve.isEmpty(errors, EHF010106T2_04, "EHF010106T2_04", "不可空白");//
		ve.isEmpty(errors, EHF010106T2_05, "EHF010106T2_05", "不可空白");//
		ve.isEmpty(errors, EHF010106T2_07, "EHF010106T2_07", "不可空白");//
		ve.isEmpty(errors, EHF010106T2_08, "EHF010106T2_08", "不可空白");//
		
		ve.isEmpty(errors, EHF010106T4_02, "EHF010106T4_02", "不可空白");//
		ve.isEmpty(errors, EHF010106T4_04, "EHF010106T4_04", "不可空白");//
		ve.isEmpty(errors, EHF010106T4_05, "EHF010106T4_05", "不可空白");//
		
		ve.isNumEmpty(errors, EHF020403T1_02, "EHF020403T1_02", "不可空白", false);
		ve.isEmpty(errors, EHF020403T1_04_START, "EHF020403T1_04_START", "不可空白");
		ve.isEmpty(errors, EHF020403T1_04_END, "EHF020403T1_04_END", "不可空白");
		
		/*if(!"".equals(EHF020403T1_04_END)){
			Calendar cal_START 	= ems_tools.covertStringToCalendar(EHF020403T1_04_START+" "+EHF020403T1_04_START_HH+":"+EHF020403T1_04_START_MM+":00", "yyyy/MM/dd HH:mm:ss");
			Calendar cal_END 	= ems_tools.covertStringToCalendar(EHF020403T1_04_END  +" "+EHF020403T1_04_END_HH+":"+EHF020403T1_04_END_MM+":00"    , "yyyy/MM/dd HH:mm:ss");
			if(cal_START.compareTo(cal_END)>0){
				errors.add("EHF020403T1_04_START",new ActionMessage("時間錯誤，請確認!!"));
				errors.add("EHF020403T1_04_END",new ActionMessage("時間錯誤，請確認!!"));
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						"使用期間錯誤,請確認後再建立資料!! ");
			}
		}*/
		
		ve.isEmpty(errors, EHF030200T1_02, "EHF030200T1_02", "不可空白");
		ve.isNumEmpty(errors, EHF030200T1_02_MONEY, "EHF030200T1_02_MONEY", "不可空白", true);
		ve.isEmpty(errors, EHF030200T1_02_01, "EHF030200T1_02_01", "不可空白");
		ve.isEmpty(errors, EHF030200T1_04_01, "EHF030200T1_04_01", "不可空白");
		ve.isNumEmpty(errors, EHF030200T1_02_MONEY_01, "EHF030200T1_02_MONEY_01", "不可空白", true);
		
		if((!"".equals(getEHF030200T1_03())|| getEHF030200T1_03()==null) && !GenericValidator.maxLength(getEHF030200T1_03(), 25)){//
			errors.add("EHF030200T1_03", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"備註字數不得超過25個字!!");
		}
		
		if((!"".equals(getEHF030200T1_03_01())|| getEHF030200T1_03_01()==null) && !GenericValidator.maxLength(getEHF030200T1_03_01(), 25)){//
			errors.add("EHF030200T1_03_01", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"備註字數不得超過25個字!!");
		}
		
		ve.isEmpty(errors, EHF030300T1_02, "EHF030300T1_02", "不可空白");
		ve.isEmpty(errors, EHF030300T1_03, "EHF030300T1_03", "不可空白");
		ve.isTWID(errors, EHF030300T1_04, "EHF030300T1_04", "請輸入正確的身分證字號");
		ve.isEmpty(errors, EHF030300T1_05, "EHF030300T1_05", "不可空白");
		ve.isEmpty(errors, EHF030300T1_06, "EHF030300T1_06", "不可空白");
		
		if(errors.isEmpty()){
			try{
				Statement stmt = conn.createStatement();
				String sql = "";
			
				sql += " SELECT * FROM EHF030200T1 " +
				   	   " WHERE 1=1 " +
				   	   " AND EHF030200T1_01 = '"+HR_EmployeeSysNo+"' " +
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
				System.out.println("EHF010106M0F.addData_validate()" + e);
			}
		}
		
		try{
			Statement stmt = conn.createStatement();
			String sql = "";
		
			sql += 	" SELECT EHF020403T1_01, EHF020403T1_02, EHF020403T1_03, EHF020403T1_04," +
					" DATE_FORMAT(EHF020403T1_04_START,	'%Y/%m/%d %H:%i:%s') AS EHF020403T1_04_START," +
					" DATE_FORMAT(EHF020403T1_04_END, 	'%Y/%m/%d %H:%i:%s') AS EHF020403T1_04_END," +
					" EHF020403T1_05," +
					" EHF020403T1_06" +
					" FROM" +
					" EHF020403T1 WHERE 1 = 1" +
					" AND EHF020403T1_02 = '"+EHF020403T1_02+"' " +  //感應卡號
					" AND EHF020403T1_06 = '"+request.getAttribute("compid")+"' " +
					" ORDER BY EHF020403T1_04_END" +
					" LIMIT 1";
		
			ResultSet rs = stmt.executeQuery(sql);
		
			if(rs.next()){
				//曾經有人使用過，要判斷是否為同一人
				if(HR_EmployeeSysNo.equals(rs.getString("EHF020403T1_01"))){
					//上一位使用者與目前要新增的人為同一個人
					//l_actionErrors.add("EHF020403T1_02",new ActionMessage("感應卡已存在"));
					request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":
						request.getAttribute("ErrMSG")+"<br>")+EHF020403T1_02+"感應卡資料已存在,請修改時間(迄)即可");
				}else{
					//判斷新感應卡的起與上一張感應卡的迄  時間是否有交錯
					
					//新感應卡的時間(起)
					/*Calendar NEW_cal_START 	= ems_tools.covertStringToCalendar(EHF020403T1_04_START+" "+EHF020403T1_04_START_HH+":"+EHF020403T1_04_START_MM+":00", "yyyy/MM/dd HH:mm:ss");
					//上一張感應卡的(迄)
					Calendar Old_cal_END 	= ems_tools.covertStringToCalendar(rs.getString("EHF020403T1_04_END"), "yyyy/MM/dd HH:mm:ss");
						
					if(NEW_cal_START.compareTo(Old_cal_END)<0){
						errors.add("EHF020403T1_02",new ActionMessage("感應卡使用中"));
						request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":
						request.getAttribute("ErrMSG")+"<br>")+EHF020403T1_02+"感應卡使用中,請確認!!");
						
					}*/
				}
			}
		
			rs.close();
			stmt.close();
		}catch (SQLException e) {
			System.out.println("EHF010106M0F.addData_validate()" + e);
		}
		
	}

	private void addData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(errors, HR_EmployeeNo, "HR_EmployeeNo", "不可空白");
		ve.isEmpty(errors, EHF010106T0_04, "EHF010106T0_04", "不可空白");	//姓名(中)
		ve.isEmpty(errors, EHF010106T0_06, "EHF010106T0_06", "不可空白");	//員工類別
		ve.isTWID(errors, EHF010106T0_01, "EHF010106T0_01", "請輸入正確的身分證字號");
		ve.isEmpty(errors, EHF010106T0_12, "EHF010106T0_12", "不可空白");	//
		
		if( ("".equals(getEHF010106T0_01())||getEHF010106T0_01()==null) && 
			("".equals(getEHF010106T0_02())||getEHF010106T0_02()==null) && 
			("".equals(getEHF010106T0_03())||getEHF010106T0_03()==null) ){
			errors.add("EHF010106T0_01", new ActionMessage(""));
			errors.add("EHF010106T0_02", new ActionMessage(""));
			errors.add("EHF010106T0_03", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"身分證字號、護照號碼、居留證號碼請擇一填寫!!");
		}
		
		if( ("".equals(getEHF010106T0_15()) || getEHF010106T0_15()==null) && ("".equals(getEHF010106T0_13()) || getEHF010106T0_13()==null)){
			errors.add("EHF010106T0_13", new ActionMessage(""));
			errors.add("EHF010106T0_15", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"聯絡電話或手機號碼請擇一填寫!!");			
		}	
		
		if(request.getAttribute("ERR_MSG")!=null){
			request.setAttribute("button_init", "init");
		}
		
				
	}

	public String getHR_EmployeeSysNo() {
		return HR_EmployeeSysNo;
	}

	public void setHR_EmployeeSysNo(String hREmployeeSysNo) {
		HR_EmployeeSysNo = hREmployeeSysNo;
	}

	public String getHR_EmployeeNo() {
		return HR_EmployeeNo;
	}

	public void setHR_EmployeeNo(String hREmployeeNo) {
		HR_EmployeeNo = hREmployeeNo;
	}

	public String getEHF010106T0_01() {
		return EHF010106T0_01;
	}

	public void setEHF010106T0_01(String eHF010106T0_01) {
		EHF010106T0_01 = eHF010106T0_01;
	}

	public String getEHF010106T0_02() {
		return EHF010106T0_02;
	}

	public void setEHF010106T0_02(String eHF010106T0_02) {
		EHF010106T0_02 = eHF010106T0_02;
	}

	public String getEHF010106T0_03() {
		return EHF010106T0_03;
	}

	public void setEHF010106T0_03(String eHF010106T0_03) {
		EHF010106T0_03 = eHF010106T0_03;
	}

	public String getEHF010106T0_04() {
		return EHF010106T0_04;
	}

	public void setEHF010106T0_04(String eHF010106T0_04) {
		EHF010106T0_04 = eHF010106T0_04;
	}

	public String getEHF010106T0_05() {
		return EHF010106T0_05;
	}

	public void setEHF010106T0_05(String eHF010106T0_05) {
		EHF010106T0_05 = eHF010106T0_05;
	}

	public String getEHF010106T0_06() {
		return EHF010106T0_06;
	}

	public void setEHF010106T0_06(String eHF010106T0_06) {
		EHF010106T0_06 = eHF010106T0_06;
	}

	public String getEHF010106T0_07() {
		return EHF010106T0_07;
	}

	public void setEHF010106T0_07(String eHF010106T0_07) {
		EHF010106T0_07 = eHF010106T0_07;
	}

	public String getEHF010106T0_08() {
		return EHF010106T0_08;
	}

	public void setEHF010106T0_08(String eHF010106T0_08) {
		EHF010106T0_08 = eHF010106T0_08;
	}

	public String getEHF010106T0_09() {
		return EHF010106T0_09;
	}

	public void setEHF010106T0_09(String eHF010106T0_09) {
		EHF010106T0_09 = eHF010106T0_09;
	}

	public String getEHF010106T0_10() {
		return EHF010106T0_10;
	}

	public void setEHF010106T0_10(String eHF010106T0_10) {
		EHF010106T0_10 = eHF010106T0_10;
	}

	public String getEHF010106T0_11() {
		return EHF010106T0_11;
	}

	public void setEHF010106T0_11(String eHF010106T0_11) {
		EHF010106T0_11 = eHF010106T0_11;
	}

	public String getEHF010106T0_1101() {
		return EHF010106T0_1101;
	}

	public void setEHF010106T0_1101(String eHF010106T0_1101) {
		EHF010106T0_1101 = eHF010106T0_1101;
	}

	public String getEHF010106T0_1102() {
		return EHF010106T0_1102;
	}

	public void setEHF010106T0_1102(String eHF010106T0_1102) {
		EHF010106T0_1102 = eHF010106T0_1102;
	}

	public String getEHF010106T0_12() {
		return EHF010106T0_12;
	}

	public void setEHF010106T0_12(String eHF010106T0_12) {
		EHF010106T0_12 = eHF010106T0_12;
	}

	public String getEHF010106T0_13() {
		return EHF010106T0_13;
	}

	public void setEHF010106T0_13(String eHF010106T0_13) {
		EHF010106T0_13 = eHF010106T0_13;
	}

	public String getEHF010106T0_1301() {
		return EHF010106T0_1301;
	}

	public void setEHF010106T0_1301(String eHF010106T0_1301) {
		EHF010106T0_1301 = eHF010106T0_1301;
	}

	public String getEHF010106T0_1302() {
		return EHF010106T0_1302;
	}

	public void setEHF010106T0_1302(String eHF010106T0_1302) {
		EHF010106T0_1302 = eHF010106T0_1302;
	}

	public String getEHF010106T0_14() {
		return EHF010106T0_14;
	}

	public void setEHF010106T0_14(String eHF010106T0_14) {
		EHF010106T0_14 = eHF010106T0_14;
	}

	public String getEHF010106T0_15() {
		return EHF010106T0_15;
	}

	public void setEHF010106T0_15(String eHF010106T0_15) {
		EHF010106T0_15 = eHF010106T0_15;
	}

	public String getEHF010106T0_16() {
		return EHF010106T0_16;
	}

	public void setEHF010106T0_16(String eHF010106T0_16) {
		EHF010106T0_16 = eHF010106T0_16;
	}

	public String getEHF010106T0_17() {
		return EHF010106T0_17;
	}

	public void setEHF010106T0_17(String eHF010106T0_17) {
		EHF010106T0_17 = eHF010106T0_17;
	}

	public String getEHF010106T0_18() {
		return EHF010106T0_18;
	}

	public void setEHF010106T0_18(String eHF010106T0_18) {
		EHF010106T0_18 = eHF010106T0_18;
	}

	public String getEHF010106T0_1801() {
		return EHF010106T0_1801;
	}

	public void setEHF010106T0_1801(String eHF010106T0_1801) {
		EHF010106T0_1801 = eHF010106T0_1801;
	}

	public String getEHF010106T0_1802() {
		return EHF010106T0_1802;
	}

	public void setEHF010106T0_1802(String eHF010106T0_1802) {
		EHF010106T0_1802 = eHF010106T0_1802;
	}

	public String getEHF010106T0_19() {
		return EHF010106T0_19;
	}

	public void setEHF010106T0_19(String eHF010106T0_19) {
		EHF010106T0_19 = eHF010106T0_19;
	}

	public boolean isEHF010106T0_20() {
		return EHF010106T0_20;
	}

	public void setEHF010106T0_20(boolean eHF010106T0_20) {
		EHF010106T0_20 = eHF010106T0_20;
	}

	public boolean isEHF010106T0_21() {
		return EHF010106T0_21;
	}

	public void setEHF010106T0_21(boolean eHF010106T0_21) {
		EHF010106T0_21 = eHF010106T0_21;
	}

	public String getEHF010106T0_22() {
		return EHF010106T0_22;
	}

	public void setEHF010106T0_22(String eHF010106T0_22) {
		EHF010106T0_22 = eHF010106T0_22;
	}

	public String getEHF010106T0_23() {
		return EHF010106T0_23;
	}

	public void setEHF010106T0_23(String eHF010106T0_23) {
		EHF010106T0_23 = eHF010106T0_23;
	}

	public String getEHF010106T0_24() {
		return EHF010106T0_24;
	}

	public void setEHF010106T0_24(String eHF010106T0_24) {
		EHF010106T0_24 = eHF010106T0_24;
	}

	public String getEHF010106T0_25() {
		return EHF010106T0_25;
	}

	public void setEHF010106T0_25(String eHF010106T0_25) {
		EHF010106T0_25 = eHF010106T0_25;
	}

	public String getHR_JobDate() {
		return HR_JobDate;
	}

	public void setHR_JobDate(String hRJobDate) {
		HR_JobDate = hRJobDate;
	}

	public String getHR_OffJobDate() {
		return HR_OffJobDate;
	}

	public void setHR_OffJobDate(String hROffJobDate) {
		HR_OffJobDate = hROffJobDate;
	}

	public String getHR_Seniority() {
		return HR_Seniority;
	}

	public void setHR_Seniority(String hRSeniority) {
		HR_Seniority = hRSeniority;
	}

	public String getHR_SeniorityAdjust() {
		return HR_SeniorityAdjust;
	}

	public void setHR_SeniorityAdjust(String hRSeniorityAdjust) {
		HR_SeniorityAdjust = hRSeniorityAdjust;
	}

	public String getHR_CompanySysNo() {
		return HR_CompanySysNo;
	}

	public void setHR_CompanySysNo(String hRCompanySysNo) {
		HR_CompanySysNo = hRCompanySysNo;
	}

	public String getLogin_No() {
		return Login_No;
	}

	public void setLogin_No(String loginNo) {
		Login_No = loginNo;
	}

	public String getEHF010106T0_TYPE() {
		return EHF010106T0_TYPE;
	}

	public void setEHF010106T0_TYPE(String eHF010106T0TYPE) {
		EHF010106T0_TYPE = eHF010106T0TYPE;
	}

	public List getEHF010106T0_LIST() {
		return EHF010106T0_LIST;
	}

	public void setEHF010106T0_LIST(List eHF010106T0LIST) {
		EHF010106T0_LIST = eHF010106T0LIST;
	}

	public FormFile getUPLOADFILE() {
		return UPLOADFILE;
	}

	public void setUPLOADFILE(FormFile uPLOADFILE) {
		UPLOADFILE = uPLOADFILE;
	}

	public String getEHF010106T1_01() {
		return EHF010106T1_01;
	}

	public void setEHF010106T1_01(String eHF010106T1_01) {
		EHF010106T1_01 = eHF010106T1_01;
	}

	public String getEHF010106T1_02() {
		return EHF010106T1_02;
	}

	public void setEHF010106T1_02(String eHF010106T1_02) {
		EHF010106T1_02 = eHF010106T1_02;
	}

	public String getEHF010106T1_0201() {
		return EHF010106T1_0201;
	}

	public void setEHF010106T1_0201(String eHF010106T1_0201) {
		EHF010106T1_0201 = eHF010106T1_0201;
	}

	public String getEHF010106T1_0202() {
		return EHF010106T1_0202;
	}

	public void setEHF010106T1_0202(String eHF010106T1_0202) {
		EHF010106T1_0202 = eHF010106T1_0202;
	}

	public String getEHF010106T1_0203() {
		return EHF010106T1_0203;
	}

	public void setEHF010106T1_0203(String eHF010106T1_0203) {
		EHF010106T1_0203 = eHF010106T1_0203;
	}

	public String getEHF010106T1_0204() {
		return EHF010106T1_0204;
	}

	public void setEHF010106T1_0204(String eHF010106T1_0204) {
		EHF010106T1_0204 = eHF010106T1_0204;
	}

	public int getEHF010106T2_01() {
		return EHF010106T2_01;
	}

	public void setEHF010106T2_01(int eHF010106T2_01) {
		EHF010106T2_01 = eHF010106T2_01;
	}

	public String getEHF010106T2_02() {
		return EHF010106T2_02;
	}

	public void setEHF010106T2_02(String eHF010106T2_02) {
		EHF010106T2_02 = eHF010106T2_02;
	}

	public String getEHF010106T2_03() {
		return EHF010106T2_03;
	}

	public void setEHF010106T2_03(String eHF010106T2_03) {
		EHF010106T2_03 = eHF010106T2_03;
	}

	public String getEHF010106T2_04() {
		return EHF010106T2_04;
	}

	public void setEHF010106T2_04(String eHF010106T2_04) {
		EHF010106T2_04 = eHF010106T2_04;
	}

	public String getEHF010106T2_05() {
		return EHF010106T2_05;
	}

	public void setEHF010106T2_05(String eHF010106T2_05) {
		EHF010106T2_05 = eHF010106T2_05;
	}

	public String getEHF010106T2_06() {
		return EHF010106T2_06;
	}

	public void setEHF010106T2_06(String eHF010106T2_06) {
		EHF010106T2_06 = eHF010106T2_06;
	}

	public String getEHF010106T2_07() {
		return EHF010106T2_07;
	}

	public void setEHF010106T2_07(String eHF010106T2_07) {
		EHF010106T2_07 = eHF010106T2_07;
	}

	public String getEHF010106T2_08() {
		return EHF010106T2_08;
	}

	public void setEHF010106T2_08(String eHF010106T2_08) {
		EHF010106T2_08 = eHF010106T2_08;
	}

	public String getEHF010106T2_09() {
		return EHF010106T2_09;
	}

	public void setEHF010106T2_09(String eHF010106T2_09) {
		EHF010106T2_09 = eHF010106T2_09;
	}

	public List getEHF010106T2_LIST() {
		return EHF010106T2_LIST;
	}

	public void setEHF010106T2_LIST(List eHF010106T2LIST) {
		EHF010106T2_LIST = eHF010106T2LIST;
	}

	public int getEHF010106T3_01() {
		return EHF010106T3_01;
	}

	public void setEHF010106T3_01(int eHF010106T3_01) {
		EHF010106T3_01 = eHF010106T3_01;
	}

	public String getEHF010106T3_02() {
		return EHF010106T3_02;
	}

	public void setEHF010106T3_02(String eHF010106T3_02) {
		EHF010106T3_02 = eHF010106T3_02;
	}

	public String getEHF010106T3_03() {
		return EHF010106T3_03;
	}

	public void setEHF010106T3_03(String eHF010106T3_03) {
		EHF010106T3_03 = eHF010106T3_03;
	}

	public String getEHF010106T3_04() {
		return EHF010106T3_04;
	}

	public void setEHF010106T3_04(String eHF010106T3_04) {
		EHF010106T3_04 = eHF010106T3_04;
	}

	public String getEHF010106T3_05() {
		return EHF010106T3_05;
	}

	public void setEHF010106T3_05(String eHF010106T3_05) {
		EHF010106T3_05 = eHF010106T3_05;
	}

	public String getEHF010106T3_06() {
		return EHF010106T3_06;
	}

	public void setEHF010106T3_06(String eHF010106T3_06) {
		EHF010106T3_06 = eHF010106T3_06;
	}

	public String getEHF010106T3_07() {
		return EHF010106T3_07;
	}

	public void setEHF010106T3_07(String eHF010106T3_07) {
		EHF010106T3_07 = eHF010106T3_07;
	}

	public List getEHF010106T3_LIST() {
		return EHF010106T3_LIST;
	}

	public void setEHF010106T3_LIST(List eHF010106T3LIST) {
		EHF010106T3_LIST = eHF010106T3LIST;
	}

	public int getEHF010106T4_01() {
		return EHF010106T4_01;
	}

	public void setEHF010106T4_01(int eHF010106T4_01) {
		EHF010106T4_01 = eHF010106T4_01;
	}

	public String getEHF010106T4_02() {
		return EHF010106T4_02;
	}

	public void setEHF010106T4_02(String eHF010106T4_02) {
		EHF010106T4_02 = eHF010106T4_02;
	}

	public String getEHF010106T4_03() {
		return EHF010106T4_03;
	}

	public void setEHF010106T4_03(String eHF010106T4_03) {
		EHF010106T4_03 = eHF010106T4_03;
	}

	public String getEHF010106T4_04() {
		return EHF010106T4_04;
	}

	public void setEHF010106T4_04(String eHF010106T4_04) {
		EHF010106T4_04 = eHF010106T4_04;
	}

	public String getEHF010106T4_05() {
		return EHF010106T4_05;
	}

	public void setEHF010106T4_05(String eHF010106T4_05) {
		EHF010106T4_05 = eHF010106T4_05;
	}

	public String getEHF010106T4_06() {
		return EHF010106T4_06;
	}

	public void setEHF010106T4_06(String eHF010106T4_06) {
		EHF010106T4_06 = eHF010106T4_06;
	}

	public String getEHF010106T4_07() {
		return EHF010106T4_07;
	}

	public void setEHF010106T4_07(String eHF010106T4_07) {
		EHF010106T4_07 = eHF010106T4_07;
	}

	public List getEHF010106T4_LIST() {
		return EHF010106T4_LIST;
	}

	public void setEHF010106T4_LIST(List eHF010106T4LIST) {
		EHF010106T4_LIST = eHF010106T4LIST;
	}

	public int getEHF010106T5_01() {
		return EHF010106T5_01;
	}

	public void setEHF010106T5_01(int eHF010106T5_01) {
		EHF010106T5_01 = eHF010106T5_01;
	}

	public String getEHF010106T5_02() {
		return EHF010106T5_02;
	}

	public void setEHF010106T5_02(String eHF010106T5_02) {
		EHF010106T5_02 = eHF010106T5_02;
	}

	public int getEHF010106T5_03() {
		return EHF010106T5_03;
	}

	public void setEHF010106T5_03(int eHF010106T5_03) {
		EHF010106T5_03 = eHF010106T5_03;
	}

	public String getEHF010106T5_04() {
		return EHF010106T5_04;
	}

	public void setEHF010106T5_04(String eHF010106T5_04) {
		EHF010106T5_04 = eHF010106T5_04;
	}

	public String getEHF010106T5_05() {
		return EHF010106T5_05;
	}

	public void setEHF010106T5_05(String eHF010106T5_05) {
		EHF010106T5_05 = eHF010106T5_05;
	}

	public List getEHF010106T5_LIST() {
		return EHF010106T5_LIST;
	}

	public void setEHF010106T5_LIST(List eHF010106T5LIST) {
		EHF010106T5_LIST = eHF010106T5LIST;
	}

	public String getHR_DepartmentSysNo() {
		return HR_DepartmentSysNo;
	}

	public void setHR_DepartmentSysNo(String hRDepartmentSysNo) {
		HR_DepartmentSysNo = hRDepartmentSysNo;
	}

	public String getEHF010106T6_01() {
		return EHF010106T6_01;
	}

	public void setEHF010106T6_01(String eHF010106T6_01) {
		EHF010106T6_01 = eHF010106T6_01;
	}

	public String getEHF010106T6_02() {
		return EHF010106T6_02;
	}

	public void setEHF010106T6_02(String eHF010106T6_02) {
		EHF010106T6_02 = eHF010106T6_02;
	}

	public boolean isEHF010106T6_03() {
		return EHF010106T6_03;
	}

	public void setEHF010106T6_03(boolean eHF010106T6_03) {
		EHF010106T6_03 = eHF010106T6_03;
	}

	public String getHR_JobNameSysNo() {
		return HR_JobNameSysNo;
	}

	public void setHR_JobNameSysNo(String hRJobNameSysNo) {
		HR_JobNameSysNo = hRJobNameSysNo;
	}

	public String getHR_IsManager() {
		return HR_IsManager;
	}

	public void setHR_IsManager(String hRIsManager) {
		HR_IsManager = hRIsManager;
	}

	public String getHR_ManagerLevel() {
		return HR_ManagerLevel;
	}

	public void setHR_ManagerLevel(String hRManagerLevel) {
		HR_ManagerLevel = hRManagerLevel;
	}

	public String getHR_DesignateManager() {
		return HR_DesignateManager;
	}

	public void setHR_DesignateManager(String hRDesignateManager) {
		HR_DesignateManager = hRDesignateManager;
	}

	public String getHR_JobAgent1() {
		return HR_JobAgent1;
	}

	public void setHR_JobAgent1(String hRJobAgent1) {
		HR_JobAgent1 = hRJobAgent1;
	}

	public String getHR_JobAgent2() {
		return HR_JobAgent2;
	}

	public void setHR_JobAgent2(String hRJobAgent2) {
		HR_JobAgent2 = hRJobAgent2;
	}

	public String getHR_JobAgent3() {
		return HR_JobAgent3;
	}

	public void setHR_JobAgent3(String hRJobAgent3) {
		HR_JobAgent3 = hRJobAgent3;
	}

	public String getHR_CheckLeave() {
		return HR_CheckLeave;
	}

	public void setHR_CheckLeave(String hRCheckLeave) {
		HR_CheckLeave = hRCheckLeave;
	}

	public String getHR_LeaveStartTime() {
		return HR_LeaveStartTime;
	}

	public void setHR_LeaveStartTime(String hRLeaveStartTime) {
		HR_LeaveStartTime = hRLeaveStartTime;
	}

	public String getHR_LeaveEndTime() {
		return HR_LeaveEndTime;
	}

	public void setHR_LeaveEndTime(String hRLeaveEndTime) {
		HR_LeaveEndTime = hRLeaveEndTime;
	}

	public String getHR_JobName() {
		return HR_JobName;
	}

	public void setHR_JobName(String hRJobName) {
		HR_JobName = hRJobName;
	}

	public String getHR_JobNameNo() {
		return HR_JobNameNo;
	}

	public void setHR_JobNameNo(String hRJobNameNo) {
		HR_JobNameNo = hRJobNameNo;
	}

	public String getHR_DepartmentNo() {
		return HR_DepartmentNo;
	}

	public void setHR_DepartmentNo(String hRDepartmentNo) {
		HR_DepartmentNo = hRDepartmentNo;
	}

	public String getHR_DepartmentName() {
		return HR_DepartmentName;
	}

	public void setHR_DepartmentName(String hRDepartmentName) {
		HR_DepartmentName = hRDepartmentName;
	}

	public int getEHF010100T1_01() {
		return EHF010100T1_01;
	}

	public void setEHF010100T1_01(int eHF010100T1_01) {
		EHF010100T1_01 = eHF010100T1_01;
	}

	public String getEHF010100T1_02() {
		return EHF010100T1_02;
	}

	public void setEHF010100T1_02(String eHF010100T1_02) {
		EHF010100T1_02 = eHF010100T1_02;
	}

	public String getEHF010100T1_03() {
		return EHF010100T1_03;
	}

	public void setEHF010100T1_03(String eHF010100T1_03) {
		EHF010100T1_03 = eHF010100T1_03;
	}

	public String getEHF010100T1_04() {
		return EHF010100T1_04;
	}

	public void setEHF010100T1_04(String eHF010100T1_04) {
		EHF010100T1_04 = eHF010100T1_04;
	}

	public String getEHF010100T1_04_TXT() {
		return EHF010100T1_04_TXT;
	}

	public void setEHF010100T1_04_TXT(String eHF010100T1_04TXT) {
		EHF010100T1_04_TXT = eHF010100T1_04TXT;
	}

	public String getEHF010100T1_04_KEY() {
		return EHF010100T1_04_KEY;
	}

	public void setEHF010100T1_04_KEY(String eHF010100T1_04KEY) {
		EHF010100T1_04_KEY = eHF010100T1_04KEY;
	}

	public String getEHF010100T1_04_HIDE() {
		return EHF010100T1_04_HIDE;
	}

	public void setEHF010100T1_04_HIDE(String eHF010100T1_04HIDE) {
		EHF010100T1_04_HIDE = eHF010100T1_04HIDE;
	}

	public String getEHF010100T1_05() {
		return EHF010100T1_05;
	}

	public void setEHF010100T1_05(String eHF010100T1_05) {
		EHF010100T1_05 = eHF010100T1_05;
	}

	public String getEHF010100T1_06() {
		return EHF010100T1_06;
	}

	public void setEHF010100T1_06(String eHF010100T1_06) {
		EHF010100T1_06 = eHF010100T1_06;
	}

	public String getEHF020403T0_01() {
		return EHF020403T0_01;
	}

	public void setEHF020403T0_01(String eHF020403T0_01) {
		EHF020403T0_01 = eHF020403T0_01;
	}

	public String getEHF020403T0_02() {
		return EHF020403T0_02;
	}

	public void setEHF020403T0_02(String eHF020403T0_02) {
		EHF020403T0_02 = eHF020403T0_02;
	}

	public String getEHF020403T0_03() {
		return EHF020403T0_03;
	}

	public void setEHF020403T0_03(String eHF020403T0_03) {
		EHF020403T0_03 = eHF020403T0_03;
	}

	public String getEHF020403T0_04() {
		return EHF020403T0_04;
	}

	public void setEHF020403T0_04(String eHF020403T0_04) {
		EHF020403T0_04 = eHF020403T0_04;
	}

	public String getEHF020403T0_05() {
		return EHF020403T0_05;
	}

	public void setEHF020403T0_05(String eHF020403T0_05) {
		EHF020403T0_05 = eHF020403T0_05;
	}

	public boolean isCHECKED() {
		return CHECKED;
	}

	public void setCHECKED(boolean cHECKED) {
		CHECKED = cHECKED;
	}

	public String getEHF020403T1_01() {
		return EHF020403T1_01;
	}

	public void setEHF020403T1_01(String eHF020403T1_01) {
		EHF020403T1_01 = eHF020403T1_01;
	}

	public String getEHF020403T1_02() {
		return EHF020403T1_02;
	}

	public void setEHF020403T1_02(String eHF020403T1_02) {
		EHF020403T1_02 = eHF020403T1_02;
	}

	public String getEHF020403T1_03() {
		return EHF020403T1_03;
	}

	public void setEHF020403T1_03(String eHF020403T1_03) {
		EHF020403T1_03 = eHF020403T1_03;
	}

	public String getEHF020403T1_04() {
		return EHF020403T1_04;
	}

	public void setEHF020403T1_04(String eHF020403T1_04) {
		EHF020403T1_04 = eHF020403T1_04;
	}

	public String getEHF020403T1_04_START() {
		return EHF020403T1_04_START;
	}

	public void setEHF020403T1_04_START(String eHF020403T1_04START) {
		EHF020403T1_04_START = eHF020403T1_04START;
	}

	public String getEHF020403T1_04_START_HH() {
		return EHF020403T1_04_START_HH;
	}

	public void setEHF020403T1_04_START_HH(String eHF020403T1_04STARTHH) {
		EHF020403T1_04_START_HH = eHF020403T1_04STARTHH;
	}

	public String getEHF020403T1_04_START_MM() {
		return EHF020403T1_04_START_MM;
	}

	public void setEHF020403T1_04_START_MM(String eHF020403T1_04STARTMM) {
		EHF020403T1_04_START_MM = eHF020403T1_04STARTMM;
	}

	public String getEHF020403T1_04_END() {
		return EHF020403T1_04_END;
	}

	public void setEHF020403T1_04_END(String eHF020403T1_04END) {
		EHF020403T1_04_END = eHF020403T1_04END;
	}

	public String getEHF020403T1_04_END_HH() {
		return EHF020403T1_04_END_HH;
	}

	public void setEHF020403T1_04_END_HH(String eHF020403T1_04ENDHH) {
		EHF020403T1_04_END_HH = eHF020403T1_04ENDHH;
	}

	public String getEHF020403T1_04_END_MM() {
		return EHF020403T1_04_END_MM;
	}

	public void setEHF020403T1_04_END_MM(String eHF020403T1_04ENDMM) {
		EHF020403T1_04_END_MM = eHF020403T1_04ENDMM;
	}

	public String getEHF020403T1_05() {
		return EHF020403T1_05;
	}

	public void setEHF020403T1_05(String eHF020403T1_05) {
		EHF020403T1_05 = eHF020403T1_05;
	}

	public String getEHF020403T1_06() {
		return EHF020403T1_06;
	}

	public void setEHF020403T1_06(String eHF020403T1_06) {
		EHF020403T1_06 = eHF020403T1_06;
	}

	public List getEHF020403C() {
		return EHF020403C;
	}

	public void setEHF020403C(List eHF020403C) {
		EHF020403C = eHF020403C;
	}

	public String getEHF030200T0_01() {
		return EHF030200T0_01;
	}

	public void setEHF030200T0_01(String eHF030200T0_01) {
		EHF030200T0_01 = eHF030200T0_01;
	}

	public String getEHF030200T0_02() {
		return EHF030200T0_02;
	}

	public void setEHF030200T0_02(String eHF030200T0_02) {
		EHF030200T0_02 = eHF030200T0_02;
	}

	public String getEHF030200T0_03() {
		return EHF030200T0_03;
	}

	public void setEHF030200T0_03(String eHF030200T0_03) {
		EHF030200T0_03 = eHF030200T0_03;
	}

	public int getEHF030200T0_04() {
		return EHF030200T0_04;
	}

	public void setEHF030200T0_04(int eHF030200T0_04) {
		EHF030200T0_04 = eHF030200T0_04;
	}

	public String getEHF030200T0_05() {
		return EHF030200T0_05;
	}

	public void setEHF030200T0_05(String eHF030200T0_05) {
		EHF030200T0_05 = eHF030200T0_05;
	}

	public String getEHF030200T0_06() {
		return EHF030200T0_06;
	}

	public void setEHF030200T0_06(String eHF030200T0_06) {
		EHF030200T0_06 = eHF030200T0_06;
	}

	public String getEHF030200T0_06_AC() {
		return EHF030200T0_06_AC;
	}

	public void setEHF030200T0_06_AC(String eHF030200T0_06AC) {
		EHF030200T0_06_AC = eHF030200T0_06AC;
	}

	public String getEHF030200T0_07() {
		return EHF030200T0_07;
	}

	public void setEHF030200T0_07(String eHF030200T0_07) {
		EHF030200T0_07 = eHF030200T0_07;
	}

	public String getEHF030200T0_08() {
		return EHF030200T0_08;
	}

	public void setEHF030200T0_08(String eHF030200T0_08) {
		EHF030200T0_08 = eHF030200T0_08;
	}

	public int getEHF030200T0_09() {
		return EHF030200T0_09;
	}

	public void setEHF030200T0_09(int eHF030200T0_09) {
		EHF030200T0_09 = eHF030200T0_09;
	}

	public String getEHF030200T0_10() {
		return EHF030200T0_10;
	}

	public void setEHF030200T0_10(String eHF030200T0_10) {
		EHF030200T0_10 = eHF030200T0_10;
	}

	public int getEHF030200T0_11() {
		return EHF030200T0_11;
	}

	public void setEHF030200T0_11(int eHF030200T0_11) {
		EHF030200T0_11 = eHF030200T0_11;
	}

	public String getEHF030200T0_12() {
		return EHF030200T0_12;
	}

	public void setEHF030200T0_12(String eHF030200T0_12) {
		EHF030200T0_12 = eHF030200T0_12;
	}

	public String getEHF030200T0_13() {
		return EHF030200T0_13;
	}

	public void setEHF030200T0_13(String eHF030200T0_13) {
		EHF030200T0_13 = eHF030200T0_13;
	}

	public String getEHF030200T0_14() {
		return EHF030200T0_14;
	}

	public void setEHF030200T0_14(String eHF030200T0_14) {
		EHF030200T0_14 = eHF030200T0_14;
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

	public String getEHF030200T1_03() {
		return EHF030200T1_03;
	}

	public void setEHF030200T1_03(String eHF030200T1_03) {
		EHF030200T1_03 = eHF030200T1_03;
	}

	public String getEHF030200T1_02_01() {
		return EHF030200T1_02_01;
	}

	public void setEHF030200T1_02_01(String eHF030200T1_02_01) {
		EHF030200T1_02_01 = eHF030200T1_02_01;
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

	public List getEHF030200C() {
		return EHF030200C;
	}

	public void setEHF030200C(List eHF030200C) {
		EHF030200C = eHF030200C;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEHF030300T0_01() {
		return EHF030300T0_01;
	}

	public void setEHF030300T0_01(String eHF030300T0_01) {
		EHF030300T0_01 = eHF030300T0_01;
	}

	public String getEHF030300T0_01_TXT() {
		return EHF030300T0_01_TXT;
	}

	public void setEHF030300T0_01_TXT(String eHF030300T0_01TXT) {
		EHF030300T0_01_TXT = eHF030300T0_01TXT;
	}

	public String getEHF030300T0_02() {
		return EHF030300T0_02;
	}

	public void setEHF030300T0_02(String eHF030300T0_02) {
		EHF030300T0_02 = eHF030300T0_02;
	}

	public String getEHF030300T0_02_TXT() {
		return EHF030300T0_02_TXT;
	}

	public void setEHF030300T0_02_TXT(String eHF030300T0_02TXT) {
		EHF030300T0_02_TXT = eHF030300T0_02TXT;
	}

	public String getEHF030300T0_03() {
		return EHF030300T0_03;
	}

	public void setEHF030300T0_03(String eHF030300T0_03) {
		EHF030300T0_03 = eHF030300T0_03;
	}

	public int getEHF030300T0_04() {
		return EHF030300T0_04;
	}

	public void setEHF030300T0_04(int eHF030300T0_04) {
		EHF030300T0_04 = eHF030300T0_04;
	}

	public String getEHF030300T0_05() {
		return EHF030300T0_05;
	}

	public void setEHF030300T0_05(String eHF030300T0_05) {
		EHF030300T0_05 = eHF030300T0_05;
	}

	public int getEHF030300T0_05_HIDE() {
		return EHF030300T0_05_HIDE;
	}

	public void setEHF030300T0_05_HIDE(int eHF030300T0_05HIDE) {
		EHF030300T0_05_HIDE = eHF030300T0_05HIDE;
	}

	public String getEHF030300T0_05_NUMBER() {
		return EHF030300T0_05_NUMBER;
	}

	public void setEHF030300T0_05_NUMBER(String eHF030300T0_05NUMBER) {
		EHF030300T0_05_NUMBER = eHF030300T0_05NUMBER;
	}

	public String getEHF030300T0_05_VERSION() {
		return EHF030300T0_05_VERSION;
	}

	public void setEHF030300T0_05_VERSION(String eHF030300T0_05VERSION) {
		EHF030300T0_05_VERSION = eHF030300T0_05VERSION;
	}

	public String getEHF030300T0_05_VERSION_NAME() {
		return EHF030300T0_05_VERSION_NAME;
	}

	public void setEHF030300T0_05_VERSION_NAME(String eHF030300T0_05VERSIONNAME) {
		EHF030300T0_05_VERSION_NAME = eHF030300T0_05VERSIONNAME;
	}

	public int getEHF030300T0_06() {
		return EHF030300T0_06;
	}

	public void setEHF030300T0_06(int eHF030300T0_06) {
		EHF030300T0_06 = eHF030300T0_06;
	}

	public String getEHF030300T0_07() {
		return EHF030300T0_07;
	}

	public void setEHF030300T0_07(String eHF030300T0_07) {
		EHF030300T0_07 = eHF030300T0_07;
	}

	public int getEHF030300T0_07_HIDE() {
		return EHF030300T0_07_HIDE;
	}

	public void setEHF030300T0_07_HIDE(int eHF030300T0_07HIDE) {
		EHF030300T0_07_HIDE = eHF030300T0_07HIDE;
	}

	public String getEHF030300T0_07_NUMBER() {
		return EHF030300T0_07_NUMBER;
	}

	public void setEHF030300T0_07_NUMBER(String eHF030300T0_07NUMBER) {
		EHF030300T0_07_NUMBER = eHF030300T0_07NUMBER;
	}

	public String getEHF030300T0_07_VERSION() {
		return EHF030300T0_07_VERSION;
	}

	public void setEHF030300T0_07_VERSION(String eHF030300T0_07VERSION) {
		EHF030300T0_07_VERSION = eHF030300T0_07VERSION;
	}

	public String getEHF030300T0_07_VERSION_NAME() {
		return EHF030300T0_07_VERSION_NAME;
	}

	public void setEHF030300T0_07_VERSION_NAME(String eHF030300T0_07VERSIONNAME) {
		EHF030300T0_07_VERSION_NAME = eHF030300T0_07VERSIONNAME;
	}

	public int getEHF030300T0_08() {
		return EHF030300T0_08;
	}

	public void setEHF030300T0_08(int eHF030300T0_08) {
		EHF030300T0_08 = eHF030300T0_08;
	}

	public int getEHF030300T0_09() {
		return EHF030300T0_09;
	}

	public void setEHF030300T0_09(int eHF030300T0_09) {
		EHF030300T0_09 = eHF030300T0_09;
	}

	public int getEHF030300T0_10() {
		return EHF030300T0_10;
	}

	public void setEHF030300T0_10(int eHF030300T0_10) {
		EHF030300T0_10 = eHF030300T0_10;
	}

	public int getEHF030300T0_11() {
		return EHF030300T0_11;
	}

	public void setEHF030300T0_11(int eHF030300T0_11) {
		EHF030300T0_11 = eHF030300T0_11;
	}

	public String getEHF030300T0_12() {
		return EHF030300T0_12;
	}

	public void setEHF030300T0_12(String eHF030300T0_12) {
		EHF030300T0_12 = eHF030300T0_12;
	}

	public String getEHF030300T0_13() {
		return EHF030300T0_13;
	}

	public void setEHF030300T0_13(String eHF030300T0_13) {
		EHF030300T0_13 = eHF030300T0_13;
	}

	public String getEHF030300T0_14() {
		return EHF030300T0_14;
	}

	public void setEHF030300T0_14(String eHF030300T0_14) {
		EHF030300T0_14 = eHF030300T0_14;
	}

	public int getEHF030300T0_15() {
		return EHF030300T0_15;
	}

	public void setEHF030300T0_15(int eHF030300T0_15) {
		EHF030300T0_15 = eHF030300T0_15;
	}

	public int getEHF030300T0_16() {
		return EHF030300T0_16;
	}

	public void setEHF030300T0_16(int eHF030300T0_16) {
		EHF030300T0_16 = eHF030300T0_16;
	}

	public String getEHF030300T0_05_START() {
		return EHF030300T0_05_START;
	}

	public void setEHF030300T0_05_START(String eHF030300T0_05START) {
		EHF030300T0_05_START = eHF030300T0_05START;
	}

	public String getEHF030300T0_05_END() {
		return EHF030300T0_05_END;
	}

	public void setEHF030300T0_05_END(String eHF030300T0_05END) {
		EHF030300T0_05_END = eHF030300T0_05END;
	}

	public String getEHF030300T0_07_START() {
		return EHF030300T0_07_START;
	}

	public void setEHF030300T0_07_START(String eHF030300T0_07START) {
		EHF030300T0_07_START = eHF030300T0_07START;
	}

	public String getEHF030300T0_07_END() {
		return EHF030300T0_07_END;
	}

	public void setEHF030300T0_07_END(String eHF030300T0_07END) {
		EHF030300T0_07_END = eHF030300T0_07END;
	}

	public String getEHF030300T1_01() {
		return EHF030300T1_01;
	}

	public void setEHF030300T1_01(String eHF030300T1_01) {
		EHF030300T1_01 = eHF030300T1_01;
	}

	public String getEHF030300T1_02() {
		return EHF030300T1_02;
	}

	public void setEHF030300T1_02(String eHF030300T1_02) {
		EHF030300T1_02 = eHF030300T1_02;
	}

	public String getEHF030300T1_03() {
		return EHF030300T1_03;
	}

	public void setEHF030300T1_03(String eHF030300T1_03) {
		EHF030300T1_03 = eHF030300T1_03;
	}

	public String getEHF030300T1_04() {
		return EHF030300T1_04;
	}

	public void setEHF030300T1_04(String eHF030300T1_04) {
		EHF030300T1_04 = eHF030300T1_04;
	}

	public String getEHF030300T1_05() {
		return EHF030300T1_05;
	}

	public void setEHF030300T1_05(String eHF030300T1_05) {
		EHF030300T1_05 = eHF030300T1_05;
	}

	public String getEHF030300T1_06() {
		return EHF030300T1_06;
	}

	public void setEHF030300T1_06(String eHF030300T1_06) {
		EHF030300T1_06 = eHF030300T1_06;
	}

	public List getEHF030300C() {
		return EHF030300C;
	}

	public void setEHF030300C(List eHF030300C) {
		EHF030300C = eHF030300C;
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

	public String getHR_LastUpdateDate() {
		return HR_LastUpdateDate;
	}

	public void setHR_LastUpdateDate(String hRLastUpdateDate) {
		HR_LastUpdateDate = hRLastUpdateDate;
	}

	

}
