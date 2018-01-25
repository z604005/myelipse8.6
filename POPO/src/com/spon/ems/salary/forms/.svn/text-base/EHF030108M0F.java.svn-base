package com.spon.ems.salary.forms;

import java.sql.Connection;
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

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.ems.salary.models.EHF030108;
import com.spon.utils.util.BA_Vaildate;

/**
 * 全勤獎金扣費規則設定作業 FormBean
 * @author Joe
 *
 */
public class EHF030108M0F extends ActionForm implements ValidateForm {
	
	private static final long serialVersionUID = 1L;
	private boolean CHECKED;
	private boolean CHANGED;
	
	private String EHF030108T0_01;
	private String EHF030108T0_02;
	private String EHF030108T0_03;
	private String EHF030108T0_04;
	private String EHF030108T0_05;
	private String EHF030108T0_06;
	private String EHF030108T0_07;
	private String EHF030108T0_08;
	private String EHF030108T0_09;
	private String EHF030108T0_10;
	private String EHF030108T0_11;
	private String EHF030108T0_12;
	private String EHF030108T0_13;
	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_CREATE;
	private String DATE_UPDATE;
	
	private String EHF030108T1_01;
	private String EHF030108T1_02;
	private String EHF030108T1_03;
	private String EHF030108T1_04;
	private String EHF030108T1_05;
	private String EHF030108T1_06;;
	private String EHF030108T1_07;
	private List EHF030108T1_LIST = new ArrayList();

	private List EHF030108T0_LIST = new ArrayList();
	
