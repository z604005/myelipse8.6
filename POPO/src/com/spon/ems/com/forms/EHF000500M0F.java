package com.spon.ems.com.forms;

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

import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;

public class EHF000500M0F extends ActionForm {
	private static final long serialVersionUID = 1L;
	private boolean CHECKED;
	private boolean CHANGED;

	private  String  EHF000500T0_01;	//公司行事曆序號
	private  String  EHF000500T0_02;	//公司組織(代號)
	private  String  EHF000500T0_03;	//年度(yy)--西元年
	private  String  EHF000500T0_04;	//休假類別
	private  String  EHF000500T0_05;	//休假日期 起( YYYYMMDD )
	private  String  EHF000500T0_06;	//休假日期 迄( YYYYMMDD )
	private  String  EHF000500T0_07;	//休假時間 起( 時/分 )
	private  String  EHF000500T0_07_HH;
	private  String  EHF000500T0_07_MM;
	private  String  EHF000500T0_08;	//休假時間 迄( 時/分 )
	private  String  EHF000500T0_08_HH;
	private  String  EHF000500T0_08_MM;
	private  String  EHF000500T0_09;	//休假原因
	private  String  EHF000500T0_10;	//處理人事
	private  String  EHF000500T0_11;	//公司代碼
	private  String  EHF000500T0_12;	//大小禮拜    ""：不選擇，1：大禮拜，2：小禮拜
	
	private FormFile IMP_EHF000501;
	
	private  String  USER_CREATE;
	private  String  USER_UPDATE;
	private   int  VERSION;
	private  String  DATE_UPDATE;



