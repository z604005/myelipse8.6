package com.spon.ems.hr.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import com.spon.ems.abs.interfaces.ValidateForm;

import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;
import com.spon.utils.util.HR_TOOLS;

public class EHF010100M0F extends ActionForm implements ValidateForm {
	
	//員工人事基本資料
	private String EHF010100T0_01;//員工系統代碼
	private String EHF010100T0_02;//員工工號
	private String EHF010100T0_I;//身分證字號
	private String EHF010100T0_03;//護照號碼
	private String EHF010100T0_04;//居留證號碼
	private String EHF010100T0_05;//姓名(中)
	private String EHF010100T0_06;//姓名(英)
	private String EHF010100T0_07;//員工類別
	private String EHF010100T0_08;//性別
	private String EHF010100T0_09;//婚姻狀況
	private String EHF010100T0_10;//籍貫
	private String EHF010100T0_11;//生日
	private String EHF010100T0_12;//戶籍電話
	private String EHF010100T0_1201;
	private String EHF010100T0_1202;
	private String EHF010100T0_13;//戶籍地址
	private String EHF010100T0_14;//聯絡電話
	private String EHF010100T0_1401;
	private String EHF010100T0_1402;
	private String EHF010100T0_15;//聯絡地址
	private String EHF010100T0_16;//手機號碼
	private String EHF010100T0_17;//緊急聯絡人
	private String EHF010100T0_18;//緊急聯絡人關係
	private String EHF010100T0_19;//緊急聯絡人電話
	private String EHF010100T0_1901;
	private String EHF010100T0_1902;
	private String EHF010100T0_20;//專長
	private boolean EHF010100T0_21;//是否有汽車駕照
	private boolean EHF010100T0_22;//是否有機車駕照
	private String EHF010100T0_23;//其他證照
	private String EHF010100T0_24;//上傳照片檔案路徑
	private String EHF010100T0_25;//上傳身分證明檔案路徑
	private String EHF010100T0_26;//人資備註
	private String EHF010100T0_27;//到職日期
	private String EHF010100T0_28;//離職日期
	private String EHF010100T0_29;//服務年資
	private String EHF010100T0_30;//服務年資調整值(天)
	private String EHF010100T0_31;//會員編號
	private String HR_CompanySysNo;
	
	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_CREATE;
	private String HR_LastUpdateDate;
	
	private String Login_No;	//登入帳號
	private String EHF010100T0_TYPE;
	private List EHF010100T0_LIST = new ArrayList();
	
	private FormFile UPLOADFILE;
	
	//員工狀況明細
	private String EHF010100T1_01;//員工系統代碼
	private String EHF010100T1_02;//職務狀況
	private String EHF010100T1_03;//日期
	private String EHF010100T1_0301;
	private String EHF010100T1_0302;
	private String EHF010100T1_0303;
	private String EHF010100T1_0304;
	private List EHF010100T1_LIST = new ArrayList();
	
	//員工履經歷明細
	private String EHF010100T2_01;//員工系統代碼
	private int EHF010100T2_02;//員工筆數
	private String EHF010100T2_03;//公司名稱
	private String EHF010100T2_04;//公司產業別
	private String EHF010100T2_05;//公司部門
	private String EHF010100T2_06;//公司職務
	private String EHF010100T2_07;//工作內容
	private String EHF010100T2_08;//工作日期(起)
	private String EHF010100T2_09;//工作日期(迄)
	private String EHF010100T2_10;//離職原因
	private List EHF010100T2_LIST = new ArrayList();
	
	//員工學歷明細
	private String EHF010100T3_01;//員工系統代碼
	private int EHF010100T3_02;//員工筆數
	private String EHF010100T3_03;//學歷
	private String EHF010100T3_04;//學校名稱
	private String EHF010100T3_05;//學校科系
	private String EHF010100T3_06;//在校期間(起)
	private String EHF010100T3_07;//在校期間(迄)
	private String EHF010100T3_08;//畢(肄)業
	private List EHF010100T3_LIST = new ArrayList();
	
	//證照資料明細
	private String EHF010100T4_01;//員工系統代碼
	private int EHF010100T4_02;//員工筆數
	private String EHF010100T4_03;//證照名稱
	private String EHF010100T4_04;//檔案名稱
	private String EHF010100T4_05;//有效期間(起)
	private String EHF010100T4_06;//有效期間(迄)
	private String EHF010100T4_07;//證照檔案上傳路徑
	private String EHF010100T4_08;
	private List EHF010100T4_LIST = new ArrayList();
	
	//家庭關係明細
	private String EHF010100T5_01;//員工系統代碼
	private int EHF010100T5_02;//員工筆數
	private String EHF010100T5_03;//家屬姓名
	private int EHF010100T5_04;//家屬年齡
	private String EHF010100T5_05;//家屬職業
	private String EHF010100T5_06;//家屬稱謂
	private List EHF010100T5_LIST = new ArrayList();
	
	//職務現況明細
	private String EHF010100T6_01;//員工系統代碼
	private String EHF010100T6_02;//部門系統代碼
	private String EHF010100T6_03;//部門歸屬區間(起)
	private String EHF010100T6_04;//部門歸屬區間(迄)
	private boolean EHF010100T6_05;//主要歸屬部門
	private String EHF010100T6_07;//是否為主管
	private String EHF010100T6_08;//級等
	private String EHF010100T6_09;//指定直屬主管
	private String EHF010100T6_10;//第一順位職務代理人
	private String EHF010100T6_11;//第二順位職務代理人
	private String EHF010100T6_12;//第三順位職務代理人
	private String EHF010100T6_13;//是否請假
	private String EHF010100T6_14;//請假開始時間
	private String EHF010100T6_15;//請假結束時間
	private String EHF000300T0_03;//職務名稱(使用者自訂)
	private String EHF000300T0_02;//職務編號(使用者自訂)
	private String EHF010100T6_06;//職務名稱基本資料_系統編碼(系統給定)
	
	private String EHF000200T0_02;
	private String EHF000200T0_03;
	
	//員工考核獎懲資料
	
	//員工班別資料
	private int EHF010100T8_01;	//員工班別序號
	private String EHF010100T8_02;	//部門組織(代號)
	private String EHF010100T8_03;	//員工工號
	private String EHF010100T8_04;	//班別序號
	private String EHF010100T8_04_TXT;
	private String EHF010100T8_04_KEY;
	private String EHF010100T8_04_HIDE;
	private String EHF010100T8_05;	//備註
	private String EHF010100T8_06;	//公司代碼
	
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
	private String EHF030200T1_04_02;
	
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
	
	private FormFile up_file1;
	
	private String tabsutil_EMP;
	private String tabsutil_ATT;
	private String tabsutil_SAL;
	private String tabsutil_INS;
	private String tabsutil_EXP;
	private String tabsutil_VA;
	private String tabsutil_DA;
	//員工年度休假資料
	private  String  EHF010300T0_01;
	private  String  EHF010300T0_02;
	private  String  EHF010300T0_03;
	private  String  EHF010300T0_04;
	private  String  EHF010300T0_04_TXT;
	private  String  EHF010300T0_05;
	private  String  EHF010300T0_05_TXT;
	private  String  EHF010300T0_06;
	private  String  EHF010300T0_06_TXT;
	private  String  EHF010300T0_07;
	private  String  EHF010300T0_07_DAY;
	private  String  EHF010300T0_07_HOUR;
	private  String  EHF010300T0_08;
	private  String  EHF010300T0_08_DAY;
	private  String  EHF010300T0_08_HOUR;
	private  String  EHF010300T0_081;  //已休時數
	private  String  EHF010300T0_09;
	private  String  EHF010300T0_10;
	private  String  EHF010300T0_11;
	private  String  EHF010300T0_12;
	
	private  String  EHF010300T1_07_DAY;
	private  String  EHF010300T1_07_HOUR;
	private  String  EHF010300T1_08_DAY;
	private  String  EHF010300T1_08_HOUR;
	private  String  EHF010300T1_09;
	private  String  EHF010300T1_10;
	
	private List EHF010300C=new ArrayList();

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
			
			//新增感應卡明細
			if (request.getAttribute("action").equals("addEHF020403T1")) {
				
				addEHF020403T1_validate(l_actionErrors, request, conn);
			}
			
			//新增津貼明細
			if (request.getAttribute("action").equals("addEHF010101T0")) {
				
				addEHF010101T0_validate(l_actionErrors, request, conn);
			}
			
			//新增薪資項目
			if (request.getAttribute("action").equals("addEHF030102T0")) {
				
				addEHF030102T0_validate(l_actionErrors, request, conn);
			}
			
			//新增加保眷屬
			if (request.getAttribute("action").equals("addEHF030300T1")) {
				
				addEHF030300T1_validate(l_actionErrors, request, conn);
			}
			
