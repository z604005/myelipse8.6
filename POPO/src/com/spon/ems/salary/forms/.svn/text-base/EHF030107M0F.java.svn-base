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

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.ems.salary.models.EHF030107;
import com.spon.utils.util.BA_Vaildate;

/**
 * 加班費規則設定作業 FormBean
 * @author Joe
 *
 */
public class EHF030107M0F extends ActionForm implements ValidateForm {
	
	private static final long serialVersionUID = 1L;
	private boolean CHECKED;
	private boolean CHANGED;
	
	private String EHF030107T0_01;
	private String EHF030107T0_02;
	private String EHF030107T0_03;
	private String EHF030107T0_04;
	private String EHF030107T0_05;
	private String EHF030107T0_06;
	private String EHF030107T0_07;
	private String EHF030107T0_08;
	private String EHF030107T0_09;
	private String EHF030107T0_10;
	private String EHF030107T0_11;
	
	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_CREATE;
	private String DATE_UPDATE;
	
	private List EHF030107T0_LIST = new ArrayList();
	
	public ActionErrors validate(ActionMapping mapping,	HttpServletRequest request, Connection conn) {
		
		ActionErrors l_actionErrors = new ActionErrors();
		
		String arrid[] = request.getParameterValues("checkId");
		if ((arrid==null?false:!arrid[0].equals(""))){
			EHF030107T0_01=arrid[0];
		}
		
		
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		if (l_actionErrors.isEmpty()) {
			
			//新增時判斷條件
			if (request.getAttribute("action").equals("addData")) {
				
				ve.isEmpty(l_actionErrors, EHF030107T0_02, "EHF030107T0_02", "不可空白");  //加班費規則代號
				ve.isEmpty(l_actionErrors, EHF030107T0_03, "EHF030107T0_03", "不可空白");  //加班費規則名稱
				ve.isEmpty(l_actionErrors, EHF030107T0_09, "EHF030107T0_09", "不可空白");  //是否啟用
				
				addData_validate(l_actionErrors, request, conn);
			}

			//修改時判斷條件
			if (request.getAttribute("action").equals("saveData")) {
				
				ve.isEmpty(l_actionErrors, EHF030107T0_02, "EHF030107T0_02", "不可空白");  //加班費規則代號
				ve.isEmpty(l_actionErrors, EHF030107T0_03, "EHF030107T0_03", "不可空白");  //加班費規則名稱
				ve.isEmpty(l_actionErrors, EHF030107T0_09, "EHF030107T0_09", "不可空白");  //是否啟用
				
				saveData_validate(l_actionErrors, request, conn);
			}
			
			//刪除時判斷條件
			if (request.getAttribute("action").equals("delData")) {
				
				delData_validate(l_actionErrors, request, conn);
			}
			
		}

		return l_actionErrors;
	}
	
	private void addData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		try{
			
			//建立加班費規則設定元件
			EHF030107 ehf030107 = new EHF030107();
			
			Map ov_rule_code_duplicate_map = ehf030107.checkDuplicateOvertimeRule(EHF030107T0_02, (String)request.getAttribute("compid"));
			
			if((Boolean)ov_rule_code_duplicate_map.get("CHK_FLAG")){
				//表示加班費規則已被使用
				errors.add("EHF030107T0_02",new ActionMessage(""));
				request.setAttribute("ERR_MSG", "加班費規則代號重複!!, 請再次確認!!");
			}
			
			//關閉加班費規則設定元件
			ehf030107.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void saveData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		try{
			//建立加班費規則設定元件
			EHF030107 ehf030107 = new EHF030107();
			String sql = "";
			
			
			sql = "" +
			" SELECT " +
			" EHF030107T0_02 " +
			" FROM EHF030107T0 " +
			" WHERE 1=1 " +
			" AND EHF030107T0_01 = '"+EHF030107T0_01+"' " +  //加班規則代碼
			" AND EHF030107T0_10 = '"+(String)request.getAttribute("compid")+"' ";  //公司代碼
			Statement stmt = conn.createStatement();
			ResultSet rs = null;
			
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				EHF030107T0_02=rs.getString("EHF030107T0_02");
			}
			rs.close();
			stmt.close();
			
			
			
			
			Map ov_rule_code_use_by_emp_map = ehf030107.checkOvertimeRuleUseByEmployee(EHF030107T0_02, (String)request.getAttribute("compid"));
			
			if((Boolean)ov_rule_code_use_by_emp_map.get("CHK_FLAG")){
				//建立查詢資料Map
				Map queryCondMap = new HashMap();
				queryCondMap.put("EHF030107T0_01", EHF030107T0_01);  //全勤獎金扣費規則序號
				queryCondMap.put("COMP_ID", request.getAttribute("compid"));  //全勤獎金扣費規則序號

				Statement stmt_01 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

				sql = "" +
					" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_03" +
					" FROM EHF020900T0 " +
					" WHERE 1=1 " +
					" AND EHF020900T0_04 = '"+request.getAttribute("compid")+"'" +//公司代碼
					" AND (EHF020900T0_03 = '02' OR EHF020900T0_03 = '03')";

	    	
				ResultSet rs_01 = stmt_01.executeQuery(sql);
			
				if(rs_01.next()){
					errors.add("",new ActionMessage(""));
					request.setAttribute("ERR_MSG",(request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"修改失敗，尚有薪資未出帳，請勿修改加班費規則。 ");
					//request.setAttribute("ErrMSG","尚有薪資未出帳，請勿刪除員工薪資基本資料。");
				
					Map dataMap = ehf030107.queryEditData(queryCondMap);
					
					this.setEHF030107T0_02((String)dataMap.get("EHF030107T0_02"));
					this.setEHF030107T0_03((String)dataMap.get("EHF030107T0_03"));
					this.setEHF030107T0_04((String)dataMap.get("EHF030107T0_04"));
					this.setEHF030107T0_05(String.valueOf((Float)dataMap.get("EHF030107T0_05")));
					this.setEHF030107T0_06((String)dataMap.get("EHF030107T0_06"));
					this.setEHF030107T0_07(String.valueOf((Float)dataMap.get("EHF030107T0_07")));
					this.setEHF030107T0_08(String.valueOf((Float)dataMap.get("EHF030107T0_08")));
					this.setEHF030107T0_09((String)dataMap.get("EHF030107T0_09"));
					this.setEHF030107T0_10((String)dataMap.get("EHF030107T0_10"));
					this.setEHF030107T0_11((String)dataMap.get("EHF030107T0_11"));
					
					this.setUSER_CREATE((String)dataMap.get("USER_CREATE"));
					this.setUSER_UPDATE((String)dataMap.get("USER_UPDATE"));
					this.setVERSION((Integer) dataMap.get("VERSION"));
					this.setDATE_CREATE((String)dataMap.get("DATE_CREATE"));
					this.setDATE_UPDATE((String)dataMap.get("DATE_UPDATE"));
				}
				rs_01.close();
				stmt_01.close();
			}

			rs.close();
			stmt.close();
			ehf030107.close();
		}catch (SQLException e) {
			System.out.println("EHF030102M0F.saveData_validate()" + e);
		}
	}
	
