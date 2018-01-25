package com.spon.ems.popo.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.ems.popo.models.EHF310100;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;
import com.sun.faces.util.ToolsUtil;

public class EHF310100M0F extends ActionForm implements ValidateForm{

	private String EHF310100T0_01;			//產婦的基本資料			系統編號
	private String EHF310100T0_02;			//產婦的基本資料			檔案編號
	private String EHF310100T0_03;			//產婦的基本資料			櫃號
	private String EHF310100T0_04;			//產婦的基本資料			產婦姓名
	private String EHF310100T0_05;			//產婦的基本資料			產別
	private String EHF310100T0_06;			//產婦的基本資料			預產期
	private String EHF310100T0_07;			//產婦的基本資料			生產日
	private String EHF310100T0_08;			//產婦的基本資料			訂餐日期
	private String EHF310100T0_08_B;		//產婦的基本資料			訂餐日期起
	private String EHF310100T0_08_E;		//產婦的基本資料			訂餐日期迄
	private int EHF310100T0_09;				//產婦的基本資料			訂餐天數
	private String EHF310100T0_10;			//產婦的基本資料			送餐日期
	private String EHF310100T0_11;			//產婦的基本資料			醫院
	private String EHF310100T0_11_TXT;
	private int EHF310100T0_12;				//產婦的基本資料			醫院用餐天數
	private String EHF310100T0_13;			//產婦的基本資料			醫院用餐期間(起)
	private String EHF310100T0_14;			//產婦的基本資料			醫院用餐期間(迄)
	private String EHF310100T0_15;			//產婦的基本資料			醫院房號
	private String EHF310100T0_16;			//產婦的基本資料			醫院路線
	private String EHF310100T0_16_TXT;	    //產婦的基本資料			醫院路線名稱
	private String EHF310100T0_17;			//產婦的基本資料			住宅路線
	private String EHF310100T0_17_TXT;
	private int EHF310100T0_18;				//產婦的基本資料			住宅用餐天數
	private String EHF310100T0_19;			//產婦的基本資料			住宅用餐期間(起)
	private String EHF310100T0_20;			//產婦的基本資料			住宅用餐期間(迄)
	private String EHF310100T0_21;			//產婦的基本資料			行動電話(產婦)
	private String EHF310100T0_22;			//產婦的基本資料			連絡電話(住)
	private String EHF310100T0_23;			//產婦的基本資料			聯絡1
	private String EHF310100T0_24;			//產婦的基本資料			聯絡1
	private String EHF310100T0_25;			//產婦的基本資料			聯絡2
	private String EHF310100T0_26;			//產婦的基本資料			聯絡2
	private String EHF310100T0_27;			//產婦的基本資料			聯絡3
	private String EHF310100T0_28;			//產婦的基本資料			聯絡3
	private String EHF310100T0_29;			//產婦的基本資料			聯絡4
	private String EHF310100T0_30;			//產婦的基本資料			聯絡4
	private String EHF310100T0_31;			//產婦的基本資料			產婦地址
	private String EHF310100T0_32;			//產婦的基本資料			備註1
	private String EHF310100T0_33;			//產婦的基本資料			備註2
	private String EHF310100T0_34;			//產婦的基本資料			公司代碼
	
	private List EHF310100_C = new ArrayList();
	
	
	
	private String EHF310100T1_01;			//訂餐資訊-每日明細	系統編號
	private String EHF310100T1_02;			//訂餐資訊-每日明細	日期
	private String EHF310100T1_02_B;		//訂餐資訊-每日明細	日期起
	private String EHF310100T1_02_E;		//訂餐資訊-每日明細	日期迄
	
	private String EHF310100T1_03;			//訂餐資訊-每日明細	餐點類別
	private String EHF310100T1_04;			//訂餐資訊-每日明細	路線
	private String EHF310100T1_05;			//訂餐資訊-每日明細	素食
	
	private String[] EHF310100T1_03_total = new String[3];
	private String[] EHF310100T1_04_total = new String[3];
	private String[] EHF310100T1_05_total = new String[3];
	
	private String EHF310100T1_03_A;		//訂餐資訊-每日明細	餐點類別A(早)
	private String EHF310100T1_04_A;		//訂餐資訊-每日明細	路線A(早)
	private String EHF310100T1_05_A;		//訂餐資訊-每日明細	素食A(早)
	
	private String EHF310100T1_03_B;		//訂餐資訊-每日明細	餐點類別B(中)
	private String EHF310100T1_04_B;		//訂餐資訊-每日明細	路線B(中)
	private String EHF310100T1_05_B;		//訂餐資訊-每日明細	素食B(中)
	
	private String EHF310100T1_03_C;		//訂餐資訊-每日明細	餐點類別C(晚)
	private String EHF310100T1_04_C;		//訂餐資訊-每日明細	路線C(晚)
	private String EHF310100T1_05_C;		//訂餐資訊-每日明細	素食C(晚)
	
	private String EHF310100T1_06;			//訂餐資訊-每日明細	公司代碼
	
	
	
	private String EHF310100T2_01;			//訂餐資訊-養生飲品明細		系統編號
	private String EHF310100T2_02;			//訂餐資訊-養生飲品明細		順序號碼
	private String EHF310100T2_03;			//訂餐資訊-養生飲品明細		日期起
	private String EHF310100T2_04;			//訂餐資訊-養生飲品明細		日期迄
	private String EHF310100T2_05;			//訂餐資訊-養生飲品明細		餐點類別
	private String EHF310100T2_06;			//訂餐資訊-養生飲品明細		養生飲品
	

	private String[] EHF310100T2_06_total = new String[50]; //訂餐資訊-養生飲品明細		養生飲品(陣列取值用)
	private String EHF310100T2_07;			//訂餐資訊-養生飲品明細		公司代碼
	
	
	
	private String EHF310200T0_01;			//訂餐特殊資訊		系統編號
	private String EHF310200T0_02;			//訂餐特殊資訊		特殊習慣
	private String EHF310200T0_03;			//訂餐特殊資訊		備註
	
	
	private String EHF310200T1_01;				//訂餐特殊資訊明細1	系統編號
	private String EHF310200T1_02;				//訂餐特殊資訊明細1	順序號碼
	private String EHF310200T1_03;				//訂餐特殊資訊明細1	不吃食物
	private String EHF310200T1_03_type;			//訂餐特殊資訊明細1	不吃食物種類
	private String EHF310200T1_03_type_TXT;		//訂餐特殊資訊明細1	不吃食物種類名稱
	private String EHF310200T1_03_detail;		//訂餐特殊資訊明細1	不吃食物明細
	private String EHF310200T1_03_detail_TXT;	//訂餐特殊資訊明細1	不吃食物明細名稱
	
	
	private String EHF310200T2_01;			//訂餐特殊資訊明細2	系統編號
	private String EHF310200T2_02;			//訂餐特殊資訊明細2	順序號碼
	private String EHF310200T2_03;			//訂餐特殊資訊明細2	不喝飲品
	private String EHF310200T2_03_TXT;		//訂餐特殊資訊明細2	不喝飲品
	private String EHF310200T2_03_SHOW;		//訂餐特殊資訊明細2	不喝飲品
	
	
	private String EHF310200T3_01;			//訂餐特殊資訊明細3	系統編號
	private String EHF310200T3_02;			//訂餐特殊資訊明細3	順序號碼
	private String EHF310200T3_03;			//訂餐特殊資訊明細3	特殊需求
	
	
	private String EHF310200T4_01;			//訂餐特殊資訊明細4	系統編號
	private String EHF310200T4_02;			//訂餐特殊資訊明細4	順序號碼
	private String EHF310200T4_03;			//訂餐特殊資訊明細4	特殊口味
	
	
	private String EHF310200T5_01;			//訂餐特殊資訊明細5	系統編號
	private String EHF310200T5_02;			//訂餐特殊資訊明細5	順序號碼
	private String EHF310200T5_03;			//訂餐特殊資訊明細5	菜單類別
	
	
	
	private String EHF310300T0_01;			//電訪紀錄明細		系統編號
	private String EHF310300T0_02;			//電訪紀錄明細		順序號碼
	private String EHF310300T0_03;			//電訪紀錄明細		電訪人員
	private String EHF310300T0_03_SHOW;		
	private String EHF310300T0_03_TXT;		
	private String EHF310300T0_04;			//電訪紀錄明細		電訪日期
	private String EHF310300T0_05;			//電訪紀錄明細		電訪內容
	private String EHF310300T0_06;			//電訪紀錄明細		備註
	private String EHF310300T0_07;			//電訪紀錄明細		公司代碼
	
	
	private String EHF310400T0_01;			//付款資訊1			系統編號
	private int EHF310400T0_02;				//付款資訊1			訂餐天數
	private int EHF310400T0_03;				//付款資訊1			定價
	private int EHF310400T0_04;				//付款資訊1			折扣
	private int EHF310400T0_05;				//付款資訊1			已付金額
	private String EHF310400T0_06;			//付款資訊1			公司代碼
	
	
	
