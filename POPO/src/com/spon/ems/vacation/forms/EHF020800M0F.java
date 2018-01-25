package com.spon.ems.vacation.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.ems.vacation.models.EHF020800;
import com.spon.ems.vacation.tools.EMS_AttendanceAction;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;

/**
 * 加班單申請作業 FormBean
 * @author Joe
 *
 */
public class EHF020800M0F extends ActionForm implements ValidateForm {
	
	private static final long serialVersionUID = 1L;
	private boolean CHECKED;
	private boolean CHANGED;
	
	private String EHF020800T0_01;
	private String EHF020800T0_02;
	private String EHF020800T0_03;
	private String EHF020800T0_03_TXT;
	private String EHF020800T0_04;
	private String EHF020800T0_04_TXT;
	private String EHF020800T0_05;
	private String EHF020800T0_06;
	private String EHF020800T0_06_END;
	private String EHF020800T0_07;
	private String EHF020800T0_08;
	private String EHF020800T0_09;
	private String EHF020800T0_09_DESC;
	private String EHF020800T0_10;
	private String EHF020800T0_11;
	private String EHF020800T0_11_TXT;
	
	private String EHF020800T1_01;
	private String EHF020800T1_02;
	private String EHF020800T1_03;
	private String EHF020800T1_04;
	private String EHF020800T1_04_TXT;
	//private String EHF020800T1_05;
	//private String EHF020800T1_05_TXT;
	private String EHF020800T1_06;
	private String EHF020800T1_06_year;
	private String EHF020800T1_06_HH;
	private String EHF020800T1_06_MM;
	private String EHF020800T1_07;
	private String EHF020800T1_07_year;
	private String EHF020800T1_07_HH;
	private String EHF020800T1_07_MM;
	
	private String EHF020800T1_06_BRK;
	private String EHF020800T1_06_BRK_HH;
	private String EHF020800T1_06_BRK_MM;
	private String EHF020800T1_07_BRK;
	private String EHF020800T1_07_BRK_HH;
	private String EHF020800T1_07_BRK_MM;
	
	private String EHF020800T1_08;
	private String EHF020800T1_08_DE;
	
	private String EHF020800T1_09;
	private String EHF020800T1_10;
	private String EHF020800T1_11;
	
	private Float EHF020800T1_12;//中午加班時數
	private String EHF020800T1_13;//加班類別(例假，休假，國定)
	
	private String NOON_LIMITED;//
	
	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_CREATE;
	private String DATE_UPDATE;
	private FormFile up_file1;
	private List EHF020800T0_LIST = new ArrayList();
	private List EHF020800T1_LIST = new ArrayList();
	
	
	private String ERROR;
	
	private String EHF020800T0_03_SHOW;
	private String EHF020800T0_04_SHOW;
	private String EHF020800T0_11_SHOW;
	private String EHF020800T1_04_SHOW;
	
	private FormFile UPLOADFILE;
	
	private String SIGN_COMMENT;
	