			//新增加員工年度休假設定
			if (request.getAttribute("action").equals("addEHF010300T0")) {
				
				addEHF010300T0_validate(l_actionErrors, request, conn);
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
				
				//saveDataATT_validate(l_actionErrors, request, conn);
			}
			
			if (request.getAttribute("action").equals("saveDataSAL")) {
				
				//saveDataSAL_validate(l_actionErrors, request, conn);
			}

			if (request.getAttribute("action").equals("saveDataINS")) {
				
				//saveDataINS_validate(l_actionErrors, request, conn);
			}
			
			if (request.getAttribute("action").equals("saveDataEMP")) {
				
				saveDataEMP_validate(l_actionErrors, request, conn);
			}
			
			if (request.getAttribute("action").equals("saveDataEXP")) {
				
				//saveDataEXP_validate(l_actionErrors, request, conn);
			}
			
			if (request.getAttribute("action").equals("saveDataVA")) {
				
				//saveDataVA_validate(l_actionErrors, request, conn);
			}
			//新增員工學歷明細
			if (request.getAttribute("action").equals("addEHF010100T3")) {
				
				addEHF010100T3_validate(l_actionErrors, request, conn);
			}
			
			//新增證照資料明細
			if (request.getAttribute("action").equals("addEHF010100T4")) {
				
				addEHF010100T4_validate(l_actionErrors, request, conn);
			}
			
		}
		
		if (!l_actionErrors.isEmpty()) {
			
			tabsutil_EMP = "yes";
			
		}
		
		return l_actionErrors;
	}
	
	private void addEHF010100T4_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		BA_Vaildate ve=BA_Vaildate.getInstance();
		
		ve.isEmpty(errors, EHF010100T4_03, "EHF010100T4_03", "不可空白");
		
	}

	private void addEHF010100T3_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		BA_Vaildate ve=BA_Vaildate.getInstance();
		
		ve.isEmpty(errors, EHF010100T3_03, "EHF010100T3_03", "不可空白");
		ve.isEmpty(errors, EHF010100T3_04, "EHF010100T3_04", "不可空白");
		ve.isEmpty(errors, EHF010100T3_05, "EHF010100T3_05", "不可空白");
		
	}

	private void saveDataVA_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		float EHF010300T0_07_HOUR_number=0;
		float EHF010300T0_08_HOUR_number=0;
		try{
			for (int i = 0; i < EHF010300C.size(); i++) {
				
				EHF010100M0F Form_1 = (EHF010100M0F) EHF010300C.get(i);
				
				if(Form_1.getEHF010300T1_07_HOUR()==null){
					EHF010300T0_07_HOUR_number =0;
				}else{
					EHF010300T0_07_HOUR_number=Float.parseFloat(Form_1.getEHF010300T1_07_HOUR());
				}
				
				if(Form_1.getEHF010300T1_08_HOUR()==null){
					EHF010300T0_08_HOUR_number =0;
				}else{
					EHF010300T0_08_HOUR_number=Float.parseFloat(Form_1.getEHF010300T1_08_HOUR());
				}
				
				//取得一天工作時數
				float work_hours = Float.parseFloat(tools.getSysParam(conn, (String)request.getAttribute("compid"), "WORK_HOURS"));
				float ehf010300t0_07 = ( (Float.parseFloat(Form_1.getEHF010300T1_07_DAY()) * work_hours) + EHF010300T0_07_HOUR_number );
				float ehf010300t0_08 = ( (Float.parseFloat(Form_1.getEHF010300T1_08_DAY()) * work_hours) + EHF010300T0_08_HOUR_number );
				
				if( ehf010300t0_08 > ehf010300t0_07 ){
					
					errors.add("EHF010300T0_07_DAY",new ActionMessage(""));
					errors.add("EHF010300T0_07_HOUR",new ActionMessage(""));
					request.setAttribute("ErrMSG",
							(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
							"剩餘時數不可大於休假時數!! ");
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); 
				//進行轉換 
				try {
				
					Date date01 = sdf.parse(Form_1.getEHF010300T1_09());
//					System.out.println(date01);
					Date date02 = sdf.parse(Form_1.getEHF010300T1_10()); 
//					System.out.println(date02);
					
					if(date01.compareTo(date02) > 0){
						errors.add("EHF010300T0_09",new ActionMessage(""));
						request.setAttribute("ErrMSG",
								(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
								"起始日期不可小於結束日期!! ");
					}
				
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private void addEHF010300T0_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		BA_Vaildate ve=BA_Vaildate.getInstance();
		
		ve.isEmpty(errors, EHF010300T0_06, "EHF010300T0_06", "不可空白");
		ve.isEmpty(errors, EHF010300T0_07_DAY, "EHF010300T0_07_DAY", "不可空白");
		//ve.isEmpty(l_actionErrors, EHF010300T0_07_HOUR, "EHF010300T0_07_HOUR", "不可空白");
		ve.isEmpty(errors, EHF010300T0_09, "EHF010300T0_09", "不可空白");
		ve.isEmpty(errors, EHF010300T0_10, "EHF010300T0_10", "不可空白");
		
		if(errors.isEmpty()){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); 
			//進行轉換 
			try {
			
				Date date01 = sdf.parse(EHF010300T0_09);
//				System.out.println(date01);
				Date date02 = sdf.parse(EHF010300T0_10); 
//				System.out.println(date02);
				
				if(date01.compareTo(date02) > 0){
					errors.add("EHF010300T0_09",new ActionMessage(""));
					request.setAttribute("ErrMSG",
							(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
							"起始日期不可小於結束日期!! ");
				}
			
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			try{
				Statement stmt = conn.createStatement();
				String sql = "";
				
				sql += " SELECT * FROM EHF010300T0 " +
					   " WHERE 1=1 " +
					   " AND EHF010300T0_12 = '"+request.getAttribute("compid")+"' " +  //公司代碼
					   " AND EHF010300T0_02 = '"+EHF010300T0_02+"' " +
					   " AND EHF010300T0_05 = '"+EHF010100T0_01+"' " +	//員工工號
					   " AND EHF010300T0_06 = '"+EHF010300T0_06+"' ";	//假別代號
				
				ResultSet rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					errors.add("EHF010300T0_06",new ActionMessage("該員工假別資料設定重複"));
				}
				
				rs.close();
				stmt.close();
				
			}catch (SQLException e) {
				System.out.println("EHF010300M0F.addData_validate()" + e);
			}
		}
		
	}
	//新增加保眷屬
	private void addEHF030300T1_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢查是否有未出帳薪資
		this.chk_validate(errors, request, conn, "新增加保眷屬");
		
		if(errors.isEmpty()){
			BA_Vaildate ve=BA_Vaildate.getInstance();
			ve.isEmpty(errors, EHF030300T1_02, "EHF030300T1_02", "不可空白");
			ve.isEmpty(errors, EHF030300T1_03, "EHF030300T1_03", "不可空白");
			ve.isEmpty(errors, EHF030300T1_04, "EHF030300T1_04", "不可空白");
			ve.isEmpty(errors, EHF030300T1_05, "EHF030300T1_05", "不可空白");
			ve.isEmpty(errors, EHF030300T1_06, "EHF030300T1_06", "不可空白");
		}
		
	}
	//新增薪資項目
	private void addEHF030102T0_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢查是否有未出帳薪資
		this.chk_validate(errors, request, conn, "新增薪資項目明細");
		
		
		if(errors.isEmpty()){
			BA_Vaildate ve=BA_Vaildate.getInstance();
			ve.isEmpty(errors, EHF030200T1_02, "EHF030200T1_02", "不可空白");
			
			try{
				Statement stmt = conn.createStatement();
				String sql = "";
			
				sql += " SELECT * FROM EHF030200T1 " +
				   	   " WHERE 1=1 " +
				   	   " AND EHF030200T1_01 = '"+EHF010100T0_01+"' " +	//員工工號
				   	   " AND EHF030200T1_02 = '"+EHF030200T1_02+"' " +	//薪資項目金額序號
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
				System.out.println("EHF010100M0F.addEHF030102T0_validate()" + e);
			}
		}
		
	}
	//新增津貼明細
	private void addEHF010101T0_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢查是否有未出帳薪資
		this.chk_validate(errors, request, conn, "新增津貼項目明細");
		
		if (errors.isEmpty()) {
			BA_Vaildate ve=BA_Vaildate.getInstance();
		
			ve.isEmpty(errors, EHF030200T1_02_01, "EHF030200T1_02_01", "不可空白");
			ve.isEmpty(errors, EHF030200T1_04_01, "EHF030200T1_04_01", "不可空白");
			
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = null;
				String sql = "";

				sql += " SELECT * FROM EHF030200T1 " +
			   	   " WHERE 1=1 " +
			   	   " AND EHF030200T1_01 = '"+EHF010100T0_01+"' " +	//員工工號
			   	   " AND EHF030200T1_02 = '"+EHF030200T1_02_01+"' " +	//薪資項目金額序號
			   	   " AND EHF030200T1_05 = '"+EHF030200T1_04_02+"'"+	//使用班別代號
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
				
				rs.close();
				stmt.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	//新增感應卡明細
	private void addEHF020403T1_validate(ActionErrors errors,HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		BA_TOOLS ems_tools = BA_TOOLS.getInstance();
		
		if((!"".equals(getEHF020403T1_05())|| getEHF020403T1_05()==null) && !GenericValidator.maxLength(getEHF020403T1_05(), 25)){//
			errors.add("EHF020403T1_05", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"感應卡備註字數不得超過25個字!!");
		}
		//檢查不可為空白
		BA_Vaildate ve=BA_Vaildate.getInstance();
		
		ve.isEmpty(errors, EHF020403T1_02, "EHF020403T1_02", "不可空白");  //感應卡號不可空白
		ve.isEmpty(errors, EHF020403T1_04_START, "EHF020403T1_04_START", "不可空白");  //使用期間(起)不可空白
		if(!"".equals(EHF020403T1_04_END)){
			Calendar cal_START 	= ems_tools.covertStringToCalendar(EHF020403T1_04_START+" "+EHF020403T1_04_START_HH+":"+EHF020403T1_04_START_MM+":00", "yyyy/MM/dd HH:mm:ss");
			Calendar cal_END 	= ems_tools.covertStringToCalendar(EHF020403T1_04_END  +" "+EHF020403T1_04_END_HH+":"+EHF020403T1_04_END_MM+":00"    , "yyyy/MM/dd HH:mm:ss");
			if(cal_START.compareTo(cal_END)>0){
				errors.add("EHF020403T1_04_START",new ActionMessage("時間錯誤，請確認!!"));
				errors.add("EHF020403T1_04_END",new ActionMessage("時間錯誤，請確認!!"));
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						"使用期間錯誤,請確認後再建立資料!! ");
			}
		}
		if (errors.isEmpty()) {
			//不足補0
			String str=ems_tools.addZeroForNum(EHF020403T1_02, 10);
			this.setEHF020403T1_02(str);
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
					if(EHF010100T0_01.equals(rs.getString("EHF020403T1_01"))){
						//上一位使用者與目前要新增的人為同一個人
						errors.add("EHF020403T1_02",new ActionMessage("感應卡資料已存在,請修改時間(迄)即可"));
						request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":
							request.getAttribute("ErrMSG")+"<br>")+EHF020403T1_02+"感應卡資料已存在,請修改時間(迄)即可");
					}else{
						//判斷新感應卡的起與上一張感應卡的迄  時間是否有交錯
						
						//新感應卡的時間(起)
						Calendar NEW_cal_START 	= ems_tools.covertStringToCalendar(EHF020403T1_04_START+" "+EHF020403T1_04_START_HH+":"+EHF020403T1_04_START_MM+":00", "yyyy/MM/dd HH:mm:ss");
						//上一張感應卡的(迄)
						Calendar Old_cal_END 	= ems_tools.covertStringToCalendar(rs.getString("EHF020403T1_04_END"), "yyyy/MM/dd HH:mm:ss");
							
						if(NEW_cal_START.compareTo(Old_cal_END)<0){
							errors.add("EHF020403T1_02",new ActionMessage("感應卡使用中"));
							request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":
							request.getAttribute("ErrMSG")+"<br>")+EHF020403T1_02+"感應卡使用中,請確認!!");
							
						}
					}
				}
			
				rs.close();
				stmt.close();
			}catch (SQLException e) {
				System.out.println("EHF020403M1F.addData_validate()" + e);
			}
		}
		
		
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
		
		ve.isEmpty(errors, EHF010100T0_05, "EHF010100T0_05", "不可空白");	//姓名(中)
		ve.isEmpty(errors, EHF010100T0_07, "EHF010100T0_07", "不可空白");	//員工類別
		//ve.isEmpty(errors, EHF010100T0_13, "EHF010100T0_13", "不可空白");	//戶籍地址
		ve.isEmpty(errors, EHF010100T0_02, "EHF010100T0_02", "不可空白");	//員工工號
		
		
		if(!"".equals(getEHF010100T0_I())&&getEHF010100T0_I()!=null){
			ve.isTWID(errors, EHF010100T0_I, "EHF010100T0_I", "請輸入正確的身分證字號");
		}
		
		if( ("".equals(getEHF010100T0_I())||getEHF010100T0_I()==null) && 
			("".equals(getEHF010100T0_03())||getEHF010100T0_03()==null) && 
			("".equals(getEHF010100T0_04())||getEHF010100T0_04()==null) ){
			errors.add("EHF010100T0_I", new ActionMessage(""));
			errors.add("EHF010100T0_03", new ActionMessage(""));
			errors.add("EHF010100T0_04", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"身分證字號、護照號碼、居留證號碼請擇一填寫!!");
		}
		