	private String EHF310400T2_01;			//付款資訊2			系統編號
	private String EHF310400T2_02;			//付款資訊2			定價代碼
	private String EHF310400T2_02_All;		//付款資訊2			A+B+C定價代碼
	private String EHF310400T2_02_A;		//付款資訊2			A定價代碼
	private String EHF310400T2_02_B;		//付款資訊2			B定價代碼
	private String EHF310400T2_02_C;		//付款資訊2			C定價代碼
	private int EHF310400T2_03;				//付款資訊2			數量
	private int EHF310400T2_03_All;			//付款資訊2			A+B+C數量
	private int EHF310400T2_03_A;			//付款資訊2			A數量
	private int EHF310400T2_03_B;			//付款資訊2			B數量
	private int EHF310400T2_03_C;			//付款資訊2			C數量
	private int EHF310400T2_03_All_Pay;		//付款資訊2			A+B+C金額	
	private int EHF310400T2_03_A_Pay;		//付款資訊2			A金額
	private int EHF310400T2_03_B_Pay;		//付款資訊2			B金額
	private int EHF310400T2_03_C_Pay;		//付款資訊2			C金額
	private int EHF310400T2_finalPay;		//付款資訊2			定價
	private int EHF310400T2_discount;		//付款資訊2			折扣金額
	private int EHF310400T2_realPay;		//付款資訊2			實際金額
	private int EHF310400T2_unPay;			//付款資訊2			未付金額
	
	
	private String EHF310400T1_01;			//付款明細			系統編號
	private int EHF310400T1_02;				//付款明細			順序號碼
	private String EHF310400T1_03;			//付款明細			開單日期
	private String EHF310400T1_04;			//付款明細			開單人員
	private String EHF310400T1_04_SHOW;
	private String EHF310400T1_04_TXT;
	private String EHF310400T1_05;			//付款明細			經手人員
	private String EHF310400T1_05_SHOW;
	private String EHF310400T1_05_TXT;
	private String EHF310400T1_06;			//付款明細			付款方式
	private String EHF310400T1_07;			//付款明細			付款類別
	private String EHF310400T1_08;			//付款明細			預計收款日
	private int EHF310400T1_09;				//付款明細			預計金額
	private String EHF310400T1_10;			//付款明細			實際收款日
	private int EHF310400T1_11;				//付款明細			實際金額
	private String EHF310400T1_12;			//付款明細			備註
	private String EHF310400T1_13;			//付款明細			確認
	private String EHF310400T1_14;			//付款明細			刪除
	private String EHF310400T1_15;			//付款明細			公司代碼
	
	
	
	private String EHF310500T0_01;			//贈品資訊			系統編號
	private int EHF310500T0_02;				//贈品資訊			順序號碼
	//private String EHF310500T0_02_SHOW;			
	//private String EHF310500T0_02_TXT;			
	private String EHF310500T0_03;			//贈品資訊			預留欄位
	private String EHF310500T0_04;			//贈品資訊			領取日期
	private String EHF310500T0_05;			//贈品資訊			開單日期
	private String EHF310500T0_06;			//贈品資訊			贈品類別
	private String EHF310500T0_06_TXT;
	private String EHF310500T0_07;			//贈品資訊			預留欄位
	private String EHF310500T0_08;			//贈品資訊			備註
	private String EHF310500T0_09;			//贈品資訊			公司代碼
	
	private List EHF310100T1_List = new ArrayList();		//每日明細collection
	private List EHF310100T2_List = new ArrayList();		//養生飲品collection
	private List EHF310200T1_List = new ArrayList();		//不吃食物collection
	private List EHF310200T2_List = new ArrayList();		//不喝飲品collection
	private List EHF310200T3_List = new ArrayList();		//特殊需求collection
	private List EHF310200T4_List = new ArrayList();		//特殊口味collection
	private List EHF310200T5_List = new ArrayList();		//清淡去油collection
	
	private List EHF310300T0_List = new ArrayList();		//電訪紀錄collection
	private List EHF310400T1_List = new ArrayList();		//付款資訊collection
	private List EHF310500T0_List = new ArrayList();		//贈品資訊collection
	
	private  String  USER_CREATE;
	private  String  USER_UPDATE;
	private   int  VERSION;
	private  String  DATE_CREATE;
	private  String  DATE_UPDATE;
	

	private String tabsutil_CUS;  //產婦基本資料
	private String tabsutil_ORD;  //訂餐資訊
	private String tabsutil_SPE;  //訂餐特殊資訊
	private String tabsutil_CALL; //電訪記錄
	private String tabsutil_PAY;  //付款資訊
	private String tabsutil_GIFT; //贈品資訊
	
	
	
	
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		ActionErrors l_actionErrors = new ActionErrors();
		
		//新增客戶需求單判斷條件
		if (request.getAttribute("action").equals("addData")) {
			//檢核欄位空白
			BA_Vaildate ve=BA_Vaildate.getInstance();
			ve.isEmpty(l_actionErrors, EHF310100T0_04, "EHF310100T0_04", "不可空白");
			ve.isEmpty(l_actionErrors, EHF310100T0_21, "EHF310100T0_21", "不可空白");
			ve.isEmpty(l_actionErrors, EHF310100T0_31, "EHF310100T0_31", "不可空白");
			if(EHF310100T0_09==0){
				l_actionErrors.add("EHF310100T0_09",new ActionMessage("不可為零"));
			}
			
		}
		//新增每日明細時判斷條件
		if (request.getAttribute("action").equals("addEHF310100T1")) {
			
			addEHF310100T1_validate(l_actionErrors, request, conn);
		}
		
		//新增養生飲品時判斷條件
		if (request.getAttribute("action").equals("addEHF310100T2")) {
			
			addEHF310100T2_validate(l_actionErrors, request, conn);
		}
		
		//新增不吃食物時判斷條件
		if (request.getAttribute("action").equals("addEHF310200T1")) {
			
			addEHF310200T1_validate(l_actionErrors, request, conn);
		}
		
		//新增不喝茶飲時判斷條件
		if (request.getAttribute("action").equals("addEHF310200T2")) {
			
			addEHF310200T2_validate(l_actionErrors, request, conn);
		}
		
		//新增特殊需求時判斷條件
		if (request.getAttribute("action").equals("addEHF310200T3")) {
			
			addEHF310200T3_validate(l_actionErrors, request, conn);
		}
		
		//新增特殊口味時判斷條件
		if (request.getAttribute("action").equals("addEHF310200T4")) {
			
			addEHF310200T4_validate(l_actionErrors, request, conn);
		}
		
		//新增清淡去油時判斷條件
		if (request.getAttribute("action").equals("addEHF310200T5")) {
			
			addEHF310200T5_validate(l_actionErrors, request, conn);
		}
		
		//新增電訪紀錄時判斷條件
		if (request.getAttribute("action").equals("addEHF310300T0")) {
			
			addEHF310300T0_validate(l_actionErrors, request, conn);
		}
		
		//新增付款資訊時判斷條件
		if (request.getAttribute("action").equals("addEHF310400T1")) {
			
			addEHF310400T1_validate(l_actionErrors, request, conn);
		}
		
		//新增贈品資訊時判斷條件
		if (request.getAttribute("action").equals("addEHF310500T0")) {
			
			addEHF310500T0_validate(l_actionErrors, request, conn);
		}
		