	public ActionErrors validate(ActionMapping mapping,	HttpServletRequest request, Connection conn) {
		ActionErrors l_actionErrors = new ActionErrors();
		
		List flow_sign_log_list = new ArrayList();
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		if (l_actionErrors.isEmpty()) {
			
			//新增時判斷條件
			if (request.getAttribute("action").equals("addData")) {
				
				ve.isADDate(l_actionErrors, EHF020800T0_06, "EHF020800T0_06", "日期格式錯誤");  //加班日期
				ve.isEmpty(l_actionErrors, EHF020800T0_11_SHOW, "EHF020800T0_11_SHOW", "不可空白");  
				if(l_actionErrors.isEmpty()){
					addData_validate(l_actionErrors, request, conn);
				}
			}
			
			//新增明細時判斷條件
			if (request.getAttribute("action").equals("addEHF020800T1")) {
				
				//ve.isEmpty(l_actionErrors, EHF020800T1_05, "EHF020800T1_05", "不可空白"); 
				ve.isEmpty(l_actionErrors, EHF020800T1_04, "EHF020800T1_04", "不可空白");  
				ve.isEmpty(l_actionErrors, EHF020800T1_04_SHOW, "EHF020800T1_04_SHOW", "不可空白");  
				ve.isEmpty(l_actionErrors, EHF020800T1_11, "EHF020800T1_11", "不可空白");  
				
				ve.isEmpty(l_actionErrors, EHF020800T1_06_year, "EHF020800T1_06_year", "不可空白");  
				ve.isEmpty(l_actionErrors, EHF020800T1_07_year, "EHF020800T1_07_year", "不可空白");  
				if(l_actionErrors.isEmpty()){
					addDetailData_validate(l_actionErrors, request, conn);
				}
			}

			//修改時判斷條件
			if (request.getAttribute("action").equals("saveData")) {
				
				saveData_validate(l_actionErrors, request, conn);
			}
			
			//刪除時判斷條件
			if (request.getAttribute("action").equals("delData")) {
				
				delData_validate(l_actionErrors, request, conn);
			}
			
		}
		
		//處理因為儲存檢核失敗造成無法儲存的問題
		if(!l_actionErrors.isEmpty()){
			request.setAttribute("personself", "yes");
			request.setAttribute("Form5Datas", flow_sign_log_list);
		}

		return l_actionErrors;
	}
	private void addData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		try{
		/*String sql = "" +
		" SELECT *" +
		" FROM EHF020800T0 " +
		" WHERE 1=1 " +
		" AND EHF020800T0_06 = '"+EHF020800T0_06+"' " +  //加班考勤日期
		" AND EHF020800T0_11 = '"+EHF020800T0_11+"' " +  //加班部門
		" AND EHF020800T0_10 = '"+(String)request.getAttribute("compid")+"' ";  //公司代碼
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			errors.add("EHF020800T0_06",new ActionMessage("加班時間重複，請再次確認!!"));
			errors.add("EHF020800T0_11",new ActionMessage("加班部門重複，請再次確認!!"));
			request.setAttribute("MSG_EDIT","資料重複，請再次確認!!");
		}
		
		rs.close();
		stmt.close();*/
			
			String dayStart[]=EHF020800T0_06.split("/");

			String sql = "" +
			" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02" +
			" FROM EHF020900T0 " +
			" WHERE 1=1 " +
			"  AND EHF020900T0_M2 = '"+dayStart[0]+"/"+dayStart[1]+"'"+	//考勤年月
			" AND EHF020900T0_04 = '"+(String)request.getAttribute("compid")+"'" +//公司代碼
			" AND (EHF020900T0_02 = '02' OR EHF020900T0_02 = '03')"; //已經確認考勤
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				if("02".equals(rs.getString("EHF020900T0_02"))||"03".equals(rs.getString("EHF020900T0_02")))
					errors.add("EHF020800T0_06",new ActionMessage(""));  
					
					request.setAttribute("ERR_MSG",(request.getAttribute("ERR_MSG")==null?"":
						request.getAttribute("ERR_MSG")+"<br>")+	rs.getString("EHF020900T0_M2")+"已經確認考勤!請再次確認"+"<br>");
				
			}
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	private void addDetailData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		BA_TOOLS tools = BA_TOOLS.getInstance();
		try{
					

			Calendar start_cal = tools.covertStringToCalendar(EHF020800T1_06_year);  //計算開始日期
			Calendar end_cal = tools.covertStringToCalendar(EHF020800T1_07_year);  //計算結束日期
			
			if(start_cal.compareTo(end_cal)>0){
				errors.add("EHF020800T1_06",new ActionMessage(""));
				errors.add("EHF020800T1_07",new ActionMessage(""));
				request.setAttribute("DETAIL_ERR_MSG",(request.getAttribute("DETAIL_ERR_MSG")==null?"":
					request.getAttribute("DETAIL_ERR_MSG")+"<br>")+	"起訖時間錯誤!!"+"<br>");
			}
			
			Calendar CHK_start_cal = tools.covertStringToCalendar(
					EHF020800T0_06+" 00:00:00","yyyy/MM/dd HH:mm:ss");  //計算開始日期
			Calendar CHK_end_cal   = tools.covertStringToCalendar(
					EHF020800T1_07_year+" "+EHF020800T1_07_HH+":"+EHF020800T1_07_MM+":00","yyyy/MM/dd HH:mm:ss");  //計算開始日期
			
			if(CHK_start_cal.compareTo(CHK_end_cal)>0){
				errors.add("EHF020800T1_06",new ActionMessage(""));
				request.setAttribute("DETAIL_ERR_MSG",(request.getAttribute("DETAIL_ERR_MSG")==null?"":
					request.getAttribute("DETAIL_ERR_MSG")+"<br>")+	"時間錯誤!!加班開始時間不能在考勤日之前。"+"<br>");
			}
			
			
			
			
			
			
			
			//建立EHF020800元件
			EHF020800 ehf020800 = new EHF020800();
			
			//修改於20131113  BY賴泓錡
			float min_pay_hour = Float.parseFloat(tools.getSysParam(conn, (String)request.getAttribute("compid"), "MIN_PAY_HOUR"));
			
			
			//處理加班時間資訊
			Map overtime_datetime_map =
			ehf020800.composeOvertimeDate(EHF020800T1_06_year, 
										  EHF020800T1_06_HH+EHF020800T1_06_MM,
										  EHF020800T1_07_year,
										  EHF020800T1_07_HH+EHF020800T1_07_MM,min_pay_hour);
			
			//處理加班休息時間資訊
			Map overtime_break_datetime_map =
			ehf020800.composeOvertimeBreakDate(EHF020800T1_06_year,EHF020800T1_06_year,
											   EHF020800T1_06_HH+EHF020800T1_06_MM, 
											   EHF020800T1_07_HH+EHF020800T1_07_MM, 
											   EHF020800T1_06_BRK_HH+EHF020800T1_06_BRK_MM, 
											   EHF020800T1_07_BRK_HH+EHF020800T1_07_BRK_MM);
			
			//判斷加班休息時間是否有異常設定
			Map check_map =
			ehf020800.checkOvertimeBreakDatAbnormal((String)overtime_datetime_map.get("EHF020800T1_06"), 
													(String)overtime_datetime_map.get("EHF020800T1_07"), 
													(String)overtime_break_datetime_map.get("EHF020800T1_06_BRK"),
													(String)overtime_break_datetime_map.get("EHF020800T1_07_BRK"));
			
			
			Map check_Middle_map =
				ehf020800.checkOvertime(conn,(String)overtime_datetime_map.get("EHF020800T1_06"), 
														(String)overtime_datetime_map.get("EHF020800T1_07"), 
														(String)overtime_break_datetime_map.get("EHF020800T1_06_BRK"),
														(String)overtime_break_datetime_map.get("EHF020800T1_07_BRK"),(String)request.getAttribute("compid"));
			
			if((Boolean)check_Middle_map.get("CHK_FLAG")&&NOON_LIMITED==null){
				//表示加班居間小於30分鐘
				errors.add("EHF020800T1_06",new ActionMessage(""));
				errors.add("EHF020800T1_07",new ActionMessage(""));
				request.setAttribute("DETAIL_ERR_MSG",(request.getAttribute("DETAIL_ERR_MSG")==null?"":
					request.getAttribute("DETAIL_ERR_MSG")+"<br>")+	"員工加班時間設定異常!!, 請勿低於30分鐘!!"+"<br>");
				
			}
			
			if((Boolean)check_map.get("CHK_FLAG")&&NOON_LIMITED==null){
				//表示加班休息時間區間異常
				errors.add("EHF020800T1_06_BRK_HH",new ActionMessage(""));
				errors.add("EHF020800T1_07_BRK_HH",new ActionMessage(""));
				request.setAttribute("DETAIL_ERR_MSG",(request.getAttribute("DETAIL_ERR_MSG")==null?"":
					request.getAttribute("DETAIL_ERR_MSG")+"<br>")+	"員工加班休息時間設定異常!!, 請再次確認!!"+"<br>");
				
			}else{
				
				
					//判斷員工加班時間區間是否重複
					check_map =
					ehf020800.checkConflictOvertimeData(EHF020800T0_11, EHF020800T1_04, 
													    (String)overtime_datetime_map.get("EHF020800T1_06"), 
													    (String)overtime_datetime_map.get("EHF020800T1_07"), 
													    (String)request.getAttribute("compid"));
					
					if((Boolean)check_map.get("CHK_FLAG")){
						//表示有重複資料
						errors.add("EHF020800T1_05",new ActionMessage(""));
						errors.add("EHF020800T1_04",new ActionMessage(""));
						request.setAttribute("DETAIL_ERR_MSG",(request.getAttribute("DETAIL_ERR_MSG")==null?"":
							request.getAttribute("DETAIL_ERR_MSG")+"<br>")+	"員工加班時間重複!!, 請再次確認!!"+"<br>");
						
					}
				
			}
			
			
			
			//關閉EHF020800 元件
			ehf020800.close();
			
			String dayStart[]=EHF020800T1_06_year.split("/");
			String dayEnd[]  =EHF020800T1_07_year.split("/");
			
			
			String sql = "" +
			" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02" +
			" FROM EHF020900T0 " +
			" WHERE 1=1 " +
			"  AND (EHF020900T0_M2 BETWEEN '"+dayStart[0]+"/"+dayStart[1]+ "' AND '"+dayEnd[0]+"/"+dayEnd[1]+"')"+	//考勤年月
			" AND EHF020900T0_04 = '"+(String)request.getAttribute("compid")+"'" +//公司代碼
			" AND (EHF020900T0_02 = '02' OR EHF020900T0_02 = '03')"; //已經確認考勤
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				if("02".equals(rs.getString("EHF020900T0_02"))||"03".equals(rs.getString("EHF020900T0_02")))
					errors.add("EHF020800T1_06_year",new ActionMessage(""));  
					errors.add("EHF020800T1_07_year",new ActionMessage(""));  
					request.setAttribute("DETAIL_ERR_MSG",(request.getAttribute("DETAIL_ERR_MSG")==null?"":
						request.getAttribute("DETAIL_ERR_MSG")+"<br>")+	rs.getString("EHF020900T0_M2")+"已經確認考勤!請再次確認"+"<br>");
				
			}
			
			
			rs.close();
			stmt.close();

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	private void saveData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
	}	
	private void delData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
	}	
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("utf-8");
			
			EHF020800T0_LIST = ListUtils.lazyList(new ArrayList(),new Factory() {
            	public Object create() {
                return new EHF020800M0F();
            	}
			});
			
			EHF020800T1_LIST = ListUtils.lazyList(new ArrayList(),new Factory() {
            	public Object create() {
                return new EHF020800M0F();
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
	public String getEHF020800T0_01() {
		return EHF020800T0_01;
	}
	public void setEHF020800T0_01(String eHF020800T0_01) {
		EHF020800T0_01 = eHF020800T0_01;
	}
	public String getEHF020800T0_02() {
		return EHF020800T0_02;
	}
	public void setEHF020800T0_02(String eHF020800T0_02) {
		EHF020800T0_02 = eHF020800T0_02;
	}
	public String getEHF020800T0_03() {
		return EHF020800T0_03;
	}
	public void setEHF020800T0_03(String eHF020800T0_03) {
		EHF020800T0_03 = eHF020800T0_03;
	}
	public String getEHF020800T0_04() {
		return EHF020800T0_04;
	}
	public void setEHF020800T0_04(String eHF020800T0_04) {
		EHF020800T0_04 = eHF020800T0_04;
	}
	public String getEHF020800T0_05() {
		return EHF020800T0_05;
	}
	public void setEHF020800T0_05(String eHF020800T0_05) {
		EHF020800T0_05 = eHF020800T0_05;
	}
	public String getEHF020800T0_06() {
		return EHF020800T0_06;
	}
	public void setEHF020800T0_06(String eHF020800T0_06) {
		EHF020800T0_06 = eHF020800T0_06;
	}
	public String getEHF020800T0_07() {
		return EHF020800T0_07;
	}
	public void setEHF020800T0_07(String eHF020800T0_07) {
		EHF020800T0_07 = eHF020800T0_07;
	}
	public String getEHF020800T0_08() {
		return EHF020800T0_08;
	}
	public void setEHF020800T0_08(String eHF020800T0_08) {
		EHF020800T0_08 = eHF020800T0_08;
	}
	public String getEHF020800T0_09() {
		return EHF020800T0_09;
	}
	public void setEHF020800T0_09(String eHF020800T0_09) {
		EHF020800T0_09 = eHF020800T0_09;
	}
	public String getEHF020800T0_09_DESC() {
		return EHF020800T0_09_DESC;
	}
	public void setEHF020800T0_09_DESC(String eHF020800T0_09DESC) {
		EHF020800T0_09_DESC = eHF020800T0_09DESC;
	}
	public String getEHF020800T0_10() {
		return EHF020800T0_10;
	}
	public void setEHF020800T0_10(String eHF020800T0_10) {
		EHF020800T0_10 = eHF020800T0_10;
	}
	public String getEHF020800T0_11() {
		return EHF020800T0_11;
	}
	public void setEHF020800T0_11(String eHF020800T0_11) {
		EHF020800T0_11 = eHF020800T0_11;
	}
	
	
	public String getEHF020800T0_11_TXT() {
		return EHF020800T0_11_TXT;
	}
	public void setEHF020800T0_11_TXT(String eHF020800T0_11TXT) {
		EHF020800T0_11_TXT = eHF020800T0_11TXT;
	}
	public String getEHF020800T1_01() {
		return EHF020800T1_01;
	}
	public void setEHF020800T1_01(String eHF020800T1_01) {
		EHF020800T1_01 = eHF020800T1_01;
	}
	public String getEHF020800T1_02() {
		return EHF020800T1_02;
	}
	public void setEHF020800T1_02(String eHF020800T1_02) {
		EHF020800T1_02 = eHF020800T1_02;
	}
	public String getEHF020800T1_03() {
		return EHF020800T1_03;
	}
	public void setEHF020800T1_03(String eHF020800T1_03) {
		EHF020800T1_03 = eHF020800T1_03;
	}
	public String getEHF020800T1_04() {
		return EHF020800T1_04;
	}
	public void setEHF020800T1_04(String eHF020800T1_04) {
		EHF020800T1_04 = eHF020800T1_04;
	}
	public String getEHF020800T1_06() {
		return EHF020800T1_06;
	}
	public void setEHF020800T1_06(String eHF020800T1_06) {
		EHF020800T1_06 = eHF020800T1_06;
	}
	public String getEHF020800T0_06_END() {
		return EHF020800T0_06_END;
	}
	public void setEHF020800T0_06_END(String eHF020800T0_06END) {
		EHF020800T0_06_END = eHF020800T0_06END;
	}
	public String getEHF020800T1_07() {
		return EHF020800T1_07;
	}
	public void setEHF020800T1_07(String eHF020800T1_07) {
		EHF020800T1_07 = eHF020800T1_07;
	}
	public String getEHF020800T1_08() {
		return EHF020800T1_08;
	}
	public void setEHF020800T1_08(String eHF020800T1_08) {
		EHF020800T1_08 = eHF020800T1_08;
	}
	public String getEHF020800T1_09() {
		return EHF020800T1_09;
	}
	public void setEHF020800T1_09(String eHF020800T1_09) {
		EHF020800T1_09 = eHF020800T1_09;
	}
	public String getEHF020800T1_10() {
		return EHF020800T1_10;
	}
	public void setEHF020800T1_10(String eHF020800T1_10) {
		EHF020800T1_10 = eHF020800T1_10;
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
	public List getEHF020800T0_LIST() {
		return EHF020800T0_LIST;
	}
	public void setEHF020800T0_LIST(List eHF020800T0LIST) {
		EHF020800T0_LIST = eHF020800T0LIST;
	}
	public List getEHF020800T1_LIST() {
		return EHF020800T1_LIST;
	}
	public void setEHF020800T1_LIST(List eHF020800T1LIST) {
		EHF020800T1_LIST = eHF020800T1LIST;
	}
	public String getEHF020800T0_03_TXT() {
		return EHF020800T0_03_TXT;
	}
	public void setEHF020800T0_03_TXT(String eHF020800T0_03TXT) {
		EHF020800T0_03_TXT = eHF020800T0_03TXT;
	}
	public String getEHF020800T0_04_TXT() {
		return EHF020800T0_04_TXT;
	}
	public void setEHF020800T0_04_TXT(String eHF020800T0_04TXT) {
		EHF020800T0_04_TXT = eHF020800T0_04TXT;
	}
	public String getEHF020800T1_04_TXT() {
		return EHF020800T1_04_TXT;
	}
	public void setEHF020800T1_04_TXT(String eHF020800T1_04TXT) {
		EHF020800T1_04_TXT = eHF020800T1_04TXT;
	}
	public String getEHF020800T1_06_HH() {
		return EHF020800T1_06_HH;
	}
	public void setEHF020800T1_06_HH(String eHF020800T1_06HH) {
		EHF020800T1_06_HH = eHF020800T1_06HH;
	}
	public String getEHF020800T1_06_MM() {
		return EHF020800T1_06_MM;
	}
	public void setEHF020800T1_06_MM(String eHF020800T1_06MM) {
		EHF020800T1_06_MM = eHF020800T1_06MM;
	}
	public String getEHF020800T1_07_HH() {
		return EHF020800T1_07_HH;
	}
	public void setEHF020800T1_07_HH(String eHF020800T1_07HH) {
		EHF020800T1_07_HH = eHF020800T1_07HH;
	}
	public String getEHF020800T1_07_MM() {
		return EHF020800T1_07_MM;
	}
	public void setEHF020800T1_07_MM(String eHF020800T1_07MM) {
		EHF020800T1_07_MM = eHF020800T1_07MM;
	}
	public String getEHF020800T1_06_BRK() {
		return EHF020800T1_06_BRK;
	}
	public void setEHF020800T1_06_BRK(String eHF020800T1_06BRK) {
		EHF020800T1_06_BRK = eHF020800T1_06BRK;
	}
	public String getEHF020800T1_06_BRK_HH() {
		return EHF020800T1_06_BRK_HH;
	}
	public void setEHF020800T1_06_BRK_HH(String eHF020800T1_06BRKHH) {
		EHF020800T1_06_BRK_HH = eHF020800T1_06BRKHH;
	}
	public String getEHF020800T1_06_BRK_MM() {
		return EHF020800T1_06_BRK_MM;
	}
	public void setEHF020800T1_06_BRK_MM(String eHF020800T1_06BRKMM) {
		EHF020800T1_06_BRK_MM = eHF020800T1_06BRKMM;
	}
	public String getEHF020800T1_07_BRK() {
		return EHF020800T1_07_BRK;
	}
	public void setEHF020800T1_07_BRK(String eHF020800T1_07BRK) {
		EHF020800T1_07_BRK = eHF020800T1_07BRK;
	}
	public String getEHF020800T1_07_BRK_HH() {
		return EHF020800T1_07_BRK_HH;
	}
	public void setEHF020800T1_07_BRK_HH(String eHF020800T1_07BRKHH) {
		EHF020800T1_07_BRK_HH = eHF020800T1_07BRKHH;
	}
	public String getEHF020800T1_07_BRK_MM() {
		return EHF020800T1_07_BRK_MM;
	}
	public void setEHF020800T1_07_BRK_MM(String eHF020800T1_07BRKMM) {
		EHF020800T1_07_BRK_MM = eHF020800T1_07BRKMM;
	}
	public String getEHF020800T1_08_DE() {
		return EHF020800T1_08_DE;
	}
	public void setEHF020800T1_08_DE(String eHF020800T1_08DE) {
		EHF020800T1_08_DE = eHF020800T1_08DE;
	}
	public String getEHF020800T1_11() {
		return EHF020800T1_11;
	}
	public void setEHF020800T1_11(String eHF020800T1_11) {
		EHF020800T1_11 = eHF020800T1_11;
	}
	public String getEHF020800T1_06_year() {
		return EHF020800T1_06_year;
	}
	public void setEHF020800T1_06_year(String eHF020800T1_06Year) {
		EHF020800T1_06_year = eHF020800T1_06Year;
	}
	public String getEHF020800T1_07_year() {
		return EHF020800T1_07_year;
	}
	public void setEHF020800T1_07_year(String eHF020800T1_07Year) {
		EHF020800T1_07_year = eHF020800T1_07Year;
	}
	public FormFile getUp_file1() {
		return up_file1;
	}
	public void setUp_file1(FormFile upFile1) {
		up_file1 = upFile1;
	}
	public String getERROR() {
		return ERROR;
	}
	public void setERROR(String eRROR) {
		ERROR = eRROR;
	}
	public void setEHF020800T0_03_SHOW(String eHF020800T0_03_SHOW) {
		EHF020800T0_03_SHOW = eHF020800T0_03_SHOW;
	}
	public String getEHF020800T0_03_SHOW() {
		return EHF020800T0_03_SHOW;
	}
	public void setEHF020800T0_04_SHOW(String eHF020800T0_04_SHOW) {
		EHF020800T0_04_SHOW = eHF020800T0_04_SHOW;
	}
	public String getEHF020800T0_04_SHOW() {
		return EHF020800T0_04_SHOW;
	}
	public void setEHF020800T0_11_SHOW(String eHF020800T0_11_SHOW) {
		EHF020800T0_11_SHOW = eHF020800T0_11_SHOW;
	}
	public String getEHF020800T0_11_SHOW() {
		return EHF020800T0_11_SHOW;
	}
	public void setEHF020800T1_04_SHOW(String eHF020800T1_04_SHOW) {
		EHF020800T1_04_SHOW = eHF020800T1_04_SHOW;
	}
	public String getEHF020800T1_04_SHOW() {
		return EHF020800T1_04_SHOW;
	}
	public void setUPLOADFILE(FormFile uPLOADFILE) {
		UPLOADFILE = uPLOADFILE;
	}
	public FormFile getUPLOADFILE() {
		return UPLOADFILE;
	}
	public Float getEHF020800T1_12() {
		return EHF020800T1_12;
	}
	public void setEHF020800T1_12(Float eHF020800T1_12) {
		EHF020800T1_12 = eHF020800T1_12;
	}
	public String getNOON_LIMITED() {
		return NOON_LIMITED;
	}
	public void setNOON_LIMITED(String nOONLIMITED) {
		NOON_LIMITED = nOONLIMITED;
	}
	public String getEHF020800T1_13() {
		return EHF020800T1_13;
	}
	public void setEHF020800T1_13(String eHF020800T1_13) {
		EHF020800T1_13 = eHF020800T1_13;
	}
	public void setSIGN_COMMENT(String sIGN_COMMENT) {
		SIGN_COMMENT = sIGN_COMMENT;
	}
	public String getSIGN_COMMENT() {
		return SIGN_COMMENT;
	}

	
	
	
	
}