	private List EHF000501C=new ArrayList();

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;
	}
	public ActionErrors validate(ActionMapping mapping,	HttpServletRequest request, Connection conn) {
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		BA_Vaildate ve=BA_Vaildate.getInstance();
		
		ve.isEmpty(l_actionErrors, EHF000500T0_03, "EHF000500T0_03", "不可空白");	//年度(yy)--西元年
		ve.isEmpty(l_actionErrors, EHF000500T0_04, "EHF000500T0_04", "不可空白");	//休假類別
		ve.isEmpty(l_actionErrors, EHF000500T0_05, "EHF000500T0_05", "不可空白"); 	//起始日期不可空白
		ve.isEmpty(l_actionErrors, EHF000500T0_06, "EHF000500T0_06", "不可空白"); 	//結束日期不可空白
		
		if (l_actionErrors.isEmpty()) {
			//新增時判斷條件
			if (request.getAttribute("action").equals("addData")) {
				
				addData_validate(l_actionErrors, request, conn);
			}

			//	     修改時判斷條件
			if (request.getAttribute("action").equals("saveData")) {
				saveData_validate(l_actionErrors, request, conn);
			}
		}

		return l_actionErrors;
	}
	private void saveData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		String sql="";
		try {
	
		String dayStart[]=EHF000500T0_05.split("/");
		String dayEnd[]  =EHF000500T0_06.split("/");
		
		
		//劦佑不需要這一段
//		sql = "" +
//		" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02" +
//		" FROM EHF020900T0 " +
//		" WHERE 1=1 " +
//		"  AND (EHF020900T0_M2 BETWEEN '"+dayStart[0]+"/"+dayStart[1]+ "' AND '"+dayEnd[0]+"/"+dayEnd[1]+"')"+	//考勤年月
//	
//		" AND EHF020900T0_04 = '"+request.getAttribute("compid")+"'" +//公司代碼
//		" AND (EHF020900T0_02 = '02' OR EHF020900T0_02 = '03')"; //已經確認考勤
//
//		Statement stmt_01 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
//		ResultSet rs_01 = stmt_01.executeQuery(sql);
//	
//		while(rs_01.next()){
//			if("02".equals(rs_01.getString("EHF020900T0_02"))||"03".equals(rs_01.getString("EHF020900T0_02")))
//				errors.add("EHF000500T0_05",new ActionMessage(""));  
//				errors.add("EHF000500T0_06",new ActionMessage(""));  
//				request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
//					rs_01.getString("EHF020900T0_M2")+"已經確認考勤!請再次確認"+" ");
//		
//		}
//		rs_01.close();
//		stmt_01.close();
		
	
	
		BA_TOOLS tools = BA_TOOLS.getInstance();
	
	
		Calendar start_cal = tools.covertStringToCalendar(EHF000500T0_05);  //計算開始日期
		Calendar end_cal = tools.covertStringToCalendar(EHF000500T0_06);  //計算結束日期
	
		if(start_cal.compareTo(end_cal)>0){
			errors.add("EHF000500T0_05",new ActionMessage(""));  
			errors.add("EHF000500T0_06",new ActionMessage(""));
			request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"日期錯誤，請檢查!!"+" ");
		}
	
		//驗證輸入的年度與(起)(訖)年度相同
		int EHF000500T0_03_number= Integer.valueOf(EHF000500T0_03);//年度
		int EHF000500T0_05_year_number= Integer.valueOf(dayStart[0]);//休假日期(起)年度
		int EHF000500T0_06_year_number=Integer.valueOf(dayEnd[0]);
	
		if(EHF000500T0_03_number!=(EHF000500T0_05_year_number-1911))//年度
		{
			errors.add("EHF000500T0_05",new ActionMessage(""));  
			errors.add("EHF000500T0_06",new ActionMessage(""));
			request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"休假日期(起)年度請與年度相同!!"+" ");
			
		}
	
	
		if(EHF000500T0_03_number!=(EHF000500T0_06_year_number-1911))//年度
		{

			errors.add("EHF000500T0_05",new ActionMessage(""));  
			errors.add("EHF000500T0_06",new ActionMessage(""));
			request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"休假日期(迄)年度請與年度相同!!"+" ");
		}
		
		
		} catch (Exception e) {
			request.setAttribute("MSG", "儲存失敗!");
			System.out.println("EHF000501M0A.queryDatas() " + e);
			System.out.println(e);
		}
		
		
		
	}
	private void addData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub	
		try {
			String sql = ""
					+ " SELECT EHF000500T0.*, "
					+ " DATE_FORMAT(EHF000500T0_05, '%Y年%c月%e日') AS START_DATE, "
					+ " DATE_FORMAT(EHF000500T0_06, '%Y年%c月%e日') AS END_DATE "
					+ " FROM EHF000500T0 " 
					+ " WHERE 1=1 "
					+ " AND EHF000500T0_11 = '"	+ request.getAttribute("compid") + "' ";

			// 年度
			if (!"".equals(EHF000500T0_03)&& EHF000500T0_03 != null) {
				sql += " AND EHF000500T0_03 = '" + EHF000500T0_03	+ "' ";
			}
			// 休假類別
			if (!"".equals(EHF000500T0_04)&& EHF000500T0_04 != null) {
				sql += " AND EHF000500T0_04 = '" + EHF000500T0_04+ "' ";
			}
			// 休假日期區間
			if (!"".equals(EHF000500T0_05)&& EHF000500T0_05 != null	&& 
				!"".equals(EHF000500T0_06)&& EHF000500T0_06 != null) {
				//sql += " AND EHF000500T0_05 >= '" + EHF000500T0_05 + "' " 
				//     + " AND EHF000500T0_06 <= '" + EHF000500T0_06 + "' ";
				sql += " AND  '" + EHF000500T0_05 + "' between EHF000500T0_05 AND EHF000500T0_06"
					+  " AND  '" + EHF000500T0_06 + "' between EHF000500T0_05 AND EHF000500T0_06";
				
				
			} else if (!"".equals(EHF000500T0_06)&& EHF000500T0_06 != null) {
				//sql += " AND EHF000500T0_06 <= '" + EHF000500T0_06 + "' ";
				sql += " AND  '" + EHF000500T0_06 + "' between EHF000500T0_05 AND EHF000500T0_06";
			} else if (!"".equals(EHF000500T0_05)&& EHF000500T0_05 != null) {
				//sql += " AND EHF000500T0_05 >= '" + EHF000500T0_05+ "' ";
				sql += " AND  '" + EHF000500T0_05 + "' between EHF000500T0_05 AND EHF000500T0_06";
			}

			sql += " ORDER BY EHF000500T0_03, EHF000500T0_05, EHF000500T0_07, "
					+ " EHF000500T0_06, EHF000500T0_08, EHF000500T0_04 ";

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,	ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {
				//l_actionErrors.add("EHF030600T0_M",new ActionMessage(""));  //入扣帳年月
				errors.add("EHF000500T0_05",new ActionMessage(""));  
				errors.add("EHF000500T0_06",new ActionMessage(""));  
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						" 時間重複,請檢查!! ");
			}
			rs.close();
			stmt.close();
			
			
			String dayStart[]=EHF000500T0_05.split("/");
			String dayEnd[]  =EHF000500T0_06.split("/");
			