//		if( ("".equals(getEHF010100T0_14()) || getEHF010100T0_14()==null) && ("".equals(getEHF010100T0_16()) || getEHF010100T0_16()==null)){
//			errors.add("EHF010100T0_14", new ActionMessage(""));
//			errors.add("EHF010100T0_16", new ActionMessage(""));
//			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"聯絡電話或手機號碼請擇一填寫!!");			
//		}
		
		
		
		
		try{			
			if("1".equals(getEHF010100T1_02())){
				//現職日期
				ve.isEmpty(errors, EHF010100T1_0301, "EHF010100T1_0301", "不可空白");	
				
				if("".equals(EHF010100T1_0301) && EHF010100T1_0301 != null){
					request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"職務狀況與日期不符或日期未填寫，請確認！");
				}
				
			}else if("2".equals(getEHF010100T1_02())){
				//離職生效日日期
				ve.isEmpty(errors, EHF010100T1_0302, "EHF010100T1_0302", "不可空白");	

				if("".equals(EHF010100T1_0302) && EHF010100T1_0302 != null){
					request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"職務狀況與日期不符或日期未填寫，請確認！");
				}
				
//				String sql=" SELECT EHF010100T1_02 FROM EHF010100T1 " +
//				   		   "  WHERE EHF010100T1_01='"+getEHF010100T0_01()+"' " +
//				   		   "    AND EHF010100T1_04='0' " +
//				   		   "    AND HR_CompanySysNo='"+request.getAttribute("compid").toString()+"' ";
//		
//				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
//				ResultSet rs = stmt.executeQuery(sql);
//				if(rs.next() && !rs.getString("EHF010100T1_02").equals("2")){//EHF010100T1未失效的資料若不是離職
//					//代表是要從未離職變更為離職
//					errors.add("EHF010100T1_02", new ActionMessage(""));
//					request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"變更職務狀態為離職請透過離職單作業！");
//				}
//				rs.close();
//				stmt.close();
//				
			}else if("3".equals(getEHF010100T1_02())){
				//復職日期
				ve.isEmpty(errors, EHF010100T1_0303, "EHF010100T1_0303", "不可空白");	

				if("".equals(EHF010100T1_0303) && EHF010100T1_0303 != null){
					request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"職務狀況與日期不符或日期未填寫，請確認！");
				}
				
			}else if("4".equals(getEHF010100T1_02())){
				//留職停薪日期
				ve.isEmpty(errors, EHF010100T1_0304, "EHF010100T1_0304", "不可空白");	

				if("".equals(EHF010100T1_0304) && EHF010100T1_0304 != null){
					request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"職務狀況與日期不符或日期未填寫，請確認！");
				}
				
			}
				
		}catch(Exception e){
			System.out.println("檢核職務狀況日期欄位是否空白時錯誤："+e);
		}
		
		
		try{
			HR_TOOLS hr_tools = new HR_TOOLS();
			BA_TOOLS tools = BA_TOOLS.getInstance();
			Map empInfMap = hr_tools.getEmpInfMapByEmpId("", getEHF010100T0_01(), request.getAttribute("compid").toString());
			if(!((String)empInfMap.get("SEARCHSTATUS")).equals(getEHF010100T1_02())){
				//若職務狀態有變更，判斷日期不可小餘前個日期
//				System.out.println("getEHF010100T1_03:"+getEHF010100T1_03());
				Calendar cal_EHF010100T1_03=null;
				if(getEHF010100T1_0301()!=null && !"".equals(getEHF010100T1_0301()) && "1".equals(getEHF010100T1_02())){
					cal_EHF010100T1_03=tools.covertStringToCalendar(getEHF010100T1_0301().toString());
				}else if(getEHF010100T1_0302()!=null && !"".equals(getEHF010100T1_0302())&& "2".equals(getEHF010100T1_02())){
					cal_EHF010100T1_03=tools.covertStringToCalendar(getEHF010100T1_0302().toString());
				}else if(getEHF010100T1_0303()!=null && !"".equals(getEHF010100T1_0303())&& "3".equals(getEHF010100T1_02())){
					cal_EHF010100T1_03=tools.covertStringToCalendar(getEHF010100T1_0303().toString());
				}else if(getEHF010100T1_0304()!=null && !"".equals(getEHF010100T1_0304())&& "4".equals(getEHF010100T1_02())){
					cal_EHF010100T1_03=tools.covertStringToCalendar(getEHF010100T1_0304().toString());
				}
				if(tools.covertStringToCalendar((String)empInfMap.get("CHECKDATE")).after(cal_EHF010100T1_03)){
					//若原日期大於變更日期，代表邏輯錯誤  例：原日期2017/09/30  欲更改日期2017/09/29
					errors.add("EHF010100T1_02", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"職務狀況日期邏輯錯誤");
				}
			}
			hr_tools.close();
		}catch(Exception e){
			System.out.println("檢核職務狀況日期變更時錯誤："+e);
		}
		try{ //OK hank
			HR_TOOLS hr_tools = new HR_TOOLS();
			Map empInfMap = hr_tools.getEmpInfMapByEmpId("", getEHF010100T0_01(), request.getAttribute("compid").toString());
			
			if(((String)empInfMap.get("SEARCHSTATUS")).equals("1")){
				//代表原職務狀況為到職
				if(/*"1".equals(getEHF010100T1_02()) || */"3".equals(getEHF010100T1_02()) ){
					errors.add("EHF010100T1_02", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"職務狀況錯誤1");
				}
			}else if(((String)empInfMap.get("SEARCHSTATUS")).equals("2")){
				//代表原職務狀況為離職
				if(/*"1".equals(getEHF010100T1_02()) || "2".equals(getEHF010100T1_02()) ||*/ "3".equals(getEHF010100T1_02()) || "4".equals(getEHF010100T1_02()) ){
					errors.add("EHF010100T1_02", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"職務狀況錯誤2");
				}
			}else if(((String)empInfMap.get("SEARCHSTATUS")).equals("3")){
				//代表原職務狀況為復職
				if("1".equals(getEHF010100T1_02()) /*|| "3".equals(getEHF010100T1_02())*/ ){
					errors.add("EHF010100T1_02", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"職務狀況錯誤3");
				}
			}else if(((String)empInfMap.get("SEARCHSTATUS")).equals("4")){
				//代表原職務狀況為留職停薪
				if("1".equals(getEHF010100T1_02()) || "2".equals(getEHF010100T1_02()) /*|| "4".equals(getEHF010100T1_02())*/ ){
					errors.add("EHF010100T1_02", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"職務狀況錯誤4");
				}
			}
			hr_tools.close();
		}catch(Exception e){
			System.out.println("檢核職務狀況變更時錯誤："+e);
		}
		
		
		
		
		
	}

	private void saveDataINS_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢查是否有未出帳薪資
		this.chk_validate(errors, request, conn, "修改勞健保投保設定");
		
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
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		this.chk_validate(errors, request, conn, "修改員工薪資基本資料");
		
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(errors, EHF030200T0_08, "EHF030200T0_08", "不可空白");
		
		if(tools.StringToBoolean(EHF030200T0_08)){
			ve.isEmpty(errors, EHF030200T0_05, "EHF030200T0_05", "不可空白");
			ve.isEmpty(errors, EHF030200T0_07, "EHF030200T0_07", "不可空白");
			ve.isNumEmpty(errors, EHF030200T0_04+"", "EHF030200T0_04", "不可空白", true);
			ve.isEmpty(errors, EHF030200T0_18, "EHF030200T0_18", "不可空白");
			ve.isEmpty(errors, EHF030200T0_20, "EHF030200T0_20", "不可空白");
			ve.isEmpty(errors, EHF030200T0_21, "EHF030200T0_21", "不可空白");
		}
		
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
		
		ve.isEmpty(errors, EHF010100T8_04, "EHF010100T8_04", "不可空白");	//班別序號
		ve.isEmpty(errors, EHF020403T0_05, "EHF020403T0_05", "不可空白");	//是否計算考勤
		
		if((!"".equals(getEHF020403T1_05())|| getEHF020403T1_05()==null) && !GenericValidator.maxLength(getEHF020403T1_05(), 25)){//
			errors.add("EHF020403T1_05", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"感應卡備註字數不得超過25個字!!");
		}
		
		try{						
			for (int i = 0; i < EHF020403C.size(); i++) {
				EHF010100M0F Form = (EHF010100M0F) EHF020403C.get(i);
				
				
				Statement stmt = conn.createStatement();
				String sql = "";
			
				sql += " SELECT * FROM EHF020403T1 " +
				   	   " WHERE 1=1 " +
				   	   " AND '"+Form.getEHF020403T1_04_END()+" "+Form.getEHF020403T1_04_END_HH()+":"+Form.getEHF020403T1_04_END_MM()+":00"+"' BETWEEN DATE_FORMAT(EHF020403T1_04_START, '%Y/%m/%d %H%i%s') AND DATE_FORMAT(EHF020403T1_04_END, '%Y/%m/%d %H%i%s')"+
				   	   " AND EHF020403T1_01 <> '"+EHF010100T0_01+"' " +  //員工工號
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
			System.out.println("EHF010100M0F.saveDataATT_validate()" + e);
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
		
		ve.isEmpty(errors, EHF010100T5_03, "EHF010100T5_03", "不可空白");
		ve.isNumEmpty(errors, EHF010100T5_04+"", "EHF010100T5_04", "不可空白", false);
		ve.isEmpty(errors, EHF010100T5_05, "EHF010100T5_05", "不可空白");
		ve.isEmpty(errors, EHF010100T5_06, "EHF010100T5_06", "不可空白");
		
		ve.isEmpty(errors, EHF010100T3_03, "EHF010100T3_03", "不可空白");//學歷
		ve.isEmpty(errors, EHF010100T3_04, "EHF010100T3_04", "不可空白");//學校名稱
		ve.isEmpty(errors, EHF010100T3_05, "EHF010100T3_05", "不可空白");//學校科系
		ve.isEmpty(errors, EHF010100T3_06, "EHF010100T3_06", "不可空白");//在校期間(起)
		ve.isEmpty(errors, EHF010100T3_07, "EHF010100T3_07", "不可空白");//在校期間(迄)
		ve.isEmpty(errors, EHF010100T3_08, "EHF010100T3_08", "不可空白");//畢(肄)業
		
		ve.isEmpty(errors, EHF010100T2_03, "EHF010100T2_03", "不可空白");//公司名稱
		ve.isEmpty(errors, EHF010100T2_04, "EHF010100T2_04", "不可空白");//公司產業別
		ve.isEmpty(errors, EHF010100T2_05, "EHF010100T2_05", "不可空白");//公司部門
		ve.isEmpty(errors, EHF010100T2_06, "EHF010100T2_06", "不可空白");//公司職務
		ve.isEmpty(errors, EHF010100T2_08, "EHF010100T2_08", "不可空白");//工作日期(起)
		ve.isEmpty(errors, EHF010100T2_09, "EHF010100T2_09", "不可空白");//工作日期(迄)
		
		ve.isEmpty(errors, EHF010100T4_03, "EHF010100T4_03", "不可空白");//證照名稱
		ve.isEmpty(errors, EHF010100T4_05, "EHF010100T4_05", "不可空白");//有效期間(起)
		ve.isEmpty(errors, EHF010100T4_06, "EHF010100T4_06", "不可空白");//有效期間(迄)
		
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
				   	   " AND EHF030200T1_01 = '"+EHF010100T0_01+"' " +
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
				if(EHF010100T0_01.equals(rs.getString("EHF020403T1_01"))){
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
			System.out.println("EHF010100M0F.addData_validate()" + e);
		}
		
	}

	private void addData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(errors, EHF010100T0_02, "EHF010100T0_02", "不可空白");	//員工工號
		ve.isEmpty(errors, EHF010100T0_05, "EHF010100T0_05", "不可空白");	//姓名(中)
		ve.isEmpty(errors, EHF010100T0_07, "EHF010100T0_07", "不可空白");	//員工類別
		//ve.isEmpty(errors, EHF010100T0_13, "EHF010100T0_13", "不可空白");	//戶籍地址
		ve.isEmpty(errors, EHF000200T0_02, "EHF000200T0_02", "不可空白");	//所屬部門
		ve.isEmpty(errors, EHF010100T6_03, "EHF010100T6_03", "部門歸屬區間(起)不可空白");	//部門歸屬區間
		//ve.isEmpty(errors, EHF010100T6_04, "EHF010100T6_03", "部門歸屬區間(迄)不可空白");	//部門歸屬區間
		ve.isEmpty(errors, EHF010100T6_07, "EHF010100T6_07", "不可空白"+"<br>"+"<br>");//主管職務
		//ve.isEmpty(errors, EHF010100T8_04_KEY, "EHF010100T8_04_KEY", "不可空白");	//班別
		ve.isEmpty(errors, EHF000300T0_02, "EHF000300T0_02", "<br>"+"<br>"+"不可空白");	//職務名稱
		//ve.isEmpty(errors, EHF020403T0_05, "EHF020403T0_05", "<br>"+"<br>"+"不可空白");	//是否計算考勤
		
		//ve.isEmpty(errors, EHF010100T8_04, "EHF010100T8_04", "<br>"+"<br>"+"不可空白");	//班別序號
		
		ve.isEmpty(errors, EHF010100T1_02, "EHF010100T1_02", "不可空白");	//職務狀況
		
		if("1".equals(EHF010100T1_02)){
			if("".equals(EHF010100T1_0301)){
				errors.add("EHF010100T1_02",new ActionMessage("日期不可空白"));
				ve.isEmpty(errors, EHF010100T1_0301, "EHF010100T1_0301", "不可空白");	//職務狀況
			}
		}else if("2".equals(EHF010100T1_02)){
			if("".equals(EHF010100T1_0301)){
				errors.add("EHF010100T1_02",new ActionMessage("日期不可空白"));
				ve.isEmpty(errors, EHF010100T1_0302, "EHF010100T1_0302", "不可空白");	//職務狀況
			}
		}else if("3".equals(EHF010100T1_02)){
			if("".equals(EHF010100T1_0301)){
				errors.add("EHF010100T1_02",new ActionMessage("日期不可空白"));
				ve.isEmpty(errors, EHF010100T1_0303, "EHF010100T1_0303", "不可空白");	//職務狀況
			}
		}else if("4".equals(EHF010100T1_02)){
			if("".equals(EHF010100T1_0301)){
				errors.add("EHF010100T1_02",new ActionMessage("日期不可空白"));
				ve.isEmpty(errors, EHF010100T1_0304, "EHF010100T1_0304", "不可空白");	//職務狀況
			}
		}
		
		
		
		if(!"".equals(getEHF010100T0_I())&&getEHF010100T0_I()!=null){
			ve.isTWID(errors, EHF010100T0_I, "EHF010100T0_I", "請輸入正確的身分證字號");
		}
		
		if( ("".equals(getEHF010100T0_I())||getEHF010100T0_I()==null) && 
			("".equals(getEHF010100T0_03())||getEHF010100T0_03()==null) && 
			("".equals(getEHF010100T0_04())||getEHF010100T0_04()==null) ){
			errors.add("EHF010100T0_I", new ActionMessage(""));
			errors.add("EHF010100T0_03", new ActionMessage(""));
			errors.add("EHF010100T0_04", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"身分證字號、護照號碼、居留證號碼請擇一填寫!!");
		}
			
//		if( ("".equals(getEHF010100T0_14()) || getEHF010100T0_14()==null) && ("".equals(getEHF010100T0_16()) || getEHF010100T0_16()==null)){
//			errors.add("EHF010100T0_14", new ActionMessage(""));
//			errors.add("EHF010100T0_16", new ActionMessage(""));
//			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"聯絡電話或手機號碼請擇一填寫!!");			
//		}	
		
		if(request.getAttribute("ERR_MSG")!=null){
			request.setAttribute("button_init", "init");
		}
		
		if("1".equals(EHF020403T0_05)){
			ve.isEmpty(errors, EHF010100T8_04, "EHF010100T8_04", "不可空白");	//職務
		}
		
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("utf-8");
			EHF020403C = ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new EHF010100M0F();
				            }
			 });
			EHF010300C = ListUtils.lazyList(new ArrayList(),new Factory() {
	            			public Object create() {
	            				return new EHF010100M0F();
	            			}
			});
		} catch (Exception e) {
		}

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
				" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2, EHF020900T0_03" +
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

	public String getEHF010100T0_01() {
		return EHF010100T0_01;
	}

	public void setEHF010100T0_01(String eHF010100T0_01) {
		EHF010100T0_01 = eHF010100T0_01;
	}

	public String getEHF010100T0_02() {
		return EHF010100T0_02;
	}

	public void setEHF010100T0_02(String eHF010100T0_02) {
		EHF010100T0_02 = eHF010100T0_02;
	}

	public String getEHF010100T0_I() {
		return EHF010100T0_I;
	}

	public void setEHF010100T0_I(String eHF010100T0I) {
		EHF010100T0_I = eHF010100T0I;
	}

	public String getEHF010100T0_03() {
		return EHF010100T0_03;
	}

	public void setEHF010100T0_03(String eHF010100T0_03) {
		EHF010100T0_03 = eHF010100T0_03;
	}

	public String getEHF010100T0_04() {
		return EHF010100T0_04;
	}

	public void setEHF010100T0_04(String eHF010100T0_04) {
		EHF010100T0_04 = eHF010100T0_04;
	}

	public String getEHF010100T0_05() {
		return EHF010100T0_05;
	}

	public void setEHF010100T0_05(String eHF010100T0_05) {
		EHF010100T0_05 = eHF010100T0_05;
	}

	public String getEHF010100T0_06() {
		return EHF010100T0_06;
	}

	public void setEHF010100T0_06(String eHF010100T0_06) {
		EHF010100T0_06 = eHF010100T0_06;
	}

	public String getEHF010100T0_07() {
		return EHF010100T0_07;
	}

	public void setEHF010100T0_07(String eHF010100T0_07) {
		EHF010100T0_07 = eHF010100T0_07;
	}

	public String getEHF010100T0_08() {
		return EHF010100T0_08;
	}

	public void setEHF010100T0_08(String eHF010100T0_08) {
		EHF010100T0_08 = eHF010100T0_08;
	}

	public String getEHF010100T0_09() {
		return EHF010100T0_09;
	}

	public void setEHF010100T0_09(String eHF010100T0_09) {
		EHF010100T0_09 = eHF010100T0_09;
	}

	public String getEHF010100T0_10() {
		return EHF010100T0_10;
	}

	public void setEHF010100T0_10(String eHF010100T0_10) {
		EHF010100T0_10 = eHF010100T0_10;
	}

	public String getEHF010100T0_11() {
		return EHF010100T0_11;
	}

	public void setEHF010100T0_11(String eHF010100T0_11) {
		EHF010100T0_11 = eHF010100T0_11;
	}

	public String getEHF010100T0_12() {
		return EHF010100T0_12;
	}

	public void setEHF010100T0_12(String eHF010100T0_12) {
		EHF010100T0_12 = eHF010100T0_12;
	}

	public String getEHF010100T0_1201() {
		return EHF010100T0_1201;
	}

	public void setEHF010100T0_1201(String eHF010100T0_1201) {
		EHF010100T0_1201 = eHF010100T0_1201;
	}

	public String getEHF010100T0_1202() {
		return EHF010100T0_1202;
	}

	public void setEHF010100T0_1202(String eHF010100T0_1202) {
		EHF010100T0_1202 = eHF010100T0_1202;
	}

	public String getEHF010100T0_13() {
		return EHF010100T0_13;
	}

	public void setEHF010100T0_13(String eHF010100T0_13) {
		EHF010100T0_13 = eHF010100T0_13;
	}

	public String getEHF010100T0_14() {
		return EHF010100T0_14;
	}

	public void setEHF010100T0_14(String eHF010100T0_14) {
		EHF010100T0_14 = eHF010100T0_14;
	}

	public String getEHF010100T0_1401() {
		return EHF010100T0_1401;
	}

	public void setEHF010100T0_1401(String eHF010100T0_1401) {
		EHF010100T0_1401 = eHF010100T0_1401;
	}

	public String getEHF010100T0_1402() {
		return EHF010100T0_1402;
	}

	public void setEHF010100T0_1402(String eHF010100T0_1402) {
		EHF010100T0_1402 = eHF010100T0_1402;
	}

	public String getEHF010100T0_15() {
		return EHF010100T0_15;
	}

	public void setEHF010100T0_15(String eHF010100T0_15) {
		EHF010100T0_15 = eHF010100T0_15;
	}

	public String getEHF010100T0_16() {
		return EHF010100T0_16;
	}

	public void setEHF010100T0_16(String eHF010100T0_16) {
		EHF010100T0_16 = eHF010100T0_16;
	}

	public String getEHF010100T0_17() {
		return EHF010100T0_17;
	}

	public void setEHF010100T0_17(String eHF010100T0_17) {
		EHF010100T0_17 = eHF010100T0_17;
	}

	public String getEHF010100T0_18() {
		return EHF010100T0_18;
	}

	public void setEHF010100T0_18(String eHF010100T0_18) {
		EHF010100T0_18 = eHF010100T0_18;
	}

	public String getEHF010100T0_19() {
		return EHF010100T0_19;
	}

	public void setEHF010100T0_19(String eHF010100T0_19) {
		EHF010100T0_19 = eHF010100T0_19;
	}

	public String getEHF010100T0_1901() {
		return EHF010100T0_1901;
	}

	public void setEHF010100T0_1901(String eHF010100T0_1901) {
		EHF010100T0_1901 = eHF010100T0_1901;
	}

	public String getEHF010100T0_1902() {
		return EHF010100T0_1902;
	}

	public void setEHF010100T0_1902(String eHF010100T0_1902) {
		EHF010100T0_1902 = eHF010100T0_1902;
	}

	public String getEHF010100T0_20() {
		return EHF010100T0_20;
	}

	public void setEHF010100T0_20(String eHF010100T0_20) {
		EHF010100T0_20 = eHF010100T0_20;
	}

	public boolean isEHF010100T0_21() {
		return EHF010100T0_21;
	}

	public void setEHF010100T0_21(boolean eHF010100T0_21) {
		EHF010100T0_21 = eHF010100T0_21;
	}

	public boolean isEHF010100T0_22() {
		return EHF010100T0_22;
	}

	public void setEHF010100T0_22(boolean eHF010100T0_22) {
		EHF010100T0_22 = eHF010100T0_22;
	}

	public String getEHF010100T0_23() {
		return EHF010100T0_23;
	}

	public void setEHF010100T0_23(String eHF010100T0_23) {
		EHF010100T0_23 = eHF010100T0_23;
	}

	public String getEHF010100T0_24() {
		return EHF010100T0_24;
	}

	public void setEHF010100T0_24(String eHF010100T0_24) {
		EHF010100T0_24 = eHF010100T0_24;
	}

	public String getEHF010100T0_25() {
		return EHF010100T0_25;
	}

	public void setEHF010100T0_25(String eHF010100T0_25) {
		EHF010100T0_25 = eHF010100T0_25;
	}

	public String getEHF010100T0_26() {
		return EHF010100T0_26;
	}

	public void setEHF010100T0_26(String eHF010100T0_26) {
		EHF010100T0_26 = eHF010100T0_26;
	}

	public String getEHF010100T0_27() {
		return EHF010100T0_27;
	}

	public void setEHF010100T0_27(String eHF010100T0_27) {
		EHF010100T0_27 = eHF010100T0_27;
	}

	public String getEHF010100T0_28() {
		return EHF010100T0_28;
	}

	public void setEHF010100T0_28(String eHF010100T0_28) {
		EHF010100T0_28 = eHF010100T0_28;
	}

	public String getEHF010100T0_29() {
		return EHF010100T0_29;
	}

	public void setEHF010100T0_29(String eHF010100T0_29) {
		EHF010100T0_29 = eHF010100T0_29;
	}

	public String getEHF010100T0_30() {
		return EHF010100T0_30;
	}

	public void setEHF010100T0_30(String eHF010100T0_30) {
		EHF010100T0_30 = eHF010100T0_30;
	}

	public String getHR_CompanySysNo() {
		return HR_CompanySysNo;
	}

	public void setHR_CompanySysNo(String hRCompanySysNo) {
		HR_CompanySysNo = hRCompanySysNo;
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

	public String getLogin_No() {
		return Login_No;
	}

	public void setLogin_No(String loginNo) {
		Login_No = loginNo;
	}

	public String getEHF010100T0_TYPE() {
		return EHF010100T0_TYPE;
	}

	public void setEHF010100T0_TYPE(String eHF010100T0TYPE) {
		EHF010100T0_TYPE = eHF010100T0TYPE;
	}

	public List getEHF010100T0_LIST() {
		return EHF010100T0_LIST;
	}

	public void setEHF010100T0_LIST(List eHF010100T0LIST) {
		EHF010100T0_LIST = eHF010100T0LIST;
	}

	public FormFile getUPLOADFILE() {
		return UPLOADFILE;
	}

	public void setUPLOADFILE(FormFile uPLOADFILE) {
		UPLOADFILE = uPLOADFILE;
	}

	public String getEHF010100T1_01() {
		return EHF010100T1_01;
	}

	public void setEHF010100T1_01(String eHF010100T1_01) {
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

	public String getEHF010100T1_0301() {
		return EHF010100T1_0301;
	}

	public void setEHF010100T1_0301(String eHF010100T1_0301) {
		EHF010100T1_0301 = eHF010100T1_0301;
	}

	public String getEHF010100T1_0302() {
		return EHF010100T1_0302;
	}

	public void setEHF010100T1_0302(String eHF010100T1_0302) {
		EHF010100T1_0302 = eHF010100T1_0302;
	}

	public String getEHF010100T1_0303() {
		return EHF010100T1_0303;
	}

	public void setEHF010100T1_0303(String eHF010100T1_0303) {
		EHF010100T1_0303 = eHF010100T1_0303;
	}

	public String getEHF010100T1_0304() {
		return EHF010100T1_0304;
	}

	public void setEHF010100T1_0304(String eHF010100T1_0304) {
		EHF010100T1_0304 = eHF010100T1_0304;
	}

	public String getEHF010100T2_01() {
		return EHF010100T2_01;
	}

	public void setEHF010100T2_01(String eHF010100T2_01) {
		EHF010100T2_01 = eHF010100T2_01;
	}

	public int getEHF010100T2_02() {
		return EHF010100T2_02;
	}

	public void setEHF010100T2_02(int eHF010100T2_02) {
		EHF010100T2_02 = eHF010100T2_02;
	}

	public String getEHF010100T2_03() {
		return EHF010100T2_03;
	}

	public void setEHF010100T2_03(String eHF010100T2_03) {
		EHF010100T2_03 = eHF010100T2_03;
	}

	public String getEHF010100T2_04() {
		return EHF010100T2_04;
	}

	public void setEHF010100T2_04(String eHF010100T2_04) {
		EHF010100T2_04 = eHF010100T2_04;
	}

	public String getEHF010100T2_05() {
		return EHF010100T2_05;
	}

	public void setEHF010100T2_05(String eHF010100T2_05) {
		EHF010100T2_05 = eHF010100T2_05;
	}

	public String getEHF010100T2_06() {
		return EHF010100T2_06;
	}

	public void setEHF010100T2_06(String eHF010100T2_06) {
		EHF010100T2_06 = eHF010100T2_06;
	}

	public String getEHF010100T2_07() {
		return EHF010100T2_07;
	}

	public void setEHF010100T2_07(String eHF010100T2_07) {
		EHF010100T2_07 = eHF010100T2_07;
	}

	public String getEHF010100T2_08() {
		return EHF010100T2_08;
	}

	public void setEHF010100T2_08(String eHF010100T2_08) {
		EHF010100T2_08 = eHF010100T2_08;
	}

	public String getEHF010100T2_09() {
		return EHF010100T2_09;
	}

	public void setEHF010100T2_09(String eHF010100T2_09) {
		EHF010100T2_09 = eHF010100T2_09;
	}

	public String getEHF010100T2_10() {
		return EHF010100T2_10;
	}

	public void setEHF010100T2_10(String eHF010100T2_10) {
		EHF010100T2_10 = eHF010100T2_10;
	}

	public List getEHF010100T2_LIST() {
		return EHF010100T2_LIST;
	}

	public void setEHF010100T2_LIST(List eHF010100T2LIST) {
		EHF010100T2_LIST = eHF010100T2LIST;
	}

	public String getEHF010100T3_01() {
		return EHF010100T3_01;
	}

	public void setEHF010100T3_01(String eHF010100T3_01) {
		EHF010100T3_01 = eHF010100T3_01;
	}

	public int getEHF010100T3_02() {
		return EHF010100T3_02;
	}

	public void setEHF010100T3_02(int eHF010100T3_02) {
		EHF010100T3_02 = eHF010100T3_02;
	}

	public String getEHF010100T3_03() {
		return EHF010100T3_03;
	}

	public void setEHF010100T3_03(String eHF010100T3_03) {
		EHF010100T3_03 = eHF010100T3_03;
	}

	public String getEHF010100T3_04() {
		return EHF010100T3_04;
	}

	public void setEHF010100T3_04(String eHF010100T3_04) {
		EHF010100T3_04 = eHF010100T3_04;
	}

	public String getEHF010100T3_05() {
		return EHF010100T3_05;
	}

	public void setEHF010100T3_05(String eHF010100T3_05) {
		EHF010100T3_05 = eHF010100T3_05;
	}

	public String getEHF010100T3_06() {
		return EHF010100T3_06;
	}

	public void setEHF010100T3_06(String eHF010100T3_06) {
		EHF010100T3_06 = eHF010100T3_06;
	}

	public String getEHF010100T3_07() {
		return EHF010100T3_07;
	}

	public void setEHF010100T3_07(String eHF010100T3_07) {
		EHF010100T3_07 = eHF010100T3_07;
	}

	public String getEHF010100T3_08() {
		return EHF010100T3_08;
	}

	public void setEHF010100T3_08(String eHF010100T3_08) {
		EHF010100T3_08 = eHF010100T3_08;
	}

	public List getEHF010100T3_LIST() {
		return EHF010100T3_LIST;
	}

	public void setEHF010100T3_LIST(List eHF010100T3LIST) {
		EHF010100T3_LIST = eHF010100T3LIST;
	}

	public String getEHF010100T4_01() {
		return EHF010100T4_01;
	}

	public void setEHF010100T4_01(String eHF010100T4_01) {
		EHF010100T4_01 = eHF010100T4_01;
	}

	public int getEHF010100T4_02() {
		return EHF010100T4_02;
	}

	public void setEHF010100T4_02(int eHF010100T4_02) {
		EHF010100T4_02 = eHF010100T4_02;
	}

	public String getEHF010100T4_03() {
		return EHF010100T4_03;
	}

	public void setEHF010100T4_03(String eHF010100T4_03) {
		EHF010100T4_03 = eHF010100T4_03;
	}

	public String getEHF010100T4_04() {
		return EHF010100T4_04;
	}

	public void setEHF010100T4_04(String eHF010100T4_04) {
		EHF010100T4_04 = eHF010100T4_04;
	}

	public String getEHF010100T4_05() {
		return EHF010100T4_05;
	}

	public void setEHF010100T4_05(String eHF010100T4_05) {
		EHF010100T4_05 = eHF010100T4_05;
	}

	public String getEHF010100T4_06() {
		return EHF010100T4_06;
	}

	public void setEHF010100T4_06(String eHF010100T4_06) {
		EHF010100T4_06 = eHF010100T4_06;
	}

	public String getEHF010100T4_07() {
		return EHF010100T4_07;
	}

	public void setEHF010100T4_07(String eHF010100T4_07) {
		EHF010100T4_07 = eHF010100T4_07;
	}

	public List getEHF010100T4_LIST() {
		return EHF010100T4_LIST;
	}

	public void setEHF010100T4_LIST(List eHF010100T4LIST) {
		EHF010100T4_LIST = eHF010100T4LIST;
	}

	public String getEHF010100T5_01() {
		return EHF010100T5_01;
	}

	public void setEHF010100T5_01(String eHF010100T5_01) {
		EHF010100T5_01 = eHF010100T5_01;
	}

	public int getEHF010100T5_02() {
		return EHF010100T5_02;
	}

	public void setEHF010100T5_02(int eHF010100T5_02) {
		EHF010100T5_02 = eHF010100T5_02;
	}

	public String getEHF010100T5_03() {
		return EHF010100T5_03;
	}

	public void setEHF010100T5_03(String eHF010100T5_03) {
		EHF010100T5_03 = eHF010100T5_03;
	}

	public int getEHF010100T5_04() {
		return EHF010100T5_04;
	}

	public void setEHF010100T5_04(int eHF010100T5_04) {
		EHF010100T5_04 = eHF010100T5_04;
	}

	public String getEHF010100T5_05() {
		return EHF010100T5_05;
	}

	public void setEHF010100T5_05(String eHF010100T5_05) {
		EHF010100T5_05 = eHF010100T5_05;
	}

	public String getEHF010100T5_06() {
		return EHF010100T5_06;
	}

	public void setEHF010100T5_06(String eHF010100T5_06) {
		EHF010100T5_06 = eHF010100T5_06;
	}

	public List getEHF010100T5_LIST() {
		return EHF010100T5_LIST;
	}

	public void setEHF010100T5_LIST(List eHF010100T5LIST) {
		EHF010100T5_LIST = eHF010100T5LIST;
	}

	public String getEHF010100T6_01() {
		return EHF010100T6_01;
	}

	public void setEHF010100T6_01(String eHF010100T6_01) {
		EHF010100T6_01 = eHF010100T6_01;
	}

	public String getEHF010100T6_02() {
		return EHF010100T6_02;
	}

	public void setEHF010100T6_02(String eHF010100T6_02) {
		EHF010100T6_02 = eHF010100T6_02;
	}

	public String getEHF010100T6_03() {
		return EHF010100T6_03;
	}

	public void setEHF010100T6_03(String eHF010100T6_03) {
		EHF010100T6_03 = eHF010100T6_03;
	}

	public String getEHF010100T6_04() {
		return EHF010100T6_04;
	}

	public void setEHF010100T6_04(String eHF010100T6_04) {
		EHF010100T6_04 = eHF010100T6_04;
	}

	public boolean isEHF010100T6_05() {
		return EHF010100T6_05;
	}

	public void setEHF010100T6_05(boolean eHF010100T6_05) {
		EHF010100T6_05 = eHF010100T6_05;
	}

	public String getEHF010100T6_07() {
		return EHF010100T6_07;
	}

	public void setEHF010100T6_07(String eHF010100T6_07) {
		EHF010100T6_07 = eHF010100T6_07;
	}

	public String getEHF010100T6_08() {
		return EHF010100T6_08;
	}

	public void setEHF010100T6_08(String eHF010100T6_08) {
		EHF010100T6_08 = eHF010100T6_08;
	}

	public String getEHF010100T6_09() {
		return EHF010100T6_09;
	}

	public void setEHF010100T6_09(String eHF010100T6_09) {
		EHF010100T6_09 = eHF010100T6_09;
	}

	public String getEHF010100T6_10() {
		return EHF010100T6_10;
	}

	public void setEHF010100T6_10(String eHF010100T6_10) {
		EHF010100T6_10 = eHF010100T6_10;
	}

	public String getEHF010100T6_11() {
		return EHF010100T6_11;
	}

	public void setEHF010100T6_11(String eHF010100T6_11) {
		EHF010100T6_11 = eHF010100T6_11;
	}

	public String getEHF010100T6_12() {
		return EHF010100T6_12;
	}

	public void setEHF010100T6_12(String eHF010100T6_12) {
		EHF010100T6_12 = eHF010100T6_12;
	}

	public String getEHF010100T6_13() {
		return EHF010100T6_13;
	}

	public void setEHF010100T6_13(String eHF010100T6_13) {
		EHF010100T6_13 = eHF010100T6_13;
	}

	public String getEHF010100T6_14() {
		return EHF010100T6_14;
	}

	public void setEHF010100T6_14(String eHF010100T6_14) {
		EHF010100T6_14 = eHF010100T6_14;
	}

	public String getEHF010100T6_15() {
		return EHF010100T6_15;
	}

	public void setEHF010100T6_15(String eHF010100T6_15) {
		EHF010100T6_15 = eHF010100T6_15;
	}

	public String getEHF000300T0_03() {
		return EHF000300T0_03;
	}

	public void setEHF000300T0_03(String eHF000300T0_03) {
		EHF000300T0_03 = eHF000300T0_03;
	}

	public String getEHF000300T0_02() {
		return EHF000300T0_02;
	}

	public void setEHF000300T0_02(String eHF000300T0_02) {
		EHF000300T0_02 = eHF000300T0_02;
	}

	public String getEHF010100T6_06() {
		return EHF010100T6_06;
	}

	public void setEHF010100T6_06(String eHF010100T6_06) {
		EHF010100T6_06 = eHF010100T6_06;
	}

	public String getEHF000200T0_02() {
		return EHF000200T0_02;
	}

	public void setEHF000200T0_02(String eHF000200T0_02) {
		EHF000200T0_02 = eHF000200T0_02;
	}

	public String getEHF000200T0_03() {
		return EHF000200T0_03;
	}

	public void setEHF000200T0_03(String eHF000200T0_03) {
		EHF000200T0_03 = eHF000200T0_03;
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

	public String getEHF010100T4_08() {
		return EHF010100T4_08;
	}

	public void setEHF010100T4_08(String eHF010100T4_08) {
		EHF010100T4_08 = eHF010100T4_08;
	}

	public int getEHF010100T8_01() {
		return EHF010100T8_01;
	}

	public void setEHF010100T8_01(int eHF010100T8_01) {
		EHF010100T8_01 = eHF010100T8_01;
	}

	public String getEHF010100T8_02() {
		return EHF010100T8_02;
	}

	public void setEHF010100T8_02(String eHF010100T8_02) {
		EHF010100T8_02 = eHF010100T8_02;
	}

	public String getEHF010100T8_03() {
		return EHF010100T8_03;
	}

	public void setEHF010100T8_03(String eHF010100T8_03) {
		EHF010100T8_03 = eHF010100T8_03;
	}

	public String getEHF010100T8_04() {
		return EHF010100T8_04;
	}

	public void setEHF010100T8_04(String eHF010100T8_04) {
		EHF010100T8_04 = eHF010100T8_04;
	}

	public String getEHF010100T8_04_TXT() {
		return EHF010100T8_04_TXT;
	}

	public void setEHF010100T8_04_TXT(String eHF010100T8_04TXT) {
		EHF010100T8_04_TXT = eHF010100T8_04TXT;
	}

	public String getEHF010100T8_04_KEY() {
		return EHF010100T8_04_KEY;
	}

	public void setEHF010100T8_04_KEY(String eHF010100T8_04KEY) {
		EHF010100T8_04_KEY = eHF010100T8_04KEY;
	}

	public String getEHF010100T8_04_HIDE() {
		return EHF010100T8_04_HIDE;
	}

	public void setEHF010100T8_04_HIDE(String eHF010100T8_04HIDE) {
		EHF010100T8_04_HIDE = eHF010100T8_04HIDE;
	}

	public String getEHF010100T8_05() {
		return EHF010100T8_05;
	}

	public void setEHF010100T8_05(String eHF010100T8_05) {
		EHF010100T8_05 = eHF010100T8_05;
	}

	public String getEHF010100T8_06() {
		return EHF010100T8_06;
	}

	public void setEHF010100T8_06(String eHF010100T8_06) {
		EHF010100T8_06 = eHF010100T8_06;
	}

	public void setUp_file1(FormFile up_file1) {
		this.up_file1 = up_file1;
	}

	public FormFile getUp_file1() {
		return up_file1;
	}

	public void setEHF030200T1_04_02(String eHF030200T1_04_02) {
		EHF030200T1_04_02 = eHF030200T1_04_02;
	}

	public String getEHF030200T1_04_02() {
		return EHF030200T1_04_02;
	}

	public void setTabsutil_EMP(String tabsutil_EMP) {
		this.tabsutil_EMP = tabsutil_EMP;
	}

	public String getTabsutil_EMP() {
		return tabsutil_EMP;
	}

	public void setTabsutil_ATT(String tabsutil_ATT) {
		this.tabsutil_ATT = tabsutil_ATT;
	}

	public String getTabsutil_ATT() {
		return tabsutil_ATT;
	}

	public void setTabsutil_SAL(String tabsutil_SAL) {
		this.tabsutil_SAL = tabsutil_SAL;
	}

	public String getTabsutil_SAL() {
		return tabsutil_SAL;
	}

	public void setTabsutil_INS(String tabsutil_INS) {
		this.tabsutil_INS = tabsutil_INS;
	}

	public String getTabsutil_INS() {
		return tabsutil_INS;
	}

	public void setTabsutil_EXP(String tabsutil_EXP) {
		this.tabsutil_EXP = tabsutil_EXP;
	}

	public String getTabsutil_EXP() {
		return tabsutil_EXP;
	}
	public String getEHF010300T0_01() {
		return EHF010300T0_01;
	}
	public void setEHF010300T0_01(String eHF010300T0_01) {
		EHF010300T0_01 = eHF010300T0_01;
	}
	public String getEHF010300T0_02() {
		return EHF010300T0_02;
	}
	public void setEHF010300T0_02(String eHF010300T0_02) {
		EHF010300T0_02 = eHF010300T0_02;
	}
	public String getEHF010300T0_03() {
		return EHF010300T0_03;
	}
	public void setEHF010300T0_03(String eHF010300T0_03) {
		EHF010300T0_03 = eHF010300T0_03;
	}
	public String getEHF010300T0_04() {
		return EHF010300T0_04;
	}
	public void setEHF010300T0_04(String eHF010300T0_04) {
		EHF010300T0_04 = eHF010300T0_04;
	}
	public String getEHF010300T0_04_TXT() {
		return EHF010300T0_04_TXT;
	}
	public void setEHF010300T0_04_TXT(String eHF010300T0_04TXT) {
		EHF010300T0_04_TXT = eHF010300T0_04TXT;
	}
	public String getEHF010300T0_05() {
		return EHF010300T0_05;
	}
	public void setEHF010300T0_05(String eHF010300T0_05) {
		EHF010300T0_05 = eHF010300T0_05;
	}
	public String getEHF010300T0_05_TXT() {
		return EHF010300T0_05_TXT;
	}
	public void setEHF010300T0_05_TXT(String eHF010300T0_05TXT) {
		EHF010300T0_05_TXT = eHF010300T0_05TXT;
	}
	public String getEHF010300T0_06() {
		return EHF010300T0_06;
	}
	public void setEHF010300T0_06(String eHF010300T0_06) {
		EHF010300T0_06 = eHF010300T0_06;
	}
	public String getEHF010300T0_06_TXT() {
		return EHF010300T0_06_TXT;
	}
	public void setEHF010300T0_06_TXT(String eHF010300T0_06TXT) {
		EHF010300T0_06_TXT = eHF010300T0_06TXT;
	}
	public String getEHF010300T0_07() {
		return EHF010300T0_07;
	}
	public void setEHF010300T0_07(String eHF010300T0_07) {
		EHF010300T0_07 = eHF010300T0_07;
	}
	public String getEHF010300T0_07_DAY() {
		return EHF010300T0_07_DAY;
	}
	public void setEHF010300T0_07_DAY(String eHF010300T0_07DAY) {
		EHF010300T0_07_DAY = eHF010300T0_07DAY;
	}
	public String getEHF010300T0_07_HOUR() {
		return EHF010300T0_07_HOUR;
	}
	public void setEHF010300T0_07_HOUR(String eHF010300T0_07HOUR) {
		EHF010300T0_07_HOUR = eHF010300T0_07HOUR;
	}
	public String getEHF010300T0_08() {
		return EHF010300T0_08;
	}
	public void setEHF010300T0_08(String eHF010300T0_08) {
		EHF010300T0_08 = eHF010300T0_08;
	}
	public String getEHF010300T0_08_DAY() {
		return EHF010300T0_08_DAY;
	}
	public void setEHF010300T0_08_DAY(String eHF010300T0_08DAY) {
		EHF010300T0_08_DAY = eHF010300T0_08DAY;
	}
	public String getEHF010300T0_08_HOUR() {
		return EHF010300T0_08_HOUR;
	}
	public void setEHF010300T0_08_HOUR(String eHF010300T0_08HOUR) {
		EHF010300T0_08_HOUR = eHF010300T0_08HOUR;
	}
	public String getEHF010300T0_081() {
		return EHF010300T0_081;
	}
	public void setEHF010300T0_081(String eHF010300T0_081) {
		EHF010300T0_081 = eHF010300T0_081;
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
	public String getEHF010300T0_12() {
		return EHF010300T0_12;
	}
	public void setEHF010300T0_12(String eHF010300T0_12) {
		EHF010300T0_12 = eHF010300T0_12;
	}
	public List getEHF010300C() {
		return EHF010300C;
	}
	public void setEHF010300C(List eHF010300C) {
		EHF010300C = eHF010300C;
	}
	public String getEHF010300T1_07_DAY() {
		return EHF010300T1_07_DAY;
	}
	public void setEHF010300T1_07_DAY(String eHF010300T1_07DAY) {
		EHF010300T1_07_DAY = eHF010300T1_07DAY;
	}
	public String getEHF010300T1_07_HOUR() {
		return EHF010300T1_07_HOUR;
	}
	public void setEHF010300T1_07_HOUR(String eHF010300T1_07HOUR) {
		EHF010300T1_07_HOUR = eHF010300T1_07HOUR;
	}
	public String getEHF010300T1_08_DAY() {
		return EHF010300T1_08_DAY;
	}
	public void setEHF010300T1_08_DAY(String eHF010300T1_08DAY) {
		EHF010300T1_08_DAY = eHF010300T1_08DAY;
	}
	public String getEHF010300T1_08_HOUR() {
		return EHF010300T1_08_HOUR;
	}
	public void setEHF010300T1_08_HOUR(String eHF010300T1_08HOUR) {
		EHF010300T1_08_HOUR = eHF010300T1_08HOUR;
	}
	public String getEHF010300T1_09() {
		return EHF010300T1_09;
	}
	public void setEHF010300T1_09(String eHF010300T1_09) {
		EHF010300T1_09 = eHF010300T1_09;
	}
	public String getEHF010300T1_10() {
		return EHF010300T1_10;
	}
	public void setEHF010300T1_10(String eHF010300T1_10) {
		EHF010300T1_10 = eHF010300T1_10;
	}

	public void setTabsutil_VA(String tabsutil_VA) {
		this.tabsutil_VA = tabsutil_VA;
	}

	public String getTabsutil_VA() {
		return tabsutil_VA;
	}

	public void setTabsutil_DA(String tabsutil_DA) {
		this.tabsutil_DA = tabsutil_DA;
	}

	public String getTabsutil_DA() {
		return tabsutil_DA;
	}

	public void setEHF010100T0_31(String eHF010100T0_31) {
		EHF010100T0_31 = eHF010100T0_31;
	}

	public String getEHF010100T0_31() {
		return EHF010100T0_31;
	}

	public void setEHF010100T1_LIST(List eHF010100T1_LIST) {
		EHF010100T1_LIST = eHF010100T1_LIST;
	}

	public List getEHF010100T1_LIST() {
		return EHF010100T1_LIST;
	}

}
