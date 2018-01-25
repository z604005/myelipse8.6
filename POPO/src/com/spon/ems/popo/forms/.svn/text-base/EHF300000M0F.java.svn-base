package com.spon.ems.popo.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
import com.spon.ems.popo.models.EHF300000;
import com.spon.utils.util.BA_Vaildate;

/**
 * <Form>EMS讀取VIEWDATA的Form
 * @version 1.0
 * @created 25-四月-2011 下午 02:26:36
 */
public class EHF300000M0F extends ActionForm implements ValidateForm {
	
	private String PSFOODT0_01;
	private String PSFOODT0_02;
	private String PSFOODT0_03;
	private String PSFOODT0_04;
	private String PSFOODT0_05;
	private int PSFOODT0_06;
	private boolean PSFOODT0_07=true;
	private String PSFOODT0_07_TXT;
	private String PSFOODT0_08;
	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_UPDATE;
	
	private String PSFOODT1_01;
	private String PSFOODT1_02;
	private String PSFOODT1_03;
	private String PSFOODT1_04;
	private String PSFOODT1_05;
	private String PSFOODT1_06;
	private int PSFOODT1_07;
	private boolean PSFOODT1_08;
	private String PSFOODT1_09;

	private List listPSFOODT0_02 = new ArrayList();
	private List EHF300000_DETAIL = new ArrayList(); //M1明細的List
	private List PSFOODT0_LIST  = new ArrayList();	//M0查詢的List
	private String space;

	
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
					ve.isEmpty(l_actionErrors, PSFOODT0_01, "PSFOODT0_01","不可空白");
					ve.isEmpty(l_actionErrors, PSFOODT0_04, "PSFOODT0_04","不可空白");
					//ve.isEmpty(l_actionErrors, PSFOODT0_05, "PSFOODT0_05","不可空白");
					ve.isNumEmpty(l_actionErrors, PSFOODT0_06+"", "PSFOODT0_06","請輸入數值",true);
					ve.isEmpty(l_actionErrors, PSFOODT0_07+"", "PSFOODT0_07","不可空白");
					