	public ActionErrors validate(ActionMapping mapping,	HttpServletRequest request, Connection conn) {
		
		ActionErrors l_actionErrors = new ActionErrors();
		
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		if (l_actionErrors.isEmpty()) {
			
			//新增時判斷條件
			if (request.getAttribute("action").equals("addData")) {
				
				ve.isEmpty(l_actionErrors, EHF030108T0_02, "EHF030108T0_02", "不可空白");  //全勤獎金扣費規則代號
				ve.isEmpty(l_actionErrors, EHF030108T0_03, "EHF030108T0_03", "不可空白");  //全勤獎金扣費規則名稱
				ve.isEmpty(l_actionErrors, EHF030108T0_12, "EHF030108T0_12", "不可空白");  //是否啟用
				
				//ve.isEmpty(l_actionErrors, EHF030108T0_05, "EHF030108T0_05", "不可空白");  //遲到早退扣費基準
				//ve.isEmpty(l_actionErrors, EHF030108T0_07, "EHF030108T0_07", "不可空白");  //遲到早退扣費金額
				//ve.isEmpty(l_actionErrors, EHF030108T0_09, "EHF030108T0_09", "不可空白");  //曠職扣費基準
				//ve.isEmpty(l_actionErrors, EHF030108T0_11, "EHF030108T0_11", "不可空白");  //曠職扣費金額
				
				addData_validate(l_actionErrors, request, conn);
			}

			//修改時判斷條件
			if (request.getAttribute("action").equals("saveData")) {
				
				ve.isEmpty(l_actionErrors, EHF030108T0_02, "EHF030108T0_02", "不可空白");  //全勤獎金扣費規則代號
				ve.isEmpty(l_actionErrors, EHF030108T0_03, "EHF030108T0_03", "不可空白");  //全勤獎金扣費規則名稱
				ve.isEmpty(l_actionErrors, EHF030108T0_12, "EHF030108T0_12", "不可空白");  //是否啟用
				
				ve.isEmpty(l_actionErrors, EHF030108T0_05, "EHF030108T0_05", "不可空白");  //遲到早退扣費基準
				ve.isEmpty(l_actionErrors, EHF030108T0_07, "EHF030108T0_07", "不可空白");  //遲到早退扣費金額
				ve.isEmpty(l_actionErrors, EHF030108T0_09, "EHF030108T0_09", "不可空白");  //曠職扣費基準
				ve.isEmpty(l_actionErrors, EHF030108T0_11, "EHF030108T0_11", "不可空白");  //曠職扣費金額
				
				saveData_validate(l_actionErrors, request, conn);
			}
			
			//刪除時判斷條件
			if (request.getAttribute("action").equals("delData")) {
				
				delData_validate(l_actionErrors, request, conn);
			}
			//新增明細時驗證
			if (request.getAttribute("action").equals("addEHF030108T1")) {
				
				addDetailData_validate(l_actionErrors, request, conn);
			}
			
		}

		return l_actionErrors;
	}
	private void addDetailData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		//建立全勤獎金扣費規則設定元件
		EHF030108 ehf030108 = new EHF030108();
		Map detailDataMap= new HashMap();
		detailDataMap.put("EHF030108T0_01", EHF030108T0_01);
		detailDataMap.put("EHF030108T1_02", EHF030108T1_02);
		detailDataMap.put("COMP_ID", (String)request.getAttribute("compid"));
		
		
		if(ehf030108.SELECT_EHF030108T1((String)request.getAttribute("compid"), detailDataMap)){
			//表示有重複資料
			errors.add("",new ActionMessage(""));
			request.setAttribute("ERR_MSG", "明細重複!!, 請再次確認!!");
		}
		
		
	}
	private void addData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		try{
			
			//建立全勤獎金扣費規則設定元件
			EHF030108 ehf030108 = new EHF030108();
			
			Map full_attendance_rule_code_duplicate_map = 
			ehf030108.checkDuplicateFullAttendanceRule(EHF030108T0_02, (String)request.getAttribute("compid"));
			
			if((Boolean)full_attendance_rule_code_duplicate_map.get("CHK_FLAG")){
				//表示有重複資料
				errors.add("EHF030108T0_02",new ActionMessage(""));
				request.setAttribute("ERR_MSG", "全勤獎金扣費規則代號重複!!, 請再次確認!!");
			}
			
			//關閉全勤獎金規則設定元件
			ehf030108.close();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void saveData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
	}
	
	private void delData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		try{
			
			//建立全勤獎金扣費規則設定元件
			EHF030108 ehf030108 = new EHF030108();
			
			Map full_attendance_rule_code_use_by_emp_map = 
			ehf030108.checkFullAttendanceRuleUseByEmployee(EHF030108T0_02, (String)request.getAttribute("compid"));
			
			if((Boolean)full_attendance_rule_code_use_by_emp_map.get("CHK_FLAG")){
				errors.add("",new ActionMessage(""));
				//表示全勤獎金扣費規則已被使用
				request.setAttribute("ERR_MSG", "全勤獎金扣費規則已被員工薪資主檔使用!!, " +
											    "不可刪除, 請再次確認!!");
			}
			
			//關閉全勤獎金扣費規則設定元件
			ehf030108.close();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("utf-8");
			
			EHF030108T0_LIST = ListUtils.lazyList(new ArrayList(),new Factory() {
            	public Object create() {
            		return new EHF030108M0F();
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

	public String getEHF030108T0_01() {
		return EHF030108T0_01;
	}

	public void setEHF030108T0_01(String eHF030108T0_01) {
		EHF030108T0_01 = eHF030108T0_01;
	}

	public String getEHF030108T0_02() {
		return EHF030108T0_02;
	}

	public void setEHF030108T0_02(String eHF030108T0_02) {
		EHF030108T0_02 = eHF030108T0_02;
	}

	public String getEHF030108T0_03() {
		return EHF030108T0_03;
	}

	public void setEHF030108T0_03(String eHF030108T0_03) {
		EHF030108T0_03 = eHF030108T0_03;
	}

	public String getEHF030108T0_04() {
		return EHF030108T0_04;
	}

	public void setEHF030108T0_04(String eHF030108T0_04) {
		EHF030108T0_04 = eHF030108T0_04;
	}

	public String getEHF030108T0_05() {
		return EHF030108T0_05;
	}

	public void setEHF030108T0_05(String eHF030108T0_05) {
		EHF030108T0_05 = eHF030108T0_05;
	}

	public String getEHF030108T0_06() {
		return EHF030108T0_06;
	}

	public void setEHF030108T0_06(String eHF030108T0_06) {
		EHF030108T0_06 = eHF030108T0_06;
	}

	public String getEHF030108T0_07() {
		return EHF030108T0_07;
	}

	public void setEHF030108T0_07(String eHF030108T0_07) {
		EHF030108T0_07 = eHF030108T0_07;
	}

	public String getEHF030108T0_08() {
		return EHF030108T0_08;
	}

	public void setEHF030108T0_08(String eHF030108T0_08) {
		EHF030108T0_08 = eHF030108T0_08;
	}

	public String getEHF030108T0_09() {
		return EHF030108T0_09;
	}

	public void setEHF030108T0_09(String eHF030108T0_09) {
		EHF030108T0_09 = eHF030108T0_09;
	}

	public String getEHF030108T0_10() {
		return EHF030108T0_10;
	}

	public void setEHF030108T0_10(String eHF030108T0_10) {
		EHF030108T0_10 = eHF030108T0_10;
	}

	public String getEHF030108T0_11() {
		return EHF030108T0_11;
	}

	public void setEHF030108T0_11(String eHF030108T0_11) {
		EHF030108T0_11 = eHF030108T0_11;
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

	public List getEHF030108T0_LIST() {
		return EHF030108T0_LIST;
	}

	public void setEHF030108T0_LIST(List eHF030108T0LIST) {
		EHF030108T0_LIST = eHF030108T0LIST;
	}

	public String getEHF030108T0_12() {
		return EHF030108T0_12;
	}

	public void setEHF030108T0_12(String eHF030108T0_12) {
		EHF030108T0_12 = eHF030108T0_12;
	}

	public String getEHF030108T0_13() {
		return EHF030108T0_13;
	}

	public void setEHF030108T0_13(String eHF030108T0_13) {
		EHF030108T0_13 = eHF030108T0_13;
	}

	public String getEHF030108T1_01() {
		return EHF030108T1_01;
	}

	public void setEHF030108T1_01(String eHF030108T1_01) {
		EHF030108T1_01 = eHF030108T1_01;
	}

	public String getEHF030108T1_02() {
		return EHF030108T1_02;
	}

	public void setEHF030108T1_02(String eHF030108T1_02) {
		EHF030108T1_02 = eHF030108T1_02;
	}

	public String getEHF030108T1_03() {
		return EHF030108T1_03;
	}

	public void setEHF030108T1_03(String eHF030108T1_03) {
		EHF030108T1_03 = eHF030108T1_03;
	}

	public String getEHF030108T1_04() {
		return EHF030108T1_04;
	}

	public void setEHF030108T1_04(String eHF030108T1_04) {
		EHF030108T1_04 = eHF030108T1_04;
	}

	public String getEHF030108T1_05() {
		return EHF030108T1_05;
	}

	public void setEHF030108T1_05(String eHF030108T1_05) {
		EHF030108T1_05 = eHF030108T1_05;
	}

	public String getEHF030108T1_06() {
		return EHF030108T1_06;
	}

	public void setEHF030108T1_06(String eHF030108T1_06) {
		EHF030108T1_06 = eHF030108T1_06;
	}

	public String getEHF030108T1_07() {
		return EHF030108T1_07;
	}

	public void setEHF030108T1_07(String eHF030108T1_07) {
		EHF030108T1_07 = eHF030108T1_07;
	}

	public List getEHF030108T1_LIST() {
		return EHF030108T1_LIST;
	}

	public void setEHF030108T1_LIST(List eHF030108T1LIST) {
		EHF030108T1_LIST = eHF030108T1LIST;
	}

	
}