		return l_actionErrors;
	}

	private void addEHF310500T0_validate(ActionErrors lActionErrors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		try{
			//檢核欄位空白
			BA_Vaildate ve=BA_Vaildate.getInstance();

			ve.isEmpty(lActionErrors, EHF310500T0_04, "EHF310500T0_04", "不可空白");
			ve.isEmpty(lActionErrors, EHF310500T0_06, "EHF310500T0_06", "不可空白");
			
			setTabsutil_GIFT("yes");
			
		}catch(Exception e){
			System.out.println("檢核新增贈品資訊欄位空白時錯誤");	
		}
		
	}

	private void addEHF310100T1_validate(ActionErrors lActionErrors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//建立EHF310100元件
		EHF310100 ehf310100 = new EHF310100();
		//取得餐別清單
		List MENU_MEAL =  ehf310100.getcheckboxes(false, "MENU_MEAL", (String)request.getAttribute("compid"));
		
		ehf310100.close();
		Calendar cal_start = null;
		Calendar cal_end = null;
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{
			//檢核欄位空白
			BA_Vaildate ve=BA_Vaildate.getInstance();
			
			ve.isEmpty(lActionErrors, EHF310100T1_02_B, "EHF310100T1_02_B", "不可空白");
			ve.isEmpty(lActionErrors, EHF310100T1_02_E, "EHF310100T1_02_E", "不可空白");
			
			boolean nullFlag=true;
			
			for(int i = 0;i<MENU_MEAL.size();i++){
				
				if("".equals(EHF310100T1_03_total[i]) || EHF310100T1_03_total[i]==null){
					//代表此選項未勾選
				}else{
					//代表此選項有勾選
					nullFlag=false;
				}
			}
			if(nullFlag){
				//若一個都沒勾則報錯誤提醒使用者不可空白
				lActionErrors.add("EHF310100T1_03_total",new ActionMessage("不可空白"));
			}
			
			setTabsutil_ORD("yes");
			
		}catch(Exception e){
			System.out.println("檢核新增每日明細欄位空白時錯誤");	
		}
		
	}

	private void addEHF310400T1_validate(ActionErrors lActionErrors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
				
		
		try{
			//檢核欄位空白
			BA_Vaildate ve=BA_Vaildate.getInstance();

			ve.isEmpty(lActionErrors, EHF310400T1_03, "EHF310400T1_03", "不可空白");
			ve.isEmpty(lActionErrors, EHF310400T1_04_TXT, "EHF310400T1_04_TXT", "不可空白");
			ve.isEmpty(lActionErrors, EHF310400T1_06, "EHF310400T1_06", "不可空白");
			ve.isEmpty(lActionErrors, EHF310400T1_07, "EHF310400T1_07", "不可空白");
//			ve.isEmpty(lActionErrors, EHF310400T1_08, "EHF310400T1_08", "不可空白");
//			if(EHF310400T1_09==0){
//				lActionErrors.add("EHF310400T1_09",new ActionMessage("不可為零"));
//			}
			setTabsutil_PAY("yes");
			
		}catch(Exception e){
			System.out.println("檢核新增付款資訊欄位空白時錯誤");	
		}
	}

	private void addEHF310300T0_validate(ActionErrors lActionErrors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		

		try{
			//檢核欄位空白
			BA_Vaildate ve=BA_Vaildate.getInstance();

			ve.isEmpty(lActionErrors, EHF310300T0_04, "EHF310300T0_04", "不可空白");
			ve.isEmpty(lActionErrors, EHF310300T0_03_TXT, "EHF310300T0_03_TXT", "不可空白");
			
			setTabsutil_CALL("yes");
			
		}catch(Exception e){
			System.out.println("檢核新增電訪紀錄欄位空白時錯誤");	
		}
	}

	private void addEHF310200T5_validate(ActionErrors lActionErrors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢核資料有無重複
		try{
			String sql =   " SELECT * " +
						   " FROM EHF310200T5 " +
					   	   " WHERE 1=1 " +
					   	   " AND EHF310200T5_01 = '"+EHF310100T0_01+"' " +	//系統代碼
					   	   " AND EHF310200T5_03 = '"+EHF310200T5_03+"' " ;
			   	  
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				lActionErrors.add("EHF310200T5_03",new ActionMessage(""));
				request.setAttribute("ERR_MSG_DETAIL_DONTEAT","資料重複，請確認！！");
			}
			
			rs.close();
			stmt.close();
	
		}catch(Exception e){
			System.out.println("檢核新增清淡去油資料重複時錯誤");	
		}
		
		
		try{
			//檢核欄位空白
			BA_Vaildate ve=BA_Vaildate.getInstance();

			ve.isEmpty(lActionErrors, EHF310200T5_03, "EHF310200T5_03", "不可空白");
			
			setTabsutil_SPE("yes");
			
		}catch(Exception e){
			System.out.println("檢核新增清淡去油欄位空白時錯誤");	
		}
	}

	private void addEHF310200T4_validate(ActionErrors lActionErrors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢核資料有無重複
		try{
			String sql =   " SELECT * " +
						   " FROM EHF310200T4 " +
					   	   " WHERE 1=1 " +
					   	   " AND EHF310200T4_01 = '"+EHF310100T0_01+"' " +	//系統代碼
					   	   " AND EHF310200T4_03 = '"+EHF310200T4_03+"' " ;
			   	  
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				lActionErrors.add("EHF310200T4_03",new ActionMessage(""));
				request.setAttribute("ERR_MSG_DETAIL_DONTEAT","資料重複，請確認！！");
			}
			
			rs.close();
			stmt.close();
	
		}catch(Exception e){
			System.out.println("檢核新增特殊口味資料重複時錯誤");	
		}
		
		
		try{
			//檢核欄位空白
			BA_Vaildate ve=BA_Vaildate.getInstance();

			ve.isEmpty(lActionErrors, EHF310200T4_03, "EHF310200T4_03", "不可空白");
			
			setTabsutil_SPE("yes");
			
		}catch(Exception e){
			System.out.println("檢核新增特殊口味欄位空白時錯誤");	
		}
	}

	private void addEHF310200T3_validate(ActionErrors lActionErrors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢核資料有無重複
		try{
			String sql =   " SELECT * " +
						   " FROM EHF310200T3 " +
					   	   " WHERE 1=1 " +
					   	   " AND EHF310200T3_01 = '"+EHF310100T0_01+"' " +	//系統代碼
					   	   " AND EHF310200T3_03 = '"+EHF310200T3_03+"' " ;
			   	  
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				lActionErrors.add("EHF310200T3_03",new ActionMessage(""));
				request.setAttribute("ERR_MSG_DETAIL_DONTEAT","資料重複，請確認！！");
			}
			
			rs.close();
			stmt.close();
	
		}catch(Exception e){
			System.out.println("檢核新增特殊需求資料重複時錯誤");	
		}
		
		
		try{
			//檢核欄位空白
			BA_Vaildate ve=BA_Vaildate.getInstance();

			ve.isEmpty(lActionErrors, EHF310200T3_03, "EHF310200T3_03", "不可空白");
			
			setTabsutil_SPE("yes");
			
		}catch(Exception e){
			System.out.println("檢核新增特殊需求欄位空白時錯誤");	
		}
	}

	private void addEHF310200T2_validate(ActionErrors lActionErrors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢核資料有無重複
		try{
			String sql =   " SELECT * " +
						   " FROM EHF310200T2 " +
					   	   " WHERE 1=1 " +
					   	   " AND EHF310200T2_01 = '"+EHF310100T0_01+"' " +	//系統代碼
					   	   " AND EHF310200T2_03 = '"+EHF310200T2_03+"' " ;
			   	  
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
						
			if(rs.next()){
				lActionErrors.add("EHF310200T2_03",new ActionMessage(""));
				request.setAttribute("ERR_MSG_DETAIL_DONTEAT","資料重複，請確認！！");
			}
			
			rs.close();
			stmt.close();
	
		}catch(Exception e){
			System.out.println("檢核新增不喝飲品資料重複時錯誤");	
		}
		
		
		try{
			//檢核欄位空白
			BA_Vaildate ve=BA_Vaildate.getInstance();

			ve.isEmpty(lActionErrors, EHF310200T2_03, "EHF310200T2_03", "不可空白");
			
			setTabsutil_SPE("yes");
			
		}catch(Exception e){
			System.out.println("檢核新增不喝飲品資料重複時錯誤");	
		}
	}

	private void addEHF310200T1_validate(ActionErrors lActionErrors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢核資料有無重複
		try{
			String sql =   " SELECT * " +
						   " FROM EHF310200T1 " +
					   	   " WHERE 1=1 " +
					   	   " AND EHF310200T1_01 = '"+EHF310100T0_01+"' " +	//系統代碼
					   	   " AND EHF310200T1_03='"+EHF310200T1_03_type+"/"+EHF310200T1_03_detail+"' " ;
			   	  
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				lActionErrors.add("EHF310200T1_03_type_TXT",new ActionMessage(""));
				request.setAttribute("ERR_MSG_DETAIL_DONTEAT","資料重複，請確認！！");
			}
			
			rs.close();
			stmt.close();
	
		}catch(Exception e){
			System.out.println("檢核新增不吃食物資料重複時錯誤");	
		}
		
		
		try{
			//檢核欄位空白
			BA_Vaildate ve=BA_Vaildate.getInstance();

			ve.isEmpty(lActionErrors, EHF310200T1_03_type_TXT, "EHF310200T1_03_type_TXT", "不可空白");
			
			setTabsutil_SPE("yes");
			
		}catch(Exception e){
			System.out.println("檢核新增不吃食物欄位空白時錯誤");	
		}
	}

	private void addEHF310100T2_validate(ActionErrors lActionErrors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		

		//建立EHF310100元件
		EHF310100 ehf310100 = new EHF310100();
		//取得養生飲品清單
		List drinkList =  ehf310100.getcheckboxes(false, "Drink", (String)request.getAttribute("compid"));
		
		ehf310100.close();
		Calendar cal_start = null;
		Calendar cal_end = null;
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{
			//檢核欄位空白
			BA_Vaildate ve=BA_Vaildate.getInstance();
			
			ve.isEmpty(lActionErrors, EHF310100T2_03, "EHF310100T2_03", "不可空白");
			ve.isEmpty(lActionErrors, EHF310100T2_04, "EHF310100T2_04", "不可空白");
			ve.isEmpty(lActionErrors, EHF310100T2_05, "EHF310100T2_05", "不可空白");
			
			boolean nullFlag=true;
			
			for(int i = 0;i<drinkList.size();i++){
				
				if("".equals(EHF310100T2_06_total[i]) || EHF310100T2_06_total[i]==null){
					//代表此選項未勾選
				}else{
					//代表此選項有勾選
					nullFlag=false;
				}
			}
			if(nullFlag){
				//若一個都沒勾則報錯誤提醒使用者不可空白
				lActionErrors.add("EHF310100T2_06_total",new ActionMessage("不可空白"));
			}
		}catch(Exception e){
			System.out.println("檢核新增養生飲品明細欄位空白時錯誤");	
		}
		if(lActionErrors.isEmpty()){
			
			//檢核日期條件邏輯有無錯誤
			try{
				cal_start = tools.covertStringToCalendar(EHF310100T2_03);
				cal_end = tools.covertStringToCalendar(EHF310100T2_04);
				if(cal_start.after(cal_end)){
					lActionErrors.add("EHF310100T2_03",new ActionMessage("開始日期不可在結束日期之後，請確認！"));
					request.setAttribute("ERR_MSG_DETAIL_DRINK","開始日期不可在結束日期之後，請確認！");
					
				}
			}catch(Exception e){
				System.out.println("檢核新增養生飲品明細日期條件時錯誤");	
			}
			
			
			//檢核資料有無重複
			try{
				String sql = "";
				Statement stmt = conn.createStatement();
				ResultSet rs = null;
				if(cal_start!=null && cal_end!=null){
					
					for(int i = 0;i<drinkList.size();i++){
						sql="";
						if("".equals(EHF310100T2_06_total[i]) || EHF310100T2_06_total[i]==null){
							//代表此選項未勾選
						}else{
							//代表此選項有勾選，需檢核是否有重複
							
							sql += " SELECT *, " +
								   " DATE_FORMAT(EHF310100T2_03,	'%Y/%m/%d') AS EHF310100T2_03, " +
								   " DATE_FORMAT(EHF310100T2_04,	'%Y/%m/%d') AS EHF310100T2_04 " +
								   " FROM EHF310100T2 " +
							   	   " WHERE 1=1 " +
							   	   " AND EHF310100T2_01 = '"+EHF310100T0_01+"' " +	//系統代碼
							   	   " AND (('"+tools.covertDateToString(cal_start.getTime(), "yyyy/MM/dd")+"' BETWEEN EHF310100T2_03 AND EHF310100T2_04 " +
							   	   "      AND '"+tools.covertDateToString(cal_end.getTime(), "yyyy/MM/dd")+"' BETWEEN EHF310100T2_03 AND EHF310100T2_04 )" +
							   	   "  OR (EHF310100T2_03 BETWEEN '"+tools.covertDateToString(cal_start.getTime(), "yyyy/MM/dd")+"' AND '"+tools.covertDateToString(cal_end.getTime(), "yyyy/MM/dd")+"' " +
							   	   "      OR EHF310100T2_04 BETWEEN '"+tools.covertDateToString(cal_start.getTime(), "yyyy/MM/dd")+"' AND '"+tools.covertDateToString(cal_end.getTime(), "yyyy/MM/dd")+"')) " +	
							   	   " AND EHF310100T2_05 = '"+EHF310100T2_05+"' " +//餐別
							   	   " AND EHF310100T2_06='"+EHF310100T2_06_total[i]+"' " +	
							   	   " AND EHF310100T2_07 = '"+request.getAttribute("compid")+"' " ;  //公司代碼
								   
							
							rs = stmt.executeQuery(sql);
							
							if(rs.next()){
								lActionErrors.add("EHF310100T2_06_total",new ActionMessage(""));
								request.setAttribute("ERR_MSG_DETAIL_DRINK","資料重複，請確認！！");
							}
						}
					}
					
				}
				
				rs.close();
				stmt.close();
		
			}catch(Exception e){
				System.out.println("檢核新增養生飲品明細資料重複時錯誤");	
			}
			
		}
		
		setTabsutil_ORD("yes");
		
		
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("utf-8");
			setEHF310100T1_List(ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new EHF310100M0F();
				            }
			 }));
		} catch (Exception e) {
		}
		
		try {
			request.setCharacterEncoding("utf-8");
			setEHF310300T0_List(ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new EHF310100M0F();
				            }
			 }));
		} catch (Exception e) {
		}
		
		try {
			request.setCharacterEncoding("utf-8");
			setEHF310400T1_List(ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new EHF310100M0F();
				            }
			 }));
		} catch (Exception e) {
		}


	}

	public List getEHF310100T1_List() {
		return EHF310100T1_List;
	}



	public void setEHF310100T1_List(List eHF310100T1List) {
		EHF310100T1_List = eHF310100T1List;
	}



	public String getEHF310100T0_01() {
		return EHF310100T0_01;
	}



	public void setEHF310100T0_01(String eHF310100T0_01) {
		EHF310100T0_01 = eHF310100T0_01;
	}



	public String getEHF310100T0_02() {
		return EHF310100T0_02;
	}



	public void setEHF310100T0_02(String eHF310100T0_02) {
		EHF310100T0_02 = eHF310100T0_02;
	}



	public String getEHF310100T0_03() {
		return EHF310100T0_03;
	}



	public void setEHF310100T0_03(String eHF310100T0_03) {
		EHF310100T0_03 = eHF310100T0_03;
	}



	public String getEHF310100T0_04() {
		return EHF310100T0_04;
	}



	public void setEHF310100T0_04(String eHF310100T0_04) {
		EHF310100T0_04 = eHF310100T0_04;
	}



	public String getEHF310100T0_05() {
		return EHF310100T0_05;
	}



	public void setEHF310100T0_05(String eHF310100T0_05) {
		EHF310100T0_05 = eHF310100T0_05;
	}



	public String getEHF310100T0_06() {
		return EHF310100T0_06;
	}



	public void setEHF310100T0_06(String eHF310100T0_06) {
		EHF310100T0_06 = eHF310100T0_06;
	}



	public String getEHF310100T0_07() {
		return EHF310100T0_07;
	}



	public void setEHF310100T0_07(String eHF310100T0_07) {
		EHF310100T0_07 = eHF310100T0_07;
	}



	public String getEHF310100T0_08() {
		return EHF310100T0_08;
	}



	public void setEHF310100T0_08(String eHF310100T0_08) {
		EHF310100T0_08 = eHF310100T0_08;
	}



	public String getEHF310100T0_08_B() {
		return EHF310100T0_08_B;
	}



	public void setEHF310100T0_08_B(String eHF310100T0_08B) {
		EHF310100T0_08_B = eHF310100T0_08B;
	}



	public String getEHF310100T0_08_E() {
		return EHF310100T0_08_E;
	}



	public void setEHF310100T0_08_E(String eHF310100T0_08E) {
		EHF310100T0_08_E = eHF310100T0_08E;
	}



	public int getEHF310100T0_09() {
		return EHF310100T0_09;
	}



	public void setEHF310100T0_09(int eHF310100T0_09) {
		EHF310100T0_09 = eHF310100T0_09;
	}



	public String getEHF310100T0_10() {
		return EHF310100T0_10;
	}



	public void setEHF310100T0_10(String eHF310100T0_10) {
		EHF310100T0_10 = eHF310100T0_10;
	}



	public String getEHF310100T0_11() {
		return EHF310100T0_11;
	}



	public void setEHF310100T0_11(String eHF310100T0_11) {
		EHF310100T0_11 = eHF310100T0_11;
	}



	public String getEHF310100T0_11_TXT() {
		return EHF310100T0_11_TXT;
	}



	public void setEHF310100T0_11_TXT(String eHF310100T0_11TXT) {
		EHF310100T0_11_TXT = eHF310100T0_11TXT;
	}



	public int getEHF310100T0_12() {
		return EHF310100T0_12;
	}



	public void setEHF310100T0_12(int eHF310100T0_12) {
		EHF310100T0_12 = eHF310100T0_12;
	}



	public String getEHF310100T0_13() {
		return EHF310100T0_13;
	}



	public void setEHF310100T0_13(String eHF310100T0_13) {
		EHF310100T0_13 = eHF310100T0_13;
	}



	public String getEHF310100T0_14() {
		return EHF310100T0_14;
	}



	public void setEHF310100T0_14(String eHF310100T0_14) {
		EHF310100T0_14 = eHF310100T0_14;
	}



	public String getEHF310100T0_15() {
		return EHF310100T0_15;
	}



	public void setEHF310100T0_15(String eHF310100T0_15) {
		EHF310100T0_15 = eHF310100T0_15;
	}



	public String getEHF310100T0_16() {
		return EHF310100T0_16;
	}



	public void setEHF310100T0_16(String eHF310100T0_16) {
		EHF310100T0_16 = eHF310100T0_16;
	}



	public String getEHF310100T0_17() {
		return EHF310100T0_17;
	}



	public void setEHF310100T0_17(String eHF310100T0_17) {
		EHF310100T0_17 = eHF310100T0_17;
	}



	public String getEHF310100T0_17_TXT() {
		return EHF310100T0_17_TXT;
	}



	public void setEHF310100T0_17_TXT(String eHF310100T0_17TXT) {
		EHF310100T0_17_TXT = eHF310100T0_17TXT;
	}



	public int getEHF310100T0_18() {
		return EHF310100T0_18;
	}



	public void setEHF310100T0_18(int eHF310100T0_18) {
		EHF310100T0_18 = eHF310100T0_18;
	}



	public String getEHF310100T0_19() {
		return EHF310100T0_19;
	}



	public void setEHF310100T0_19(String eHF310100T0_19) {
		EHF310100T0_19 = eHF310100T0_19;
	}



	public String getEHF310100T0_20() {
		return EHF310100T0_20;
	}



	public void setEHF310100T0_20(String eHF310100T0_20) {
		EHF310100T0_20 = eHF310100T0_20;
	}



	public String getEHF310100T0_21() {
		return EHF310100T0_21;
	}



	public void setEHF310100T0_21(String eHF310100T0_21) {
		EHF310100T0_21 = eHF310100T0_21;
	}



	public String getEHF310100T0_22() {
		return EHF310100T0_22;
	}



	public void setEHF310100T0_22(String eHF310100T0_22) {
		EHF310100T0_22 = eHF310100T0_22;
	}



	public String getEHF310100T0_23() {
		return EHF310100T0_23;
	}



	public void setEHF310100T0_23(String eHF310100T0_23) {
		EHF310100T0_23 = eHF310100T0_23;
	}



	public String getEHF310100T0_24() {
		return EHF310100T0_24;
	}



	public void setEHF310100T0_24(String eHF310100T0_24) {
		EHF310100T0_24 = eHF310100T0_24;
	}



	public String getEHF310100T0_25() {
		return EHF310100T0_25;
	}



	public void setEHF310100T0_25(String eHF310100T0_25) {
		EHF310100T0_25 = eHF310100T0_25;
	}



	public String getEHF310100T0_26() {
		return EHF310100T0_26;
	}



	public void setEHF310100T0_26(String eHF310100T0_26) {
		EHF310100T0_26 = eHF310100T0_26;
	}



	public String getEHF310100T0_27() {
		return EHF310100T0_27;
	}



	public void setEHF310100T0_27(String eHF310100T0_27) {
		EHF310100T0_27 = eHF310100T0_27;
	}



	public String getEHF310100T0_28() {
		return EHF310100T0_28;
	}



	public void setEHF310100T0_28(String eHF310100T0_28) {
		EHF310100T0_28 = eHF310100T0_28;
	}



	public String getEHF310100T0_29() {
		return EHF310100T0_29;
	}



	public void setEHF310100T0_29(String eHF310100T0_29) {
		EHF310100T0_29 = eHF310100T0_29;
	}



	public String getEHF310100T0_30() {
		return EHF310100T0_30;
	}



	public void setEHF310100T0_30(String eHF310100T0_30) {
		EHF310100T0_30 = eHF310100T0_30;
	}



	public String getEHF310100T0_31() {
		return EHF310100T0_31;
	}



	public void setEHF310100T0_31(String eHF310100T0_31) {
		EHF310100T0_31 = eHF310100T0_31;
	}



	public String getEHF310100T0_32() {
		return EHF310100T0_32;
	}



	public void setEHF310100T0_32(String eHF310100T0_32) {
		EHF310100T0_32 = eHF310100T0_32;
	}



	public String getEHF310100T0_33() {
		return EHF310100T0_33;
	}



	public void setEHF310100T0_33(String eHF310100T0_33) {
		EHF310100T0_33 = eHF310100T0_33;
	}



	public String getEHF310100T0_34() {
		return EHF310100T0_34;
	}



	public void setEHF310100T0_34(String eHF310100T0_34) {
		EHF310100T0_34 = eHF310100T0_34;
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



	public List getEHF310100_C() {
		return EHF310100_C;
	}



	public void setEHF310100_C(List eHF310100C) {
		EHF310100_C = eHF310100C;
	}



	public String getEHF310100T1_01() {
		return EHF310100T1_01;
	}



	public void setEHF310100T1_01(String eHF310100T1_01) {
		EHF310100T1_01 = eHF310100T1_01;
	}



	public String getEHF310100T1_02() {
		return EHF310100T1_02;
	}



	public void setEHF310100T1_02(String eHF310100T1_02) {
		EHF310100T1_02 = eHF310100T1_02;
	}



	public String getEHF310100T1_03() {
		return EHF310100T1_03;
	}



	public void setEHF310100T1_03(String eHF310100T1_03) {
		EHF310100T1_03 = eHF310100T1_03;
	}



	public String getEHF310100T1_04() {
		return EHF310100T1_04;
	}



	public void setEHF310100T1_04(String eHF310100T1_04) {
		EHF310100T1_04 = eHF310100T1_04;
	}



	public String getEHF310100T1_05() {
		return EHF310100T1_05;
	}



	public void setEHF310100T1_05(String eHF310100T1_05) {
		EHF310100T1_05 = eHF310100T1_05;
	}



	public String getEHF310100T1_03_A() {
		return EHF310100T1_03_A;
	}



	public void setEHF310100T1_03_A(String eHF310100T1_03A) {
		EHF310100T1_03_A = eHF310100T1_03A;
	}



	public String getEHF310100T1_04_A() {
		return EHF310100T1_04_A;
	}



	public void setEHF310100T1_04_A(String eHF310100T1_04A) {
		EHF310100T1_04_A = eHF310100T1_04A;
	}



	public String getEHF310100T1_05_A() {
		return EHF310100T1_05_A;
	}



	public void setEHF310100T1_05_A(String eHF310100T1_05A) {
		EHF310100T1_05_A = eHF310100T1_05A;
	}



	public String getEHF310100T1_03_B() {
		return EHF310100T1_03_B;
	}



	public void setEHF310100T1_03_B(String eHF310100T1_03B) {
		EHF310100T1_03_B = eHF310100T1_03B;
	}



	public String getEHF310100T1_04_B() {
		return EHF310100T1_04_B;
	}



	public void setEHF310100T1_04_B(String eHF310100T1_04B) {
		EHF310100T1_04_B = eHF310100T1_04B;
	}



	public String getEHF310100T1_05_B() {
		return EHF310100T1_05_B;
	}



	public void setEHF310100T1_05_B(String eHF310100T1_05B) {
		EHF310100T1_05_B = eHF310100T1_05B;
	}



	public String getEHF310100T1_03_C() {
		return EHF310100T1_03_C;
	}



	public void setEHF310100T1_03_C(String eHF310100T1_03C) {
		EHF310100T1_03_C = eHF310100T1_03C;
	}



	public String getEHF310100T1_04_C() {
		return EHF310100T1_04_C;
	}



	public void setEHF310100T1_04_C(String eHF310100T1_04C) {
		EHF310100T1_04_C = eHF310100T1_04C;
	}



	public String getEHF310100T1_05_C() {
		return EHF310100T1_05_C;
	}



	public void setEHF310100T1_05_C(String eHF310100T1_05C) {
		EHF310100T1_05_C = eHF310100T1_05C;
	}



	public String getEHF310100T1_06() {
		return EHF310100T1_06;
	}



	public void setEHF310100T1_06(String eHF310100T1_06) {
		EHF310100T1_06 = eHF310100T1_06;
	}



	public String getEHF310100T2_01() {
		return EHF310100T2_01;
	}



	public void setEHF310100T2_01(String eHF310100T2_01) {
		EHF310100T2_01 = eHF310100T2_01;
	}



	public String getEHF310100T2_02() {
		return EHF310100T2_02;
	}



	public void setEHF310100T2_02(String eHF310100T2_02) {
		EHF310100T2_02 = eHF310100T2_02;
	}



	public String getEHF310100T2_03() {
		return EHF310100T2_03;
	}



	public void setEHF310100T2_03(String eHF310100T2_03) {
		EHF310100T2_03 = eHF310100T2_03;
	}



	public String getEHF310100T2_04() {
		return EHF310100T2_04;
	}



	public void setEHF310100T2_04(String eHF310100T2_04) {
		EHF310100T2_04 = eHF310100T2_04;
	}



	public String getEHF310100T2_05() {
		return EHF310100T2_05;
	}



	public void setEHF310100T2_05(String eHF310100T2_05) {
		EHF310100T2_05 = eHF310100T2_05;
	}



	public String getEHF310100T2_06() {
		return EHF310100T2_06;
	}



	public void setEHF310100T2_06(String eHF310100T2_06) {
		EHF310100T2_06 = eHF310100T2_06;
	}



	public String getEHF310100T2_07() {
		return EHF310100T2_07;
	}



	public void setEHF310100T2_07(String eHF310100T2_07) {
		EHF310100T2_07 = eHF310100T2_07;
	}



	public List getEHF310100T2_List() {
		return EHF310100T2_List;
	}



	public String getEHF310200T0_01() {
		return EHF310200T0_01;
	}



	public void setEHF310200T0_01(String eHF310200T0_01) {
		EHF310200T0_01 = eHF310200T0_01;
	}



	public String getEHF310200T0_02() {
		return EHF310200T0_02;
	}



	public void setEHF310200T0_02(String eHF310200T0_02) {
		EHF310200T0_02 = eHF310200T0_02;
	}



	public String getEHF310200T0_03() {
		return EHF310200T0_03;
	}



	public void setEHF310200T0_03(String eHF310200T0_03) {
		EHF310200T0_03 = eHF310200T0_03;
	}



	public String getEHF310200T1_01() {
		return EHF310200T1_01;
	}



	public void setEHF310200T1_01(String eHF310200T1_01) {
		EHF310200T1_01 = eHF310200T1_01;
	}



	public String getEHF310200T1_02() {
		return EHF310200T1_02;
	}



	public void setEHF310200T1_02(String eHF310200T1_02) {
		EHF310200T1_02 = eHF310200T1_02;
	}



	public String getEHF310200T1_03() {
		return EHF310200T1_03;
	}



	public void setEHF310200T1_03(String eHF310200T1_03) {
		EHF310200T1_03 = eHF310200T1_03;
	}



	public String getEHF310200T2_01() {
		return EHF310200T2_01;
	}



	public void setEHF310200T2_01(String eHF310200T2_01) {
		EHF310200T2_01 = eHF310200T2_01;
	}



	public String getEHF310200T2_02() {
		return EHF310200T2_02;
	}



	public void setEHF310200T2_02(String eHF310200T2_02) {
		EHF310200T2_02 = eHF310200T2_02;
	}



	public String getEHF310200T2_03() {
		return EHF310200T2_03;
	}



	public void setEHF310200T2_03(String eHF310200T2_03) {
		EHF310200T2_03 = eHF310200T2_03;
	}



	public String getEHF310200T3_01() {
		return EHF310200T3_01;
	}



	public void setEHF310200T3_01(String eHF310200T3_01) {
		EHF310200T3_01 = eHF310200T3_01;
	}



	public String getEHF310200T3_02() {
		return EHF310200T3_02;
	}



	public void setEHF310200T3_02(String eHF310200T3_02) {
		EHF310200T3_02 = eHF310200T3_02;
	}



	public String getEHF310200T3_03() {
		return EHF310200T3_03;
	}



	public void setEHF310200T3_03(String eHF310200T3_03) {
		EHF310200T3_03 = eHF310200T3_03;
	}



	public String getEHF310200T4_01() {
		return EHF310200T4_01;
	}



	public void setEHF310200T4_01(String eHF310200T4_01) {
		EHF310200T4_01 = eHF310200T4_01;
	}



	public String getEHF310200T4_02() {
		return EHF310200T4_02;
	}



	public void setEHF310200T4_02(String eHF310200T4_02) {
		EHF310200T4_02 = eHF310200T4_02;
	}



	public String getEHF310200T4_03() {
		return EHF310200T4_03;
	}



	public void setEHF310200T4_03(String eHF310200T4_03) {
		EHF310200T4_03 = eHF310200T4_03;
	}



	public String getEHF310200T5_01() {
		return EHF310200T5_01;
	}



	public void setEHF310200T5_01(String eHF310200T5_01) {
		EHF310200T5_01 = eHF310200T5_01;
	}



	public String getEHF310200T5_02() {
		return EHF310200T5_02;
	}



	public void setEHF310200T5_02(String eHF310200T5_02) {
		EHF310200T5_02 = eHF310200T5_02;
	}



	public String getEHF310200T5_03() {
		return EHF310200T5_03;
	}



	public void setEHF310200T5_03(String eHF310200T5_03) {
		EHF310200T5_03 = eHF310200T5_03;
	}



	public void setEHF310100T2_List(List eHF310100T2List) {
		EHF310100T2_List = eHF310100T2List;
	}



	public String getEHF310300T0_01() {
		return EHF310300T0_01;
	}



	public void setEHF310300T0_01(String eHF310300T0_01) {
		EHF310300T0_01 = eHF310300T0_01;
	}



	public String getEHF310300T0_02() {
		return EHF310300T0_02;
	}



	public void setEHF310300T0_02(String eHF310300T0_02) {
		EHF310300T0_02 = eHF310300T0_02;
	}



	public String getEHF310300T0_03() {
		return EHF310300T0_03;
	}



	public void setEHF310300T0_03(String eHF310300T0_03) {
		EHF310300T0_03 = eHF310300T0_03;
	}



	public String getEHF310300T0_04() {
		return EHF310300T0_04;
	}



	public void setEHF310300T0_04(String eHF310300T0_04) {
		EHF310300T0_04 = eHF310300T0_04;
	}



	public String getEHF310300T0_05() {
		return EHF310300T0_05;
	}



	public void setEHF310300T0_05(String eHF310300T0_05) {
		EHF310300T0_05 = eHF310300T0_05;
	}



	public String getEHF310300T0_06() {
		return EHF310300T0_06;
	}



	public void setEHF310300T0_06(String eHF310300T0_06) {
		EHF310300T0_06 = eHF310300T0_06;
	}



	public String getEHF310300T0_07() {
		return EHF310300T0_07;
	}



	public void setEHF310300T0_07(String eHF310300T0_07) {
		EHF310300T0_07 = eHF310300T0_07;
	}



	public String getEHF310400T0_01() {
		return EHF310400T0_01;
	}



	public void setEHF310400T0_01(String eHF310400T0_01) {
		EHF310400T0_01 = eHF310400T0_01;
	}



	public int getEHF310400T0_02() {
		return EHF310400T0_02;
	}



	public void setEHF310400T0_02(int eHF310400T0_02) {
		EHF310400T0_02 = eHF310400T0_02;
	}



	public int getEHF310400T0_03() {
		return EHF310400T0_03;
	}



	public void setEHF310400T0_03(int eHF310400T0_03) {
		EHF310400T0_03 = eHF310400T0_03;
	}



	public int getEHF310400T0_04() {
		return EHF310400T0_04;
	}



	public void setEHF310400T0_04(int eHF310400T0_04) {
		EHF310400T0_04 = eHF310400T0_04;
	}



	public int getEHF310400T0_05() {
		return EHF310400T0_05;
	}



	public void setEHF310400T0_05(int eHF310400T0_05) {
		EHF310400T0_05 = eHF310400T0_05;
	}



	public String getEHF310400T0_06() {
		return EHF310400T0_06;
	}



	public void setEHF310400T0_06(String eHF310400T0_06) {
		EHF310400T0_06 = eHF310400T0_06;
	}



	public String getEHF310400T2_01() {
		return EHF310400T2_01;
	}



	public void setEHF310400T2_01(String eHF310400T2_01) {
		EHF310400T2_01 = eHF310400T2_01;
	}



	public String getEHF310400T2_02() {
		return EHF310400T2_02;
	}



	public void setEHF310400T2_02(String eHF310400T2_02) {
		EHF310400T2_02 = eHF310400T2_02;
	}



	public int getEHF310400T2_03() {
		return EHF310400T2_03;
	}



	public void setEHF310400T2_03(int eHF310400T2_03) {
		EHF310400T2_03 = eHF310400T2_03;
	}



	public String getEHF310400T1_01() {
		return EHF310400T1_01;
	}



	public void setEHF310400T1_01(String eHF310400T1_01) {
		EHF310400T1_01 = eHF310400T1_01;
	}



	public int getEHF310400T1_02() {
		return EHF310400T1_02;
	}



	public void setEHF310400T1_02(int eHF310400T1_02) {
		EHF310400T1_02 = eHF310400T1_02;
	}



	public String getEHF310400T1_03() {
		return EHF310400T1_03;
	}



	public void setEHF310400T1_03(String eHF310400T1_03) {
		EHF310400T1_03 = eHF310400T1_03;
	}



	public String getEHF310400T1_04() {
		return EHF310400T1_04;
	}



	public void setEHF310400T1_04(String eHF310400T1_04) {
		EHF310400T1_04 = eHF310400T1_04;
	}



	public String getEHF310400T1_05() {
		return EHF310400T1_05;
	}



	public void setEHF310400T1_05(String eHF310400T1_05) {
		EHF310400T1_05 = eHF310400T1_05;
	}



	public String getEHF310400T1_06() {
		return EHF310400T1_06;
	}



	public void setEHF310400T1_06(String eHF310400T1_06) {
		EHF310400T1_06 = eHF310400T1_06;
	}



	public String getEHF310400T1_07() {
		return EHF310400T1_07;
	}



	public void setEHF310400T1_07(String eHF310400T1_07) {
		EHF310400T1_07 = eHF310400T1_07;
	}



	public String getEHF310400T1_08() {
		return EHF310400T1_08;
	}



	public void setEHF310400T1_08(String eHF310400T1_08) {
		EHF310400T1_08 = eHF310400T1_08;
	}



	public int getEHF310400T1_09() {
		return EHF310400T1_09;
	}



	public void setEHF310400T1_09(int eHF310400T1_09) {
		EHF310400T1_09 = eHF310400T1_09;
	}



	public String getEHF310400T1_10() {
		return EHF310400T1_10;
	}



	public void setEHF310400T1_10(String eHF310400T1_10) {
		EHF310400T1_10 = eHF310400T1_10;
	}



	public int getEHF310400T1_11() {
		return EHF310400T1_11;
	}



	public void setEHF310400T1_11(int eHF310400T1_11) {
		EHF310400T1_11 = eHF310400T1_11;
	}



	public String getEHF310400T1_12() {
		return EHF310400T1_12;
	}



	public void setEHF310400T1_12(String eHF310400T1_12) {
		EHF310400T1_12 = eHF310400T1_12;
	}



	public String getEHF310400T1_13() {
		return EHF310400T1_13;
	}



	public void setEHF310400T1_13(String eHF310400T1_13) {
		EHF310400T1_13 = eHF310400T1_13;
	}



	public String getEHF310400T1_14() {
		return EHF310400T1_14;
	}



	public void setEHF310400T1_14(String eHF310400T1_14) {
		EHF310400T1_14 = eHF310400T1_14;
	}



	public String getEHF310400T1_15() {
		return EHF310400T1_15;
	}



	public void setEHF310400T1_15(String eHF310400T1_15) {
		EHF310400T1_15 = eHF310400T1_15;
	}



	public String getEHF310500T0_01() {
		return EHF310500T0_01;
	}



	public void setEHF310500T0_01(String eHF310500T0_01) {
		EHF310500T0_01 = eHF310500T0_01;
	}



	public int getEHF310500T0_02() {
		return EHF310500T0_02;
	}



	public void setEHF310500T0_02(int eHF310500T0_02) {
		EHF310500T0_02 = eHF310500T0_02;
	}



	public String getEHF310500T0_03() {
		return EHF310500T0_03;
	}



	public void setEHF310500T0_03(String eHF310500T0_03) {
		EHF310500T0_03 = eHF310500T0_03;
	}



	public String getEHF310500T0_04() {
		return EHF310500T0_04;
	}



	public void setEHF310500T0_04(String eHF310500T0_04) {
		EHF310500T0_04 = eHF310500T0_04;
	}



	public String getEHF310500T0_05() {
		return EHF310500T0_05;
	}



	public void setEHF310500T0_05(String eHF310500T0_05) {
		EHF310500T0_05 = eHF310500T0_05;
	}



	public String getEHF310500T0_06() {
		return EHF310500T0_06;
	}



	public void setEHF310500T0_06(String eHF310500T0_06) {
		EHF310500T0_06 = eHF310500T0_06;
	}



	public String getEHF310500T0_07() {
		return EHF310500T0_07;
	}



	public void setEHF310500T0_07(String eHF310500T0_07) {
		EHF310500T0_07 = eHF310500T0_07;
	}



	public String getEHF310500T0_08() {
		return EHF310500T0_08;
	}



	public void setEHF310500T0_08(String eHF310500T0_08) {
		EHF310500T0_08 = eHF310500T0_08;
	}



	public String getEHF310500T0_09() {
		return EHF310500T0_09;
	}



	public void setEHF310500T0_09(String eHF310500T0_09) {
		EHF310500T0_09 = eHF310500T0_09;
	}



	public String getTabsutil_CUS() {
		return tabsutil_CUS;
	}



	public void setTabsutil_CUS(String tabsutilCUS) {
		tabsutil_CUS = tabsutilCUS;
	}



	public String getTabsutil_ORD() {
		return tabsutil_ORD;
	}



	public void setTabsutil_ORD(String tabsutilORD) {
		tabsutil_ORD = tabsutilORD;
	}



	public String getTabsutil_SPE() {
		return tabsutil_SPE;
	}



	public void setTabsutil_SPE(String tabsutilSPE) {
		tabsutil_SPE = tabsutilSPE;
	}



	public String getTabsutil_CALL() {
		return tabsutil_CALL;
	}



	public void setTabsutil_CALL(String tabsutilCALL) {
		tabsutil_CALL = tabsutilCALL;
	}



	public String getTabsutil_PAY() {
		return tabsutil_PAY;
	}



	public void setTabsutil_PAY(String tabsutilPAY) {
		tabsutil_PAY = tabsutilPAY;
	}



	public String getTabsutil_GIFT() {
		return tabsutil_GIFT;
	}



	public void setTabsutil_GIFT(String tabsutilGIFT) {
		tabsutil_GIFT = tabsutilGIFT;
	}



	public String getEHF310200T1_03_type() {
		return EHF310200T1_03_type;
	}



	public void setEHF310200T1_03_type(String eHF310200T1_03Type) {
		EHF310200T1_03_type = eHF310200T1_03Type;
	}



	public String getEHF310200T1_03_detail() {
		return EHF310200T1_03_detail;
	}



	public void setEHF310200T1_03_detail(String eHF310200T1_03Detail) {
		EHF310200T1_03_detail = eHF310200T1_03Detail;
	}



	public List getEHF310200T1_List() {
		return EHF310200T1_List;
	}



	public void setEHF310200T1_List(List eHF310200T1List) {
		EHF310200T1_List = eHF310200T1List;
	}



	public List getEHF310200T2_List() {
		return EHF310200T2_List;
	}



	public void setEHF310200T2_List(List eHF310200T2List) {
		EHF310200T2_List = eHF310200T2List;
	}



	public List getEHF310200T3_List() {
		return EHF310200T3_List;
	}



	public void setEHF310200T3_List(List eHF310200T3List) {
		EHF310200T3_List = eHF310200T3List;
	}



	public List getEHF310200T4_List() {
		return EHF310200T4_List;
	}



	public void setEHF310200T4_List(List eHF310200T4List) {
		EHF310200T4_List = eHF310200T4List;
	}



	public List getEHF310200T5_List() {
		return EHF310200T5_List;
	}



	public void setEHF310200T5_List(List eHF310200T5List) {
		EHF310200T5_List = eHF310200T5List;
	}



	public List getEHF310300T0_List() {
		return EHF310300T0_List;
	}



	public void setEHF310300T0_List(List eHF310300T0List) {
		EHF310300T0_List = eHF310300T0List;
	}



//	public List getEHF310100T2_06_total() {
//		return EHF310100T2_06_total;
//	}
//
//
//
//	public void setEHF310100T2_06_total(List eHF310100T2_06Total) {
//		EHF310100T2_06_total = eHF310100T2_06Total;
//	}



	public String[] getEHF310100T2_06_total() {
		return EHF310100T2_06_total;
	}



	public void setEHF310100T2_06_total(String[] eHF310100T2_06Total) {
		EHF310100T2_06_total = eHF310100T2_06Total;
	}



	public String[] getEHF310100T1_03_total() {
		return EHF310100T1_03_total;
	}



	public void setEHF310100T1_03_total(String[] eHF310100T1_03Total) {
		EHF310100T1_03_total = eHF310100T1_03Total;
	}



	public String[] getEHF310100T1_04_total() {
		return EHF310100T1_04_total;
	}



	public void setEHF310100T1_04_total(String[] eHF310100T1_04Total) {
		EHF310100T1_04_total = eHF310100T1_04Total;
	}



	public String[] getEHF310100T1_05_total() {
		return EHF310100T1_05_total;
	}



	public void setEHF310100T1_05_total(String[] eHF310100T1_05Total) {
		EHF310100T1_05_total = eHF310100T1_05Total;
	}



	public String getEHF310100T1_02_B() {
		return EHF310100T1_02_B;
	}



	public void setEHF310100T1_02_B(String eHF310100T1_02B) {
		EHF310100T1_02_B = eHF310100T1_02B;
	}



	public String getEHF310100T1_02_E() {
		return EHF310100T1_02_E;
	}



	public void setEHF310100T1_02_E(String eHF310100T1_02E) {
		EHF310100T1_02_E = eHF310100T1_02E;
	}

	public String getEHF310200T1_03_type_TXT() {
		return EHF310200T1_03_type_TXT;
	}

	public void setEHF310200T1_03_type_TXT(String eHF310200T1_03TypeTXT) {
		EHF310200T1_03_type_TXT = eHF310200T1_03TypeTXT;
	}

	public String getEHF310200T1_03_detail_TXT() {
		return EHF310200T1_03_detail_TXT;
	}

	public void setEHF310200T1_03_detail_TXT(String eHF310200T1_03DetailTXT) {
		EHF310200T1_03_detail_TXT = eHF310200T1_03DetailTXT;
	}

	public String getEHF310200T2_03_TXT() {
		return EHF310200T2_03_TXT;
	}

	public void setEHF310200T2_03_TXT(String eHF310200T2_03TXT) {
		EHF310200T2_03_TXT = eHF310200T2_03TXT;
	}

	public String getEHF310200T2_03_SHOW() {
		return EHF310200T2_03_SHOW;
	}

	public void setEHF310200T2_03_SHOW(String eHF310200T2_03SHOW) {
		EHF310200T2_03_SHOW = eHF310200T2_03SHOW;
	}

	public String getEHF310300T0_03_TXT() {
		return EHF310300T0_03_TXT;
	}

	public void setEHF310300T0_03_TXT(String eHF310300T0_03TXT) {
		EHF310300T0_03_TXT = eHF310300T0_03TXT;
	}

	public String getEHF310300T0_03_SHOW() {
		return EHF310300T0_03_SHOW;
	}

	public void setEHF310300T0_03_SHOW(String eHF310300T0_03SHOW) {
		EHF310300T0_03_SHOW = eHF310300T0_03SHOW;
	}
	/*
	public String getEHF310500T0_02_SHOW() {
		return EHF310500T0_02_SHOW;
	}

	public void setEHF310500T0_02_SHOW(String eHF310500T0_02SHOW) {
		EHF310500T0_02_SHOW = eHF310500T0_02SHOW;
	}

	public String getEHF310500T0_02_TXT() {
		return EHF310500T0_02_TXT;
	}

	public void setEHF310500T0_02_TXT(String eHF310500T0_02TXT) {
		EHF310500T0_02_TXT = eHF310500T0_02TXT;
	}
	*/
	public String getEHF310400T2_02_All() {
		return EHF310400T2_02_All;
	}

	public void setEHF310400T2_02_All(String eHF310400T2_02All) {
		EHF310400T2_02_All = eHF310400T2_02All;
	}

	public String getEHF310400T2_02_A() {
		return EHF310400T2_02_A;
	}

	public void setEHF310400T2_02_A(String eHF310400T2_02A) {
		EHF310400T2_02_A = eHF310400T2_02A;
	}

	public String getEHF310400T2_02_B() {
		return EHF310400T2_02_B;
	}

	public void setEHF310400T2_02_B(String eHF310400T2_02B) {
		EHF310400T2_02_B = eHF310400T2_02B;
	}

	public String getEHF310400T2_02_C() {
		return EHF310400T2_02_C;
	}

	public void setEHF310400T2_02_C(String eHF310400T2_02C) {
		EHF310400T2_02_C = eHF310400T2_02C;
	}

	public int getEHF310400T2_03_All() {
		return EHF310400T2_03_All;
	}

	public void setEHF310400T2_03_All(int eHF310400T2_03All) {
		EHF310400T2_03_All = eHF310400T2_03All;
	}

	public int getEHF310400T2_03_A() {
		return EHF310400T2_03_A;
	}

	public void setEHF310400T2_03_A(int eHF310400T2_03A) {
		EHF310400T2_03_A = eHF310400T2_03A;
	}

	public int getEHF310400T2_03_B() {
		return EHF310400T2_03_B;
	}

	public void setEHF310400T2_03_B(int eHF310400T2_03B) {
		EHF310400T2_03_B = eHF310400T2_03B;
	}

	public int getEHF310400T2_03_C() {
		return EHF310400T2_03_C;
	}

	public void setEHF310400T2_03_C(int eHF310400T2_03C) {
		EHF310400T2_03_C = eHF310400T2_03C;
	}

	public int getEHF310400T2_03_All_Pay() {
		return EHF310400T2_03_All_Pay;
	}

	public void setEHF310400T2_03_All_Pay(int eHF310400T2_03AllPay) {
		EHF310400T2_03_All_Pay = eHF310400T2_03AllPay;
	}

	public int getEHF310400T2_03_A_Pay() {
		return EHF310400T2_03_A_Pay;
	}

	public void setEHF310400T2_03_A_Pay(int eHF310400T2_03APay) {
		EHF310400T2_03_A_Pay = eHF310400T2_03APay;
	}

	public int getEHF310400T2_03_B_Pay() {
		return EHF310400T2_03_B_Pay;
	}

	public void setEHF310400T2_03_B_Pay(int eHF310400T2_03BPay) {
		EHF310400T2_03_B_Pay = eHF310400T2_03BPay;
	}

	public int getEHF310400T2_03_C_Pay() {
		return EHF310400T2_03_C_Pay;
	}

	public void setEHF310400T2_03_C_Pay(int eHF310400T2_03CPay) {
		EHF310400T2_03_C_Pay = eHF310400T2_03CPay;
	}

	public int getEHF310400T2_finalPay() {
		return EHF310400T2_finalPay;
	}

	public void setEHF310400T2_finalPay(int eHF310400T2FinalPay) {
		EHF310400T2_finalPay = eHF310400T2FinalPay;
	}

	public int getEHF310400T2_discount() {
		return EHF310400T2_discount;
	}

	public void setEHF310400T2_discount(int eHF310400T2Discount) {
		EHF310400T2_discount = eHF310400T2Discount;
	}

	public int getEHF310400T2_realPay() {
		return EHF310400T2_realPay;
	}

	public void setEHF310400T2_realPay(int eHF310400T2RealPay) {
		EHF310400T2_realPay = eHF310400T2RealPay;
	}

	public int getEHF310400T2_unPay() {
		return EHF310400T2_unPay;
	}

	public void setEHF310400T2_unPay(int eHF310400T2UnPay) {
		EHF310400T2_unPay = eHF310400T2UnPay;
	}

	public List getEHF310400T1_List() {
		return EHF310400T1_List;
	}

	public void setEHF310400T1_List(List eHF310400T1List) {
		EHF310400T1_List = eHF310400T1List;
	}

	public String getEHF310400T1_04_SHOW() {
		return EHF310400T1_04_SHOW;
	}

	public void setEHF310400T1_04_SHOW(String eHF310400T1_04SHOW) {
		EHF310400T1_04_SHOW = eHF310400T1_04SHOW;
	}

	public String getEHF310400T1_04_TXT() {
		return EHF310400T1_04_TXT;
	}

	public void setEHF310400T1_04_TXT(String eHF310400T1_04TXT) {
		EHF310400T1_04_TXT = eHF310400T1_04TXT;
	}

	public String getEHF310400T1_05_SHOW() {
		return EHF310400T1_05_SHOW;
	}

	public void setEHF310400T1_05_SHOW(String eHF310400T1_05SHOW) {
		EHF310400T1_05_SHOW = eHF310400T1_05SHOW;
	}

	public String getEHF310400T1_05_TXT() {
		return EHF310400T1_05_TXT;
	}

	public void setEHF310400T1_05_TXT(String eHF310400T1_05TXT) {
		EHF310400T1_05_TXT = eHF310400T1_05TXT;
	}

	public void setEHF310100T0_16_TXT(String eHF310100T0_16_TXT) {
		EHF310100T0_16_TXT = eHF310100T0_16_TXT;
	}

	public String getEHF310100T0_16_TXT() {
		return EHF310100T0_16_TXT;
	}

	public void setEHF310500T0_06_TXT(String eHF310500T0_06_TXT) {
		EHF310500T0_06_TXT = eHF310500T0_06_TXT;
	}

	public String getEHF310500T0_06_TXT() {
		return EHF310500T0_06_TXT;
	}

	public void setEHF310500T0_List(List eHF310500T0_List) {
		EHF310500T0_List = eHF310500T0_List;
	}

	public List getEHF310500T0_List() {
		return EHF310500T0_List;
	}
	
}