					if((!"".equals(getPSFOODT0_01()) && getPSFOODT0_01()!=null) && !GenericValidator.maxLength(getPSFOODT0_01(), 50)){//
						l_actionErrors.add("PSFOODT0_01", new ActionMessage(""));
						request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"食物代碼字數不得超過50個字!!");
					}
					if((!"".equals(getPSFOODT0_04()) && getPSFOODT0_04()!=null) && !GenericValidator.maxLength(getPSFOODT0_04(), 50)){//
						l_actionErrors.add("PSFOODT0_04", new ActionMessage(""));
						request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"食物名稱字數不得超過50個字!!");
					}
					if((!"".equals(getPSFOODT0_05()) && getPSFOODT0_05()!=null) && !GenericValidator.maxLength(getPSFOODT0_05(), 30)){//
						l_actionErrors.add("PSFOODT0_05", new ActionMessage(""));
						request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"食物說明字數不得超過30個字!!");
					}
					
					addData_validate(l_actionErrors, request, conn);
				}
				
				//新增明細時判斷條件
				if (request.getAttribute("action").equals("addDetailData")) {
					ve.isEmpty(l_actionErrors, PSFOODT1_04, "PSFOODT1_04","不可空白");
					ve.isEmpty(l_actionErrors, PSFOODT1_05, "PSFOODT1_05","不可空白");
					ve.isNumEmpty(l_actionErrors, PSFOODT1_07+"", "PSFOODT1_07","請輸入數值",true);
					
					if((!"".equals(getPSFOODT1_04()) && getPSFOODT1_04()!=null) && !GenericValidator.maxLength(getPSFOODT1_04(), 50)){//
						l_actionErrors.add("PSFOODT1_04", new ActionMessage(""));
						request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"代碼字數不得超過50個字!!");
					}
					if((!"".equals(getPSFOODT1_05()) && getPSFOODT1_05()!=null) && !GenericValidator.maxLength(getPSFOODT1_05(), 50)){//
						l_actionErrors.add("PSFOODT1_05", new ActionMessage(""));
						request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"代碼名稱字數不得超過50個字!!");
					}
					if((!"".equals(getPSFOODT1_06()) && getPSFOODT1_06()!=null) && !GenericValidator.maxLength(getPSFOODT1_06(), 30)){//
						l_actionErrors.add("PSFOODT1_06", new ActionMessage(""));
						request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"代碼說明字數不得超過30個字!!");
					}
					
					addDetailData_validate(l_actionErrors, request, conn);
				}
				
				//修改時判斷條件
				if (request.getAttribute("action").equals("saveData")) {
					ve.isEmpty(l_actionErrors, PSFOODT0_04, "PSFOODT0_04","不可空白");
					ve.isNumEmpty(l_actionErrors, PSFOODT0_06+"", "PSFOODT0_06","請輸入數值",true);
					ve.isEmpty(l_actionErrors, PSFOODT0_07+"", "PSFOODT0_07","不可空白");
					
					if((!"".equals(getPSFOODT0_04()) && getPSFOODT0_04()!=null) && !GenericValidator.maxLength(getPSFOODT0_04(), 50)){//
						l_actionErrors.add("PSFOODT0_04", new ActionMessage(""));
						request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"食物名稱字數不得超過50個字!!");
					}
					if((!"".equals(getPSFOODT0_05()) && getPSFOODT0_05()!=null) && !GenericValidator.maxLength(getPSFOODT0_05(), 30)){//
						l_actionErrors.add("PSFOODT0_05", new ActionMessage(""));
						request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"食物說明字數不得超過30個字!!");
					}
					
					saveData_validate(l_actionErrors, request, conn);
				}
				
				//刪除時判斷條件
				if (request.getAttribute("action").equals("delData")) {
					delData_validate(l_actionErrors, request, conn);
				}
				
				if (request.getAttribute("action").equals("deleteFOODT1")) {
					deleteFOODT1_validate(l_actionErrors, request, conn);
				}
				
			}
		 return l_actionErrors;
	}
	
	
	private void delData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		String arrid[] = request.getParameterValues("checkId");
		String comp_id = (String) request.getAttribute("compid");
		
		try{
			if(arrid!=null){  //判斷checkbox是否有選
				
				//判斷啟動條件是否為否
				Statement stmt = conn.createStatement();
				String sql = "SELECT PSFOODT0_07 FROM FOODT0 WHERE PSFOODT0_01 = '"
						 +arrid[0]+  "' and PSFOODT0_08 = '"+comp_id+"'";
				ResultSet rs = stmt.executeQuery(sql);
				if (rs.next()) {
					if(rs.getBoolean("PSFOODT0_07")){
						errors.add("", new ActionMessage(""));
						request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+
											 "刪除失敗，" +arrid[0]+ "啟用條件為否才可刪除!!");
					}
				}
				
				//停止才能刪除，在停止時已有判斷是否使用中，可省略下列
				if (selectEHF300000T0Using(arrid[0],conn)) {
						request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+
											 "刪除失敗，" +arrid[0]+ "為正在使用中，不可刪除!!");
				}
				
				//判斷是否為使用中的父類別
				sql = " " +
					" SELECT PSFOODT0_02, PSFOODT0_07 FROM foodt0 " +
					" WHERE PSFOODT0_07=true " +
					" AND PSFOODT0_02='" +arrid[0]+ "'" ;
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
						errors.add("", new ActionMessage(""));
						request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+
											 "刪除失敗，" +arrid[0]+ "為使用中的父類別，不可刪除!!");
				}
				stmt.close();
				rs.close();
				}else{
					request.setAttribute("ERR_MSG", "請先選擇一筆要刪除的資料!!");
				}
			
		}catch (SQLException e) {
			System.out.println("EHF300000M0F.delData_validate()" + e);
		}		
		
	}
	
	private void deleteFOODT1_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		//String arrid[] = request.getParameterValues("checkId");
		String comp_id = (String) request.getAttribute("compid");
		
		try{
			//使用中，不可刪除
			if(this.selectEHF300000T1Using(this.PSFOODT1_04,conn)){
			errors.add("", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+
								 "刪除失敗，" +PSFOODT1_04+ "為正在使用中，不可刪除!!");
			}
			
			//為使用中的父類別
			Statement stmt = conn.createStatement();
			String sql =  " " +
			" SELECT PSFOODT0_03 FROM foodt0 " +
			" WHERE PSFOODT0_07=true " +
			" AND PSFOODT0_03='" +PSFOODT1_04+ "'" ;
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()){
				errors.add("", new ActionMessage(""));
				request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+
									 "刪除失敗，" +PSFOODT1_04+ "為使用中的父類別，不可刪除!!");
			}
			stmt.close();
			rs.close();
		}catch (SQLException e) {
			System.out.println("EHF300000M0F.delData_validate()" + e);
		}
		
	}
	

	private void saveData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		String arrid[] = request.getParameterValues("checkId");
		String comp_id = (String) request.getAttribute("compid");
		try{
			//食物類別名稱不可重複
			Statement stmt = conn.createStatement();
			String sql = "SELECT PSFOODT0_04 FROM FOODT0 WHERE PSFOODT0_01 != '" + PSFOODT0_01 + "' " +
					"AND PSFOODT0_04 = '" + PSFOODT0_04 + "' and PSFOODT0_08 = '"+request.getAttribute("compid")+"'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				errors.add("PSFOODT0_04", new ActionMessage(""));
				request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"食物類別名稱不可重複!!");
			}
			if (!PSFOODT0_07) {
				
				//為使用中啟用條件不能為否
				if (selectEHF300000T0Using(PSFOODT0_01,conn)) {
						errors.add("PSFOODT0_07", new ActionMessage(""));
						request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+
								"儲存失敗，" +PSFOODT0_01+ "為正在使用中，啟用條件不能為否!!");
				}
				
				//為使用中的父類別不能為否
					sql = " " +
					" SELECT PSFOODT0_02  FROM foodt0 " +
					" WHERE PSFOODT0_07=true " +
					" AND PSFOODT0_02='" +PSFOODT0_01+ "'" ;
				rs = stmt.executeQuery(sql);
				if (rs.next()) {
						errors.add("PSFOODT0_01", new ActionMessage(""));
						request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+
											 "儲存失敗，" +PSFOODT0_01+ "為使用中的父類別，不可刪除!!");
				}
			}
			stmt.close();
			rs.close();
		}catch (SQLException e) {
			System.out.println("EHF300000M0F.addData_validate()" + e);
		}		
	}
	
	
	private void addData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		try{
			//食物代碼不可重複
			Statement stmt = conn.createStatement();
			String sql = "SELECT PSFOODT0_01 FROM FOODT0 WHERE PSFOODT0_01 = '"
					+ PSFOODT0_01 + "' and PSFOODT0_08 = '"+request.getAttribute("compid")+"'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				//errors.add("SM010400T0_01",new ActionMessage("此編號已有建立資料,請確認後再建立資料"));
				errors.add("PSFOODT0_01", new ActionMessage(""));
				request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"食物代碼不可重複!!");
			}
			stmt.close();
			rs.close();
		}catch (SQLException e) {
			System.out.println("EHF300000M0F.addData_validate()" + e);
		}
		
		//食物類別名稱不可重複
		try{
			Statement stmt = conn.createStatement();
			String sql = "SELECT PSFOODT0_04 FROM FOODT0 WHERE PSFOODT0_04 = '"
					+ PSFOODT0_04 + "' and PSFOODT0_08 = '"+request.getAttribute("compid")+"'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				errors.add("PSFOODT0_04", new ActionMessage(""));
				request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"食物類別名稱不可重複!!");
			}
			stmt.close();
			rs.close();
		}catch (SQLException e) {
			System.out.println("EHF300000M0F.addData_validate()" + e);
		}
		
