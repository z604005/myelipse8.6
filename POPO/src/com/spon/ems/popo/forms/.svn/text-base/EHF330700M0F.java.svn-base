package com.spon.ems.popo.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.GenericValidator;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;



import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.ems.popo.models.EHF330700;
import com.spon.utils.util.BA_Vaildate;

/**
 * <Form>EMS讀取VIEWDATA的Form
 * @version 1.0
 * @created 25-四月-2011 下午 02:26:36
 */
public class EHF330700M0F extends ActionForm implements ValidateForm {
	
	private String EHF330700T0_01;

	private List EHF330700_C  = new ArrayList(); 
	private String EHF310100T0_01;				//系統編號
	private String EHF310100T0_03;				//櫃號
	private String EHF310100T0_03_B;			//產婦的基本資料			櫃號(起)
	private String EHF310100T0_03_E;			//產婦的基本資料			櫃號(迄)
	private String EHF310100T0_04;			//產婦的基本資料			產婦姓名
	private String EHF310100T0_10_B;			//產婦的基本資料			送餐日期(起)
	private String EHF310100T0_10_E;			//產婦的基本資料			送餐日期(迄)
	
	private String EHF310100T0_01_C;		//系統編號
	private String EHF310100T0_03_C;			//櫃號
	private String EHF310100T0_04_C;			//產婦姓名
	private String EHF310100T0_10_C;			//送餐日期
	
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stubActionErrors l_actionErrors = new ActionErrors();
		
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		if (l_actionErrors.isEmpty()) {
					
					//查詢時判斷條件
					if (request.getAttribute("action").equals("queryData")) {
										
						queryData_validate(l_actionErrors, request, conn);
					}
		}
		
		
		return l_actionErrors;
	}

	private void queryData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		try {
				String Begin =EHF310100T0_10_B.toString();
				String End = EHF310100T0_10_E.toString();
					if (End != null && !End.equals("") && !"".equals(Begin)) {
						if (Begin != null && !Begin.equals("") && !"".equals(End)) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
							Date Begindate = sdf.parse(Begin);
							Date Enddate = sdf.parse(End);
							if (Begindate.after(Enddate)){
								errors.add("EHF310100T0_10_B", new ActionMessage(""));
								errors.add("EHF310100T0_10_E", new ActionMessage(""));
								request.setAttribute("ERR_MSG", "送餐結束日期不能大於送餐開始日期");
							}
						}
				}

//					int temp;
				if (EHF310100T0_03_E != null && !EHF310100T0_03_E.equals("") ) {
					if (EHF310100T0_03_B != null && !EHF310100T0_03_B.equals("") ) {
						
						
						int B=Integer.parseInt(EHF310100T0_03_B);
						int E=Integer.parseInt(EHF310100T0_03_E);
						
						if (B>E){
						//	errors.add("EHF310100T0_03_B", new ActionMessage(""));
						//	errors.add("EHF310100T0_03_E", new ActionMessage(""));
							request.setAttribute("ERR_MSG", "櫃號請依小到大輸入");
						}
						
						
					}
				}
			} catch (ParseException e) {
			e.printStackTrace();
			
		}
	}
	
	

	public void setEHF330700T0_01(String eHF330700T0_01) {
		EHF330700T0_01 = eHF330700T0_01;
	}


	public String getEHF330700T0_01() {
		return EHF330700T0_01;
	}


	public void setEHF330700_C(List eHF330700_C) {
		EHF330700_C = eHF330700_C;
	}


	public List getEHF330700_C() {
		return EHF330700_C;
	}


	public void setEHF310100T0_10_B(String eHF310100T0_10_B) {
		EHF310100T0_10_B = eHF310100T0_10_B;
	}


	public String getEHF310100T0_10_B() {
		return EHF310100T0_10_B;
	}


	public void setEHF310100T0_10_E(String eHF310100T0_10_E) {
		EHF310100T0_10_E = eHF310100T0_10_E;
	}


	public String getEHF310100T0_10_E() {
		return EHF310100T0_10_E;
	}


	public void setEHF310100T0_03_B(String eHF310100T0_03_B) {
		EHF310100T0_03_B = eHF310100T0_03_B;
	}


	public String getEHF310100T0_03_B() {
		return EHF310100T0_03_B;
	}


	public void setEHF310100T0_03_E(String eHF310100T0_03_E) {
		EHF310100T0_03_E = eHF310100T0_03_E;
	}


	public String getEHF310100T0_03_E() {
		return EHF310100T0_03_E;
	}

	public void setEHF310100T0_03_C(String eHF310100T0_03_C) {
		EHF310100T0_03_C = eHF310100T0_03_C;
	}

	public String getEHF310100T0_03_C() {
		return EHF310100T0_03_C;
	}

	public void setEHF310100T0_04_C(String eHF310100T0_04_C) {
		EHF310100T0_04_C = eHF310100T0_04_C;
	}

	public String getEHF310100T0_04_C() {
		return EHF310100T0_04_C;
	}

	public void setEHF310100T0_10_C(String eHF310100T0_10_C) {
		EHF310100T0_10_C = eHF310100T0_10_C;
	}

	public String getEHF310100T0_10_C() {
		return EHF310100T0_10_C;
	}

	public void setEHF310100T0_01(String eHF310100T0_01) {
		EHF310100T0_01 = eHF310100T0_01;
	}

	public String getEHF310100T0_01() {
		return EHF310100T0_01;
	}

	public void setEHF310100T0_03(String eHF310100T0_03) {
		EHF310100T0_03 = eHF310100T0_03;
	}

	public String getEHF310100T0_03() {
		return EHF310100T0_03;
	}

	public void setEHF310100T0_04(String eHF310100T0_04) {
		EHF310100T0_04 = eHF310100T0_04;
	}

	public String getEHF310100T0_04() {
		return EHF310100T0_04;
	}

	public void setEHF310100T0_01_C(String eHF310100T0_01_C) {
		EHF310100T0_01_C = eHF310100T0_01_C;
	}

	public String getEHF310100T0_01_C() {
		return EHF310100T0_01_C;
	}
	
	
	
}
