package com.spon.utils.sc.actions;

import org.apache.struts.action.ActionForward;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.struts.NewDispatchAction;
import com.spon.utils.sc.forms.SC010F;
import com.spon.utils.struts.form.BA_REPORTF;
import com.spon.utils.util.BA_TOOLS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.quartz.utils.DBConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * 單位資料維護
 * @version 1.01006 下午 01:09:56
 */
public class SC011A extends NewDispatchAction {

	static HttpServletRequest request1 = null ;
	public SC011A(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * 儲存新增資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward addCompany(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		BA_REPORTF Form = (BA_REPORTF) form;
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		String sql = "";
		try {
			conn = tools.getConnection();
        } catch (Exception e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        
        request.setAttribute("action","addData");
		ActionErrors lc_errors = Form.validate(mapping, request, conn);
		
		
			
			try {
				
//				PreparedStatement stmt = null;
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				
				if (lc_errors.isEmpty()) {
					
				//新增公司資料  FM010001
					
				/*String COMID = BA_TOOLS.getInstance().createCompanyUID(conn,
							"A", "FM010001", "FM010001_01", request);  //產生公司代碼 COMID
				COMID=COMID.substring(7);*/  //取最後四碼作為公司代碼
				String COMID="COM"+Form.getDATA01();
				
				sql = " INSERT INTO EHF000100T0(HR_CompanySysNo,EHF000100T0_01,EHF000100T0_02,EHF000100T0_03,EHF000100T0_04,EHF000100T0_05,EHF000100T0_06, " +
				   						   " EHF000100T0_07,EHF000100T0_08,EHF000100T0_09,EHF000100T0_10,EHF000100T0_11,EHF000100T0_12, " +
				   						   " EHF000100T0_13,EHF000100T0_14,EHF000100T0_15,EHF000100T0_16,EHF000100T0_17,EHF000100T0_18, " +
				   						   " EHF000100T0_19,EHF000100T0_20,DATE_CREATE,HR_LastUpdateDate,USER_CREATE,USER_UPDATE, " +
				   						   " VERSION) " +
				   						   " values ('"+COMID+"','','"+Form.getDATA01()+"','','','',NOW(),'','','','','','','','','','','','','','', " +
				   						   " NOW(),NOW(),'SPONADMIN','SPONADMIN',1) ";
				stmt.execute(sql);
				
				//新增使用者管理資料  SC0030
				
				//spadmin ,  spos# admin
				sql = " INSERT INTO `SC0030`(`SC0030_01`,`SC0030_02`,`SC0030_03`,`SC0030_04`,`SC0030_05`, " +
										   " `SC0030_06`,`SC0030_07`,`SC0030_08`,`SC0030_09`,`SC0030_10`, " +
										   " `SC0030_11`,`SC0030_12`,`DATE_CREATE`,`DATE_UPDATE`,`USER_CREATE`, " +
										   " `USER_UPDATE`,`VERSION`,`SC0030_13`,`SC0030_14`) " +
										   " values ('spadmin','ae1084a7897bba230efe4802b779b05f','','管理者','Y', " +//sponTECH
										   " '','00',0,NOW(), '','','',NOW(),NOW(),'SPONADMIN','SPONADMIN', '1', '','"+COMID+"') ";
				stmt.execute(sql);
				
				//USER1   ,  12345678
				sql = " INSERT INTO `SC0030`(`SC0030_01`,`SC0030_02`,`SC0030_03`,`SC0030_04`,`SC0030_05`, " +
										   " `SC0030_06`,`SC0030_07`,`SC0030_08`,`SC0030_09`,`SC0030_10`, " +
										   " `SC0030_11`,`SC0030_12`,`DATE_CREATE`,`DATE_UPDATE`,`USER_CREATE`, " +
										   " `USER_UPDATE`,`VERSION`,`SC0030_13`,`SC0030_14`) " +
										   " values ('USER1','25d55ad283aa400af464c76d713c07ad','','使用者1','Y', " +
										   " '','00',0,NOW(), '','','', NOW(),NOW(),'SPONADMIN','SPONADMIN', '1', '', '"+COMID+"') ";
				stmt.execute(sql);
				
				//USER2   ,  12345678
				sql = " INSERT INTO `SC0030`(`SC0030_01`,`SC0030_02`,`SC0030_03`,`SC0030_04`,`SC0030_05`, " +
										   " `SC0030_06`,`SC0030_07`,`SC0030_08`,`SC0030_09`,`SC0030_10`, " +
										   " `SC0030_11`,`SC0030_12`,`DATE_CREATE`,`DATE_UPDATE`,`USER_CREATE`, " +
										   " `USER_UPDATE`,`VERSION`,`SC0030_13`,`SC0030_14`) " +
										   " values ('USER2','25d55ad283aa400af464c76d713c07ad','','使用者2','Y', " +
										   " '','00',0,NOW(), '','','',NOW(),NOW(),'SPONADMIN','SPONADMIN', '1', '', '"+COMID+"') ";
				stmt.execute(sql);
				
				//USER3   ,  12345678
				sql = " INSERT INTO `SC0030`(`SC0030_01`,`SC0030_02`,`SC0030_03`,`SC0030_04`,`SC0030_05`, " +
										   " `SC0030_06`,`SC0030_07`,`SC0030_08`,`SC0030_09`,`SC0030_10`, " +
										   " `SC0030_11`,`SC0030_12`,`DATE_CREATE`,`DATE_UPDATE`,`USER_CREATE`, " +
										   " `USER_UPDATE`,`VERSION`,`SC0030_13`,`SC0030_14`) " +
										   " values ('USER3','25d55ad283aa400af464c76d713c07ad','','使用者3','Y', " +
										   " '','00',0,NOW(), '','','',NOW(),NOW(),'SPONADMIN','SPONADMIN', '1', '', '"+COMID+"') ";
				stmt.execute(sql);
				
				//新增使用者群組 SC0031
				
				//GROUP00
				sql = " INSERT INTO `SC0031`(`SC0031_01`,`SC0031_02`,`SC0031_03`,`DATE_CREATE`,`DATE_UPDATE`, " +
										   " `USER_CREATE`,`USER_UPDATE`,`VERSION`,`SC0031_04`) " +
										   " values ('spadmin','000','',NOW(),NOW(),'SPONADMIN','SPONADMIN',1,'"+COMID+"') ";
				stmt.execute(sql);
				
				//GROUP1
				sql = " INSERT INTO `SC0031`(`SC0031_01`,`SC0031_02`,`SC0031_03`,`DATE_CREATE`,`DATE_UPDATE`, " +
										   " `USER_CREATE`,`USER_UPDATE`,`VERSION`,`SC0031_04`) " +
										   " values ('USER1','001','',NOW(),NOW(),'SPONADMIN','SPONADMIN',1,'"+COMID+"') ";
				stmt.execute(sql);
				
				//GROUP2
				sql = " INSERT INTO `SC0031`(`SC0031_01`,`SC0031_02`,`SC0031_03`,`DATE_CREATE`,`DATE_UPDATE`, " +
										   " `USER_CREATE`,`USER_UPDATE`,`VERSION`,`SC0031_04`) " +
										   " values ('USER2','002','',NOW(),NOW(),'SPONADMIN','SPONADMIN',1,'"+COMID+"') ";
				stmt.execute(sql);
				
				//GROUP3
				sql = " INSERT INTO `SC0031`(`SC0031_01`,`SC0031_02`,`SC0031_03`,`DATE_CREATE`,`DATE_UPDATE`, " +
										   " `USER_CREATE`,`USER_UPDATE`,`VERSION`,`SC0031_04`) " +
										   " values ('USER3','003','',NOW(),NOW(),'SPONADMIN','SPONADMIN',1,'"+COMID+"') ";
				stmt.execute(sql);
				
				//新增程式模組資料  SC0020
				
				//序號維護模組
//				sql = " INSERT INTO `SC0020`(`SC0020_01`,`SC0020_02`,`SC0020_03`,`SC0020_04`,`SC0020_05`, " +
//										   " `SC0020_06`,`DATE_CREATE`,`DATE_UPDATE`,`USER_CREATE`,`USER_UPDATE`, " +
//										   " `VERSION`,`SC0020_07`,`SC0020_08`) " +
//										   " values ('SC005','序號維護','SC005.do','SC','點選執行:序號維護',50,NOW(),NOW(), " +
//										   " 'SPONADMIN','SPONADMIN',1,'N','"+COMID+"') ";
//				stmt.execute(sql);
				
				//新增群組權限  SC0010
				
				//群組代碼
				
				//000
				sql = " INSERT INTO `SC0010`(`SC0010_01`,`SC0010_02`,`SC0010_03`,`DATE_CREATE`,`DATE_UPDATE`, " +
										   " `USER_CREATE`,`USER_UPDATE`,`VERSION`,`SC0010_04`) " +
										   " values ('000','管理者','',NOW(),NOW(),'SPONADMIN','SPONADMIN',1, '"+COMID+"') ";
				stmt.execute(sql);
				
				//001
				sql = " INSERT INTO `SC0010`(`SC0010_01`,`SC0010_02`,`SC0010_03`,`DATE_CREATE`,`DATE_UPDATE`, " +
										   " `USER_CREATE`,`USER_UPDATE`,`VERSION`,`SC0010_04`) " +
										   " values ('001','使用者1','',NOW(),NOW(),'SPONADMIN','SPONADMIN',1, '"+COMID+"') ";
				stmt.execute(sql);
				
				//002
				sql = " INSERT INTO `SC0010`(`SC0010_01`,`SC0010_02`,`SC0010_03`,`DATE_CREATE`,`DATE_UPDATE`, " +
										   " `USER_CREATE`,`USER_UPDATE`,`VERSION`,`SC0010_04`) " +
										   " values ('002','使用者2','',NOW(),NOW(),'SPONADMIN','SPONADMIN',1, '"+COMID+"') ";
				stmt.execute(sql);
				
				//003
				sql = " INSERT INTO `SC0010`(`SC0010_01`,`SC0010_02`,`SC0010_03`,`DATE_CREATE`,`DATE_UPDATE`, " +
										   " `USER_CREATE`,`USER_UPDATE`,`VERSION`,`SC0010_04`) " +
										   " values ('003','使用者3','',NOW(),NOW(),'SPONADMIN','SPONADMIN',1, '"+COMID+"') ";
				stmt.execute(sql);
				
				//零售客戶(for 門市銷售)
				/*sql = " INSERT INTO `FM010201`(`FM010201_01`,`FM010201_02`,`FM010201_06`,`FM010201_16`,`FM010201_17`,`FM010201_19`, " +
										   "`FM010201_20`,`FM010201_21`,`FM010201_22`,`USER_CREATE`,`USER_UPDATE`,`VERSION`,`DATE_CREATE`,`DATE_UPDATE`) " +
										   " values ('CUST9999','零售客戶','00000000','01','02','A','0','"+COMID+"','1','SPONADMIN','SPONADMIN',1,NOW(),NOW()) ";
				stmt.execute(sql);*/
				
				//新增權限設定表  SC0011
				
				String dataListSC0011[][] = {{"000","SC","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},         //GROUP 000
									   		{"000","SC001","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		{"000","SC002","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		{"000","SC003B","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
//									   		{"000","SC004","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		{"000","SC005","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		{"000","SC006","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		{"000","SC007","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		{"000","EMS_CategoryM0","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		{"000","COM","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		{"000","EHF000100M0","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		{"000","EHF000200M0","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		{"000","EHF000300M0","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		{"000","EHF000400M0","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		{"000","HR","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		{"000","EHF010100M0","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		{"000","EHF010101M0","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		{"000","EHF010106M0","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		//{"000","EHF010107M0","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		//{"000","EHF010108M0","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		{"000","EHF010110M0","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		{"000","EHF010111M0","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		{"000","EHF010112M0","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		{"000","EHF010113M0","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		{"000","EHF010114M0","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		//{"000","EHF010109M0","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		{"000","SALARY","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		{"000","EHF030102M0","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		{"000","EHF030106M0","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"},
									   		{"000","EHF030108M0","1","1","1","1","1","","SPONADMIN","SPONADMIN","1"}};  
				
				String inssql = " INSERT INTO `SC0011`(`SC0011_01`,`SC0011_02`,`SC0011_03`,`SC0011_04`,`SC0011_05`, " +
				" `SC0011_06`,`SC0011_07`,`SC0011_08`,`DATE_CREATE`,`DATE_UPDATE`,`USER_CREATE`, " +
				" `USER_UPDATE`,`VERSION`,`SC0011_09`) " +
				" values (?,?,?,?,?,?,?,?, NOW(), NOW(),?,?,?,?) ";
				PreparedStatement pstmt = conn.prepareStatement(inssql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,
						pstmt, null, inssql);
				int indexid = 1 ;
				
				for(int x = 0; x < dataListSC0011.length; x++){					
					
					indexid = 1;
					p6stmt.setString(indexid, dataListSC0011[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListSC0011[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListSC0011[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListSC0011[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListSC0011[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListSC0011[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListSC0011[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListSC0011[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListSC0011[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListSC0011[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListSC0011[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, COMID);  //公司代碼
					indexid++;
					
					p6stmt.executeUpdate();
					
				}
				
				
				//新增子系統代碼  SC0040
				
				String dataListSC0040[][] = {{"SC","系統管制","系統管制","0","SPONADMIN","SPONADMIN","1"},
											 {"COM","公司基本資料","","10","SPONADMIN","SPONADMIN","1"},
											 {"HR","人事基本資料","","20","SPONADMIN","SPONADMIN","1"},
											 {"SALARY","薪資基本資料","","30","SPONADMIN","SPONADMIN","1"}};
				
				inssql = " INSERT INTO `SC0040`(`SC0040_01`,`SC0040_02`,`SC0040_03`,`SC0040_04`,`DATE_CREATE`, " +
						 " `DATE_UPDATE`,`USER_CREATE`,`USER_UPDATE`,`VERSION`,`SC0040_05`) " +
						 " values (?,?,?,?, NOW(),NOW(),?,?,?,?)";
				pstmt = conn.prepareStatement(inssql);
				p6stmt = new P6PreparedStatement(null,
						pstmt, null, inssql);
				
				for(int x = 0; x < dataListSC0040.length; x++){					
					
					indexid = 1;
					p6stmt.setString(indexid, dataListSC0040[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListSC0040[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListSC0040[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListSC0040[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListSC0040[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListSC0040[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListSC0040[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, COMID);  //公司代碼
					indexid++;
					
					p6stmt.executeUpdate();
					
				}
				
				//新增序號維護  SC0050
				
//				String dataListSC0050[][] = {{"EHF000300T0","EHF000300T0_01","0","201501","職務系統代碼","SPONADMIN","SPONADMIN","1"},
//											 {"SC0070","SC0070_01","0","20110101","Ss","SPONADMIN","SPONADMIN","1"}};
//				
//				inssql = " INSERT INTO `SC0050`(`SC0050_01`,`SC0050_02`,`SC0050_03`,`SC0050_04`,`SC0050_05`, " +
//						 " `DATE_CREATE`,`DATE_UPDATE`,`USER_CREATE`,`USER_UPDATE`,`VERSION`,`SC0050_06`) " +
//						 " values (?,?,?,?,?, NOW(),NOW(),?,?,?,?) ";
//				
//				pstmt = conn.prepareStatement(inssql);
//				p6stmt = new P6PreparedStatement(null,
//						pstmt, null, inssql);
//				
//				for(int x = 0; x < dataListSC0050.length; x++){					
//					
//					indexid = 1;
//					p6stmt.setString(indexid, dataListSC0050[x][indexid-1]);
//					indexid++;
//					p6stmt.setString(indexid, dataListSC0050[x][indexid-1]);
//					indexid++;
//					p6stmt.setString(indexid, dataListSC0050[x][indexid-1]);
//					indexid++;
//					p6stmt.setString(indexid, dataListSC0050[x][indexid-1]);
//					indexid++;
//					p6stmt.setString(indexid, dataListSC0050[x][indexid-1]);
//					indexid++;
//					p6stmt.setString(indexid, dataListSC0050[x][indexid-1]);
//					indexid++;
//					p6stmt.setString(indexid, dataListSC0050[x][indexid-1]);
//					indexid++;
//					p6stmt.setString(indexid, dataListSC0050[x][indexid-1]);
//					indexid++;
//					p6stmt.setString(indexid, COMID);  //公司代碼
//					indexid++;
//					
//					p6stmt.executeUpdate();
//					
//				}
				

				
				//新增系統參數管理  SC0060
				
				/*String dataListSC0060[][] = {{"ACTUARY_C001","收支-銷貨收入會科類別","4","","SPONADMIN","SPONADMIN","1"},
						 					 {"ACTUARY_C002","收支-銷貨收入會計科目","4101","","SPONADMIN","SPONADMIN","1"},
						 					 {"ACTUARY_STOCK","應付帳款會科","5101","預留切帳使用,採購入庫","SPONADMIN","SPONADMIN","1"},
						 					 {"ACTUARY_SALES","銷貨應收帳款科目","4101","預留切帳使用","SPONADMIN","SPONADMIN","1"},
						 					 {"BROW_MAXCONDITIONS","資料瀏覽視窗內，最多的條件數","3","","SPONADMIN","SPONADMIN","1"},
						 					 {"BROW_ROWSPERPAGE","資料瀏覽視窗內，分頁顯示資料時，每頁最多幾筆","10","8888","SPONADMIN","SPONADMIN","1"},
						 					 {"REPORT_PATH_LINUX","正式報表用路徑","//REPORT","","SPONADMIN","SPONADMIN","1"},
						 					 {"REPORT_MEM_SIZE","報表產生暫用的記憶體","64","記憶體使用過大時會造成資料錯誤","SPONADMIN","SPONADMIN","1"},
											 {"REPORT_PATH_WINDOWS","報表產生的絕對路徑_WINDOWS","C:\\","","SPONADMIN","SPONADMIN","1"},
						 					 {"SALES_MEMO","銷貨單項備註","Y","銷貨單項備註 Y啟用 N不啟用","SPONADMIN","SPONADMIN","1"},
											 {"SALES_STOCK","庫存是否可為負","N","當庫存不夠出貨時,是否可銷貨","SPONADMIN","SPONADMIN","1"},
											 {"STOCK_CHECK","出貨庫存檢核","Y","Y:要檢核 N:不檢核(會影響庫存正確性)","SPONADMIN","SPONADMIN","1"},
											 {"SALE_ACCOUNT","門市結帳之沖銷科目","4201","","SPONADMIN","SPONADMIN","1"},
											 {"SYSDATE","系統使用年度","1","1.西元年 2.民國年 預設西元年","SPONADMIN","SPONADMIN","1"},
											 {"STOCK_MEMO","採購啟用單項備註","Y","Y啟用 N不啟用","SPONADMIN","SPONADMIN","1"},
											 {"SHUTDOWN","系統維護參數Y--關機 N--正常","N","系統維護中 請稍待10分鐘","SPONADMIN","SPONADMIN","1"},
											 {"KeyInType","門市條碼及產編轉換","P","P:產品編號 Z:國際條碼","SPONADMIN","SPONADMIN","1"}};
				
				inssql = " INSERT INTO `SC0060`(`SC0060_01`,`SC0060_02`,`SC0060_03`,`SC0060_04`,`DATE_CREATE`, " +
						 " `DATE_UPDATE`,`USER_CREATE`,`USER_UPDATE`,`VERSION`,`SC0060_05`) " +
						 " values (?,?,?,?, NOW(),NOW(),?,?,?,?) ";
				
				pstmt = conn.prepareStatement(inssql);
				p6stmt = new P6PreparedStatement(null,
						pstmt, null, inssql);
				
				for(int x = 0; x < dataListSC0060.length; x++){					
					
					indexid = 1;
					p6stmt.setString(indexid, dataListSC0060[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListSC0060[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListSC0060[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListSC0060[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListSC0060[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListSC0060[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListSC0060[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, COMID);  //公司代碼
					indexid++;
					
					p6stmt.executeUpdate();
					
				}*/
				
				//新增單位資料維護  SU_UMITM
				
				sql = " INSERT INTO `SC_UNITM`(`SC_UNITM_01`,`SC_UNITM_02`,`SC_UNITM_03`,`SC_UNITM_04`,`SC_UNITM_05`, " +
					  " `SC_UNITM_06`,`SC_UNITM_07`,`SC_UNITM_08`,`SC_UNITM_09`,`SC_UNITM_10`,`SC_UNITM_11`,`SC_UNITM_12`, " +
					  " `DATE_CREATE`,`DATE_UPDATE`,`USER_CREATE`,`USER_UPDATE`,`VERSION`) " +
					  " values ('00','總公司','','','','','','負責人','','','','"+COMID+"',NOW(),NOW(),'SPONADMIN', 'SPONADMIN',1) ";
				stmt.execute(sql);
				
				//新增代碼類別管理  FM010501
				
				String dataListFM010501[][] = {{"EMPTYPE","員工類別","1","正式員工","員工類別","SPONADMIN","SPONADMIN","1","1"},
						   					   {"EMPTYPE","員工類別","2","臨時員工","員工類別","SPONADMIN","SPONADMIN","1","1"},
						   					   {"EMPTYPE","員工類別","3","外籍勞工","員工類別","SPONADMIN","SPONADMIN","1","1"},
						   					   {"EMPTYPE","員工類別","4","外籍配偶","員工類別","SPONADMIN","SPONADMIN","1","1"},
						   					   {"EMPTYPE","員工類別","5","工讀生","員工類別","SPONADMIN","SPONADMIN","1","1"},
						   					   {"JOBTYPE","職務狀況","1","到職","職務狀況","SPONADMIN","SPONADMIN","1","1"},
						   					   {"JOBTYPE","職務狀況","2","離職","職務狀況","SPONADMIN","SPONADMIN","1","1"},
						   					   {"JOBTYPE","職務狀況","3","復職","職務狀況","SPONADMIN","SPONADMIN","1","1"},
						   					   {"JOBTYPE","職務狀況","4","留職停薪","職務狀況","SPONADMIN","SPONADMIN","1","1"}};
				
				inssql = " INSERT INTO `FM010501`(`FM010501_01`,`FM010501_02`,`FM010501_03`,`FM010501_04`,`FM010501_05`, " +
						 " `DATE_CREATE`,`DATE_UPDATE`,`USER_CREATE`,`USER_UPDATE`,`VERSION`,`RANK`,`FM010501_06`) " +
						 " values (?,?,?,?,?, NOW(),NOW(),?,?,?,?,?) ";
				
				pstmt = conn.prepareStatement(inssql);
				p6stmt = new P6PreparedStatement(null,
						pstmt, null, inssql);
				
				for(int x = 0; x < dataListFM010501.length; x++){					
					
					indexid = 1;
					p6stmt.setString(indexid, dataListFM010501[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListFM010501[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListFM010501[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListFM010501[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListFM010501[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListFM010501[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListFM010501[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListFM010501[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListFM010501[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, COMID);  //公司代碼
					indexid++;
					
					p6stmt.executeUpdate();
					
				}
				
				//新增 帳目類別科目管理 FM030301
				
				/*String dataListFM030301[][] ={{"4","營業收入","1","","","1","SPONADMIN","SPONADMIN","4"},
											  {"5","營業成本","1","","","2","SPONADMIN","SPONADMIN","3"},
											  {"4101","銷貨收入","2","4","[出貨管理作業]貨品銷貨收入(建議為貨品)","1","SPONADMIN","SPONADMIN","2"},
											  {"5101","進貨成本","2","5","[進貨入庫作業]貨品進貨(建議為貨品)","2","SPONADMIN","SPONADMIN","2"},
											  {"5701","業務成本","2","5","[進貨入庫作業]業務性質進貨(勞務,其他)","2","SPONADMIN","SPONADMIN","1"},
											  {"4701","業務收入","2","4","[出貨管理作業]業務性質收入(勞務,其他)","1","SPONADMIN","SPONADMIN","1"},
											  {"7","營業外收益","1","","","1","SPONADMIN","SPONADMIN","2"},
											  {"7107","盤存盈餘","2","7","[庫存調整作業]盤點庫存盈餘","1","SPONADMIN","SPONADMIN","1"},
											  {"8","營業外支出","1","","","2","SPONADMIN", "SPONADMIN","2"},
											  {"8107","盤存損失","2","8","[庫存調整作業]盤點庫存損失","2","SPONADMIN","SPONADMIN","1"},
											  {"4201","銷貨收入","2","4","[門市銷貨作業]貨品銷貨收入(建議為貨品)","1","SPONADMIN","SPONADMIN","1"},
											  {"4801","業務收入","2","4","[門市銷貨作業]貨品銷貨收入(建議為貨品)","1","SPONADMIN","SPONADMIN","1"},
											  {"4102","銷貨退回","2","4","[銷貨退回管理作業]銷貨收入減少,視為收入的『減項』","1","SPONADMIN","SPONADMIN","2"},
											  {"4202","銷貨退回","2","4","[門市銷貨退回管理作業]","1","SPONADMIN","SPONADMIN","1"},
											  {"4104","銷貨折讓","2","4","[其他]銷貨收入減少,視為收入的『減項』","1","SPONADMIN","SPONADMIN","1"},
											  {"5203","進貨退出","2","5","[進貨退回管理作業]進貨成本減少,為支出的『減項』","2","SPONADMIN","SPONADMIN","1"},
											  {"5204","進貨折讓","2","5","[其他]進貨成本減少,視為的『減項』","2","SPONADMIN","SPONADMIN","1"},
											  {"6","營業費用","1","","","2","SPONADMIN","SPONADMIN","2"},
											  {"6106","郵電費","2","6","[其他]收到貨款時,於貨款內所扣除之費用,例:銀行匯費.郵票","2","SPONADMIN","SPONADMIN","1"},
											  {"6108","手續費","2","6","[其他]收到貨款時,於貨款內所扣除之費用,例:刷卡手續費","2","SPONADMIN","SPONADMIN","1"}};
				
				inssql = " INSERT INTO `FM030301`(`FM030301_01`,`FM030301_02`,`FM030301_03`,`FM030301_04`,`FM030301_05`, " +
						 " `FM030301_06`,`DATE_CREATE`,`DATE_UPDATE`,`USER_CREATE`,`USER_UPDATE`,`VERSION`,`FM030301_07`) " +
						 " VALUES (?,?,?,?,?,?, NOW(), NOW(),?,?,?,?) ";
				
				pstmt = conn.prepareStatement(inssql);
				p6stmt = new P6PreparedStatement(null,
						pstmt, null, inssql);
				
				for(int x = 0; x < dataListFM030301.length; x++){					
					
					indexid = 1;
					p6stmt.setString(indexid, dataListFM030301[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListFM030301[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListFM030301[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListFM030301[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListFM030301[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListFM030301[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListFM030301[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListFM030301[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, dataListFM030301[x][indexid-1]);
					indexid++;
					p6stmt.setString(indexid, COMID);  //公司代碼
					indexid++;
					
					p6stmt.executeUpdate();
					
				}*/
				
//				stmt = conn.prepareStatement(sql);
				
				Form.setDATA01(COMID);
				
				pstmt.close();
				p6stmt.close();
//				stmt.execute();
				stmt.close();
				pstmt = null;
				p6stmt = null;
				stmt = null;
						
				
				
				PreparedStatement stmt01=null;
				
				inssql="update sc0050 set " +
						"SC0050_06=?," +
						"DATE_UPDATE=NOW()," +
						"VERSION = 1" +
						" where SC0050_06 NOT IN ('A99999')"  ;
					
				stmt01 = conn.prepareStatement(inssql);
				stmt01.setString(1, COMID);
				stmt01.executeUpdate();
				
				
				inssql="update sc0060 set " +
				"SC0060_05=?," +
				"DATE_UPDATE=NOW()," +
				"VERSION = 1" +
				" where SC0060_05 NOT IN ('A99999')"  ;
			
				stmt01 = conn.prepareStatement(inssql);
				stmt01.setString(1, COMID);
				stmt01.executeUpdate();
				
				
				inssql="update sc0020 set " +
				"SC0020_08=?," +
				"DATE_UPDATE=NOW()," +
				"VERSION = 1" +
				" where SC0020_08 NOT IN ('A99999')"  ;
			
				stmt01 = conn.prepareStatement(inssql);
				stmt01.setString(1, COMID);
				stmt01.executeUpdate();
		
				
				

				inssql="update EHF000500T0 set " +
				"EHF000500T0_11=?," +
				"DATE_UPDATE=NOW()," +
				"VERSION = 1" +
				" where EHF000500T0_11 NOT IN ('A99999')"  ;
			
				stmt01 = conn.prepareStatement(inssql);
				stmt01.setString(1, COMID);
				stmt01.executeUpdate();
		
				
				
				inssql="update EHF000500T1 set " +
				"EHF000500T1_08=?," +
				"DATE_UPDATE=NOW()," +
				"VERSION = 1" +
				" where EHF000500T1_08 NOT IN ('A99999')"  ;
			
				stmt01 = conn.prepareStatement(inssql);
				stmt01.setString(1, COMID);
				stmt01.executeUpdate();
				
				
				
				
				stmt01.close();
				
				
				
				
				
				
				conn.commit();
				request.setAttribute("Form1Datas",Form);
				
				request.setAttribute("MSG", "新增公司成功!");
				
				} else {
					saveErrors(request, lc_errors);
					request.setAttribute("save_status", "error");
					request.setAttribute("Form1Datas",Form);
					return mapping.findForward("success");
				}
			} catch (Exception e) {
				request.setAttribute("Form1Datas",Form);
				request.setAttribute("MSG", "新增公司失敗!");
				System.out.println("SC011A.addCompany() "+e);
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} finally {
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (Exception e) {
				}
			}
		
		//新增資料後，回到顯示查詢結果的頁面
		//return showAllDatas(in_mapping, in_form, in_request, in_response);
	//	return queryForm(mapping, form, request, response);
		return mapping.findForward("success");
	}

	/**
	 * 新增資料表單
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward addCompanyForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		return addCompany(mapping, form, request, response);
	}
	/**
	 * 刪除資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward delData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("action","delData");
		SC010F Form = (SC010F) form;

		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		try {
			conn = tools.getConnection();
        } catch (Exception e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
		ActionErrors lc_errors = Form.validate(mapping, request, conn);
		try{
			if (lc_errors.isEmpty()) {	
				Statement stmt = conn.createStatement();
				String Sql;
				Sql = "delete from SC_UNITM where " +
				  " SC_UNITM_01='" + Form.getSC_UNITM_01()+ "' " +
				  " and SC_UNITM_02='" + Form.getSC_UNITM_02()+ "' and SC_UNITM_12='"+userform(request).getSC0030_14()+"'";
				stmt.execute(Sql);
				request.setAttribute("MSG", "刪除成功!");
				stmt.close();
				
				conn.commit();
			}
			else {
					//saveErrors(request, lc_errors);
					request.setAttribute("save_status", "error");
					request.setAttribute("Form1Datas",Form);
					request.setAttribute("MSG",request.getAttribute("ErrMSG") );
					return mapping.findForward("success");
			}
				
		}catch (Exception e){
			e.printStackTrace();
			request.setAttribute("MSG", "刪除失敗!");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}finally{
			try {
				if (conn != null && !conn.isClosed()){
					conn.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
        return init(mapping,form,request,response);
	}

	/**
	 * 編輯資料表單
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward editDataForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		if(!request.getParameter("LastKey1").equals(((SC010F)form).getSC_UNITM_01()))
		{
			request.setAttribute("MSG", "修改失敗!條件值已改變");
			return init(mapping, form, request, response);
		}
		return saveData(mapping, form, request, response);
	}
	/**
	 * 取消作業，判斷查詢條件還在不在，如果在的話，就呼叫queryDatas
	 * 如果不在就呼叫init()
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		return init(mapping, form, request, response);
//		DataSource ds;
//		Connection conn = null;
//		request.setAttribute("action","delData");
//		String temp="";
//		SC005F Form = (SC005F) form;
//		ds = getDataSource(request, "SPOS");
//		try {
//            conn = ds.getConnection();
//        } catch (SQLException e2) {
//            // TODO Auto-generated catch block
//            e2.printStackTrace();
//        }
//        temp=getSequence(conn,"SC001","SC001_01","00");
//        System.out.println(temp);
//        return init(mapping, form, request, response);
	}

	
	
	/**
	 * 一開始進來程式時顯示空的資料
	 * 在這裡要用isLogin判斷使用者是否有登入系統
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		try {
			DataSource ds;
			BA_REPORTF Form = new BA_REPORTF();
			request.setAttribute("Form1Datas", Form);
			// 將表單的顯示模式設定為create (新增模式)
			FormUtils.setFormDisplayMode(request, form, "create");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
//			System.out.println("run");
		}
		return  mapping.findForward("success");
	}

	/**
	 * 實際查詢資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward queryDatas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		SC010F Form = (SC010F) form;
		if(!Form.getSC_UNITM_01().equals(""))
		{
		String sql = "";
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		FormUtils.setFormDisplayMode(request, form, "create");
		try{
			conn = tools.getConnection();
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			sql = "select * from  SC_UNITM where SC_UNITM_01='" + Form.getSC_UNITM_01() + "' and SC_UNITM_02='" + Form.getSC_UNITM_02() + 
			      "' and SC_UNITM_12='"+userform(request).getSC0030_14()+"' order by SC_UNITM_01" ;
			ResultSet rs = stmt.executeQuery(sql);
			SC010F Form1 = new SC010F();
			
			if (rs.next()){
				Form1.setSC_UNITM_01(rs.getString("SC_UNITM_01"));
				Form1.setSC_UNITM_02(rs.getString("SC_UNITM_02"));
				Form1.setSC_UNITM_08(rs.getString("SC_UNITM_08"));
				BA_TOOLS ba=BA_TOOLS.getInstance();
				Form1.setDATE_CREATE(ba.DateToChineseDate(rs.getTimestamp("DATE_CREATE")));
				Form1.setDATE_UPDATE(ba.DateToChineseDate(rs.getTimestamp("DATE_UPDATE")));
				Form1.setUSER_CREATE(ba.GetUserName(conn,rs.getString("USER_CREATE")));
				Form1.setUSER_UPDATE(ba.GetUserName(conn,rs.getString("USER_UPDATE")));
				Form1.setVERSION(rs.getInt("VERSION"));
				request.setAttribute("Form1Datas", Form1);
				FormUtils.setFormDisplayMode(request, form, "edit");
			}
			else
			{	
				request.setAttribute("MSG", "查無資料");
				request.setAttribute("Form1Datas", Form);
				return mapping.findForward("success");
			}
			
			
			//產生下拉選單
		
			//將表單的顯示模式設定為edit (編輯模式)
			
			//指定執行鈕，應該要呼叫的method
			request.setAttribute("BUTTON_TYPE", "saveData");
			rs.close();
			stmt.close();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try {
				if (conn != null && !conn.isClosed()){
					conn.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		}
		else
		{	
			request.setAttribute("Required","true");
			request.setAttribute("MSG", "請輸入條件值");
			request.setAttribute("Form1Datas", Form);
			return mapping.findForward("success");
		}
		return mapping.findForward("success");
	}

	/**
	 * 查詢資料表單
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward queryForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		return queryDatas(mapping, form, request, response);
	}

	/**
	 * 儲存編修資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward saveData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		SC010F Form = (SC010F) form;
		
		try {
			conn = tools.getConnection();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        Form.setSC_UNITM_12(userform(request).getSC0030_14());
        request.setAttribute("action","saveData");
		ActionErrors lc_errors = Form.validate(mapping, request,conn);
		
			
		try {
			if (lc_errors.isEmpty()) {
			//Statement stmt = conn.createStatement();
			String sql="";
			PreparedStatement stmt=null;
			if(!Form.getSC_UNITM_01().equals("") || !Form.getSC_UNITM_02().equals(""))
			{
				sql="update SC_UNITM set " +
					"SC_UNITM_08=?," +
					"DATE_UPDATE=NOW()," +
					"USER_UPDATE=?," +
					"VERSION=?" +
					" where SC_UNITM_01='" + Form.getSC_UNITM_01()+ "' " +
					" and SC_UNITM_02='" + Form.getSC_UNITM_02() + "'" +
					" and SC_UNITM_12='"+userform(request).getSC0030_14()+"'";
				
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, Form.getSC_UNITM_08());
				stmt.setString(2, userform(request).getSC0030_01());
				stmt.setInt(3, Form.getVERSION()+1);
			}
			stmt.executeUpdate();
			stmt.close();
			conn.commit();
			
			request.setAttribute("MSG", "修改成功!");
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status","error");
				request.setAttribute("Form1Datas",Form);
				request.setAttribute("MSG",request.getAttribute("ErrMSG") );
				return mapping.findForward("success");
				
			}
		} catch (Exception e) {
			request.setAttribute("MSG", "修改失敗!");
			System.out.println("SC010A.saveData() "+e );
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}finally{
			try {
				if (conn != null && !conn.isClosed()){
					conn.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		//儲存後，回到顯示查詢結果的頁面
		return queryDatas(mapping, form, request, response);
//		form=(FM0102Form)request.getSession().getAttribute("FM0102Query");
	}
	public void main(String[] arg) {
	}

 


}