//		try{
//			Statement stmt = conn.createStatement();
//			String sql = "SELECT PSFOODT0_06 FROM FOODT0 WHERE PSFOODT0_06 = '"
//					+ PSFOODT0_06 + "' and PSFOODT0_08 = '"+request.getAttribute("compid")+"'";
//			ResultSet rs = stmt.executeQuery(sql);
//			if (rs.next()) {
//				
//				errors.add("PSFOODT0_06", new ActionMessage(""));
//				request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"顯示順序不可重複!!");
//			}
//			stmt.close();
//			rs.close();
//		}catch (SQLException e) {
//			System.out.println("EHF300000M0F.addData_validate()" + e);
//		}
	}
	
	
	private void addDetailData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		try{
			//代碼不可重複
			Statement stmt = conn.createStatement();
			String sql = "SELECT PSFOODT1_04 FROM FOODT1 WHERE PSFOODT1_04 = '"
					+ PSFOODT1_04 + "' and PSFOODT1_09 = '"+request.getAttribute("compid")+"'" +
					" AND PSFOODT1_01= '" +PSFOODT0_01+ "' " ;
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				errors.add("PSFOODT1_04", new ActionMessage(""));
				request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"代碼不可重複!!");
			}
			stmt.close();
			rs.close();
		}catch (SQLException e) {
			System.out.println("EHF300000M0F.addDetailData_validate()" + e);
		}
		
		try{
			Statement stmt = conn.createStatement();
			String sql = "SELECT PSFOODT1_05 FROM FOODT1 WHERE PSFOODT1_05 = '"
					+ PSFOODT1_05 + "' and PSFOODT1_09 = '"+request.getAttribute("compid")+"'" +
					" AND PSFOODT1_01= '" +PSFOODT0_01+ "' " ;
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				errors.add("PSFOODT1_05", new ActionMessage(""));
				request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"代碼名稱不可重複!!");
			}
			stmt.close();
			rs.close();
		}catch (SQLException e) {
			System.out.println("EHF300000M0F.addDetailData_validate()" + e);
		}
		
		
		if(!errors.isEmpty()){
		
			try{
				/*
				 * 取得之前作業的錯誤訊息參數
				 * 避免因為前面的方法處理中已發生檢核錯誤 -> 最後執行 return queryDatas() 時
				 * 因 queryDatas() 不知已發生檢核錯誤, 執行了查詢動作造成使用者填寫的資料消失
				 * 由於 EditAction 中此一功能的實現，導致頁面在檢核出errors時若已有明細資料，
				 * 則已有明細資料會顯示不出來，故需要以下程式來抓取明細資料
				 */
				EHF300000 EHF300000 = new EHF300000();
			
				//建立查詢資料Map
				Map queryCondMap = new HashMap();
				queryCondMap.put("PSFOODT0_01", PSFOODT0_01);  // 食物代碼
			
				setEHF300000_DETAIL(EHF300000.queryLexiconDList(queryCondMap));
			
				EHF300000.close();
			
			}catch (Exception e) {
				System.out.println("EHF300000M0F.addDetailData_validate()" + e);
			}
		}
	}

	
	//判斷其他地方是否使用有使用中的食物代碼
	public boolean selectEHF300000T0Using(String FoodID, Connection conn) {
		String sql ="";
		boolean chkFlag = false;
		try{
			Statement stmt = conn.createStatement();
			
			sql = "" +
			"SELECT PSFOODT0_01,EHF320100T3_04,EHF320100T5_04 FROM FOODT0 " +
			"LEFT OUTER JOIN EHF320100T3 " +
			"ON EHF320100T3_04=PSFOODT0_01 " +
			"LEFT OUTER JOIN EHF320100T5 " +
			"ON EHF320100T5_04=PSFOODT0_01 " +
			"WHERE EHF320100T3_04=PSFOODT0_01 " +
			"AND PSFOODT0_01 ='" +FoodID+ "' " +
			"OR EHF320100T5_04=PSFOODT0_01 " ;
			ResultSet rs = stmt.executeQuery(sql);
			//System.out.println(sql);
			if (rs.next())
				chkFlag = true;
			else
				chkFlag = false;
		}catch(Exception e){		
			e.printStackTrace();
		}		
		return chkFlag;
	}
	
	
	public boolean selectEHF300000T1Using(String FoodIDdetail, Connection conn) {
		String sql ="";
		boolean chkFlag = false;
		try{
			Statement stmt = conn.createStatement();
			
			sql = "" +
			" SELECT PSFOODT1_04,EHF320100T3_05,EHF320100T5_05 FROM FOODT1 " +
			" LEFT OUTER JOIN EHF320100T3 " +
			" ON EHF320100T3_05=PSFOODT1_04 " +
			" LEFT OUTER JOIN EHF320100T5 " +
			" ON EHF320100T5_05=PSFOODT1_04 " +
			" WHERE EHF320100T3_05=PSFOODT1_04 " +
			" AND PSFOODT1_04 ='" +FoodIDdetail+ "' " +
			" OR EHF320100T5_05=PSFOODT1_04  " ;

			ResultSet rs = stmt.executeQuery(sql);
//			System.out.println(sql);
			if (rs.next())
				chkFlag = true;
			else
				chkFlag = false;
		}catch(Exception e){		
			e.printStackTrace();
		}		
		return chkFlag;
	}
	
		
	

	public void setPSFOODT0_01(String pSFOODT0_01) {
		PSFOODT0_01 = pSFOODT0_01;
	}

	
	public String getPSFOODT0_01() {
		return PSFOODT0_01;
	}



	public void setPSFOODT0_02(String pSFOODT0_02) {
		PSFOODT0_02 = pSFOODT0_02;
	}



	public String getPSFOODT0_02() {
		return PSFOODT0_02;
	}



	public void setPSFOODT0_03(String pSFOODT0_03) {
		PSFOODT0_03 = pSFOODT0_03;
	}



	public String getPSFOODT0_03() {
		return PSFOODT0_03;
	}



	public void setPSFOODT0_04(String pSFOODT0_04) {
		PSFOODT0_04 = pSFOODT0_04;
	}



	public String getPSFOODT0_04() {
		return PSFOODT0_04;
	}



	public void setPSFOODT0_05(String pSFOODT0_05) {
		PSFOODT0_05 = pSFOODT0_05;
	}



	public String getPSFOODT0_05() {
		return PSFOODT0_05;
	}



	public void setPSFOODT0_06(int pSFOODT0_06) {
		PSFOODT0_06 = pSFOODT0_06;
	}



	public int getPSFOODT0_06() {
		return PSFOODT0_06;
	}



	public void setPSFOODT0_07(boolean pSFOODT0_07) {
		PSFOODT0_07 = pSFOODT0_07;
	}



	public boolean getPSFOODT0_07() {
		return PSFOODT0_07;
	}



	public void setPSFOODT0_08(String pSFOODT0_08) {
		PSFOODT0_08 = pSFOODT0_08;
	}



	public String getPSFOODT0_08() {
		return PSFOODT0_08;
	}



	public void setUSER_CREATE(String uSER_CREATE) {
		USER_CREATE = uSER_CREATE;
	}



	public String getUSER_CREATE() {
		return USER_CREATE;
	}



	public void setUSER_UPDATE(String uSER_UPDATE) {
		USER_UPDATE = uSER_UPDATE;
	}



	public String getUSER_UPDATE() {
		return USER_UPDATE;
	}



	public void setVERSION(int vERSION) {
		VERSION = vERSION;
	}



	public int getVERSION() {
		return VERSION;
	}



	public void setDATE_UPDATE(String dATE_UPDATE) {
		DATE_UPDATE = dATE_UPDATE;
	}



	public String getDATE_UPDATE() {
		return DATE_UPDATE;
	}