	private void delData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		try{
			
			
			
			String sql = "" +
			" SELECT " +
			" EHF030107T0_02 " +
			" FROM EHF030107T0 " +
			" WHERE 1=1 " +
			" AND EHF030107T0_01 = '"+EHF030107T0_01+"' " +  //加班規則代碼
			" AND EHF030107T0_10 = '"+(String)request.getAttribute("compid")+"' ";  //公司代碼
			Statement stmt = conn.createStatement();
			ResultSet rs = null;
			
			rs = stmt.executeQuery(sql);
			if(rs.next()){
				EHF030107T0_02=rs.getString("EHF030107T0_02");
			}
			rs.close();
			stmt.close();
			
			
			//建立加班費規則設定元件
			EHF030107 ehf030107 = new EHF030107();
			
			Map ov_rule_code_use_by_emp_map = ehf030107.checkOvertimeRuleUseByEmployee(EHF030107T0_02, (String)request.getAttribute("compid"));
			
			if((Boolean)ov_rule_code_use_by_emp_map.get("CHK_FLAG")){
				//表示有重複資料
				errors.add("",new ActionMessage(""));
				request.setAttribute("ERR_MSG", "加班費規則已被員工薪資主檔使用!!, " + "不可刪除, 請再次確認!!");
			}
			
			//關閉加班費規則設定元件
			ehf030107.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("utf-8");
			
			EHF030107T0_LIST = ListUtils.lazyList(new ArrayList(),new Factory() {
            	public Object create() {
            		return new EHF030107M0F();
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
	public String getEHF030107T0_01() {
		return EHF030107T0_01;
	}
	public void setEHF030107T0_01(String eHF030107T0_01) {
		EHF030107T0_01 = eHF030107T0_01;
	}
	public String getEHF030107T0_02() {
		return EHF030107T0_02;
	}
	public void setEHF030107T0_02(String eHF030107T0_02) {
		EHF030107T0_02 = eHF030107T0_02;
	}
	public String getEHF030107T0_03() {
		return EHF030107T0_03;
	}
	public void setEHF030107T0_03(String eHF030107T0_03) {
		EHF030107T0_03 = eHF030107T0_03;
	}
	public String getEHF030107T0_04() {
		return EHF030107T0_04;
	}
	public void setEHF030107T0_04(String eHF030107T0_04) {
		EHF030107T0_04 = eHF030107T0_04;
	}
	public String getEHF030107T0_05() {
		return EHF030107T0_05;
	}
	public void setEHF030107T0_05(String eHF030107T0_05) {
		EHF030107T0_05 = eHF030107T0_05;
	}
	public String getEHF030107T0_06() {
		return EHF030107T0_06;
	}
	public void setEHF030107T0_06(String eHF030107T0_06) {
		EHF030107T0_06 = eHF030107T0_06;
	}
	public String getEHF030107T0_07() {
		return EHF030107T0_07;
	}
	public void setEHF030107T0_07(String eHF030107T0_07) {
		EHF030107T0_07 = eHF030107T0_07;
	}
	public String getEHF030107T0_08() {
		return EHF030107T0_08;
	}
	public void setEHF030107T0_08(String eHF030107T0_08) {
		EHF030107T0_08 = eHF030107T0_08;
	}
	public String getEHF030107T0_09() {
		return EHF030107T0_09;
	}
	public void setEHF030107T0_09(String eHF030107T0_09) {
		EHF030107T0_09 = eHF030107T0_09;
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
	public List getEHF030107T0_LIST() {
		return EHF030107T0_LIST;
	}
	public void setEHF030107T0_LIST(List eHF030107T0LIST) {
		EHF030107T0_LIST = eHF030107T0LIST;
	}

	public String getEHF030107T0_10() {
		return EHF030107T0_10;
	}

	public void setEHF030107T0_10(String eHF030107T0_10) {
		EHF030107T0_10 = eHF030107T0_10;
	}

	public void setEHF030107T0_11(String eHF030107T0_11) {
		EHF030107T0_11 = eHF030107T0_11;
	}

	public String getEHF030107T0_11() {
		return EHF030107T0_11;
	}
	
	
	
	
}