//			sql = "" +
//			" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02" +
//			" FROM EHF020900T0 " +
//			" WHERE 1=1 " +
//			"  AND (EHF020900T0_M2 BETWEEN '"+dayStart[0]+"/"+dayStart[1]+ "' AND '"+dayEnd[0]+"/"+dayEnd[1]+"')"+	//考勤年月
//			
//			" AND EHF020900T0_04 = '"+request.getAttribute("compid")+"'" +//公司代碼
//			" AND (EHF020900T0_02 = '02' OR EHF020900T0_02 = '03')"; //已經確認考勤
//
//	    	Statement stmt_01 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
//			ResultSet rs_01 = stmt_01.executeQuery(sql);
//			
//			while(rs_01.next()){
//				if("02".equals(rs_01.getString("EHF020900T0_02"))||"03".equals(rs_01.getString("EHF020900T0_02")))
//					errors.add("EHF000500T0_05",new ActionMessage(""));  
//					errors.add("EHF000500T0_06",new ActionMessage(""));  
//					request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
//							
//							rs_01.getString("EHF020900T0_M2")+"已經確認考勤!請再次確認"+" ");
//				
//			}
//			rs_01.close();
//			stmt_01.close();
			
			
			BA_TOOLS tools = BA_TOOLS.getInstance();
			
			
			Calendar start_cal = tools.covertStringToCalendar(EHF000500T0_05);  //計算開始日期
			Calendar end_cal = tools.covertStringToCalendar(EHF000500T0_06);  //計算結束日期
			
			if(start_cal.compareTo(end_cal)>0){
				errors.add("EHF000500T0_05",new ActionMessage(""));  
				errors.add("EHF000500T0_06",new ActionMessage(""));
				request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"日期錯誤，請檢查!!"+" ");
			}
			
			//驗證輸入的年度與(起)(訖)年度相同
			int EHF000500T0_03_number= Integer.valueOf(EHF000500T0_03);//年度
			int EHF000500T0_05_year_number= Integer.valueOf(dayStart[0]);//休假日期(起)年度
			int EHF000500T0_06_year_number=Integer.valueOf(dayEnd[0]);
			
			if(EHF000500T0_03_number!=(EHF000500T0_05_year_number-1911))//年度
			{
				errors.add("EHF000500T0_05",new ActionMessage(""));  
				errors.add("EHF000500T0_06",new ActionMessage(""));
				request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"休假日期(起)年度請與年度相同!!"+" ");
					
			}
			
			
			if(EHF000500T0_03_number!=(EHF000500T0_06_year_number-1911))//年度
			{

				errors.add("EHF000500T0_05",new ActionMessage(""));  
				errors.add("EHF000500T0_06",new ActionMessage(""));
				request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"休假日期(迄)年度請與年度相同!!"+" ");
			}
			
			
			

		} catch (Exception e) {
			request.setAttribute("MSG", "新增失敗!");
			System.out.println("EHF000501M0A.queryDatas() " + e);
			System.out.println(e);
		}
		
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("utf-8");
			EHF000501C = ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new EHF000500M0F();
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
	public String getEHF000500T0_01() {
		return EHF000500T0_01;
	}
	public void setEHF000500T0_01(String eHF000500T0_01) {
		EHF000500T0_01 = eHF000500T0_01;
	}
	public String getEHF000500T0_02() {
		return EHF000500T0_02;
	}
	public void setEHF000500T0_02(String eHF000500T0_02) {
		EHF000500T0_02 = eHF000500T0_02;
	}
	public String getEHF000500T0_03() {
		return EHF000500T0_03;
	}
	public void setEHF000500T0_03(String eHF000500T0_03) {
		EHF000500T0_03 = eHF000500T0_03;
	}
	public String getEHF000500T0_04() {
		return EHF000500T0_04;
	}
	public void setEHF000500T0_04(String eHF000500T0_04) {
		EHF000500T0_04 = eHF000500T0_04;
	}
	public String getEHF000500T0_05() {
		return EHF000500T0_05;
	}
	public void setEHF000500T0_05(String eHF000500T0_05) {
		EHF000500T0_05 = eHF000500T0_05;
	}
	public String getEHF000500T0_06() {
		return EHF000500T0_06;
	}
	public void setEHF000500T0_06(String eHF000500T0_06) {
		EHF000500T0_06 = eHF000500T0_06;
	}
	public String getEHF000500T0_07() {
		return EHF000500T0_07;
	}
	public void setEHF000500T0_07(String eHF000500T0_07) {
		EHF000500T0_07 = eHF000500T0_07;
	}
	public String getEHF000500T0_07_HH() {
		return EHF000500T0_07_HH;
	}
	public void setEHF000500T0_07_HH(String eHF000500T0_07HH) {
		EHF000500T0_07_HH = eHF000500T0_07HH;
	}
	public String getEHF000500T0_07_MM() {
		return EHF000500T0_07_MM;
	}
	public void setEHF000500T0_07_MM(String eHF000500T0_07MM) {
		EHF000500T0_07_MM = eHF000500T0_07MM;
	}
	public String getEHF000500T0_08() {
		return EHF000500T0_08;
	}
	public void setEHF000500T0_08(String eHF000500T0_08) {
		EHF000500T0_08 = eHF000500T0_08;
	}
	public String getEHF000500T0_08_HH() {
		return EHF000500T0_08_HH;
	}
	public void setEHF000500T0_08_HH(String eHF000500T0_08HH) {
		EHF000500T0_08_HH = eHF000500T0_08HH;
	}
	public String getEHF000500T0_08_MM() {
		return EHF000500T0_08_MM;
	}
	public void setEHF000500T0_08_MM(String eHF000500T0_08MM) {
		EHF000500T0_08_MM = eHF000500T0_08MM;
	}
	public String getEHF000500T0_09() {
		return EHF000500T0_09;
	}
	public void setEHF000500T0_09(String eHF000500T0_09) {
		EHF000500T0_09 = eHF000500T0_09;
	}
	public String getEHF000500T0_10() {
		return EHF000500T0_10;
	}
	public void setEHF000500T0_10(String eHF000500T0_10) {
		EHF000500T0_10 = eHF000500T0_10;
	}
	public String getEHF000500T0_11() {
		return EHF000500T0_11;
	}
	public void setEHF000500T0_11(String eHF000500T0_11) {
		EHF000500T0_11 = eHF000500T0_11;
	}
	public String getEHF000500T0_12() {
		return EHF000500T0_12;
	}
	public void setEHF000500T0_12(String eHF000500T0_12) {
		EHF000500T0_12 = eHF000500T0_12;
	}
	public FormFile getIMP_EHF000501() {
		return IMP_EHF000501;
	}
	public void setIMP_EHF000501(FormFile iMPEHF000501) {
		IMP_EHF000501 = iMPEHF000501;
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
	public String getDATE_UPDATE() {
		return DATE_UPDATE;
	}
	public void setDATE_UPDATE(String dATEUPDATE) {
		DATE_UPDATE = dATEUPDATE;
	}
	public List getEHF000501C() {
		return EHF000501C;
	}
	public void setEHF000501C(List eHF000501C) {
		EHF000501C = eHF000501C;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}