////
	
	
	public void setPSFOODT1_01(String pSFOODT1_01) {
		PSFOODT1_01 = pSFOODT1_01;
	}



	public String getPSFOODT1_01() {
		return PSFOODT1_01;
	}



	public void setPSFOODT1_02(String pSFOODT1_02) {
		PSFOODT1_02 = pSFOODT1_02;
	}



	public String getPSFOODT1_02() {
		return PSFOODT1_02;
	}



	public void setPSFOODT1_03(String pSFOODT1_03) {
		PSFOODT1_03 = pSFOODT1_03;
	}



	public String getPSFOODT1_03() {
		return PSFOODT1_03;
	}



	public void setPSFOODT1_04(String pSFOODT1_04) {
		PSFOODT1_04 = pSFOODT1_04;
	}



	public String getPSFOODT1_04() {
		return PSFOODT1_04;
	}
	

	public void setPSFOODT1_05(String pSFOODT1_05) {
		PSFOODT1_05 = pSFOODT1_05;
	}



	public String getPSFOODT1_05() {
		return PSFOODT1_05;
	}



	public void setPSFOODT1_06(String pSFOODT1_06) {
		PSFOODT1_06 = pSFOODT1_06;
	}



	public String getPSFOODT1_06() {
		return PSFOODT1_06;
	}



	public void setPSFOODT1_07(int pSFOODT1_07) {
		PSFOODT1_07 = pSFOODT1_07;
	}



	public int getPSFOODT1_07() {
		return PSFOODT1_07;
	}



	public void setPSFOODT1_08(boolean pSFOODT1_08) {
		PSFOODT1_08 = pSFOODT1_08;
	}



	public boolean getPSFOODT1_08() {
		return PSFOODT1_08;
	}



	public void setPSFOODT1_09(String pSFOODT1_09) {
		PSFOODT1_09 = pSFOODT1_09;
	}



	public String getPSFOODT1_09() {
		return PSFOODT1_09;
	}



	public void setPSFOODT0_LIST(List pSFOODT0_LIST) {
		PSFOODT0_LIST = pSFOODT0_LIST;
	}



	public List getPSFOODT0_LIST() {
		return PSFOODT0_LIST;
	}



	public void setEHF300000_DETAIL(List eHF300000_DETAIL) {
		EHF300000_DETAIL = eHF300000_DETAIL;
	}



	public List getEHF300000_DETAIL() {
		return EHF300000_DETAIL;
	}



	public void setListPSFOODT0_02(List listPSFOODT0_02) {
		this.listPSFOODT0_02 = listPSFOODT0_02;
	}



	public List getListPSFOODT0_02() {
		return listPSFOODT0_02;
	}



	public void setSpace(String space) {
		this.space = space;
	}



	public String getSpace() {
		return space;
	}



	public void setPSFOODT0_07_TXT(String pSFOODT0_07_TXT) {
		PSFOODT0_07_TXT = pSFOODT0_07_TXT;
	}



	public String getPSFOODT0_07_TXT() {
		return PSFOODT0_07_TXT;
	}


	public void setEnable_list(boolean b) {
		// TODO Auto-generated method stub
		
	}


	
}
