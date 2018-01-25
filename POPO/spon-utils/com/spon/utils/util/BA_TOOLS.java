package com.spon.utils.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
 
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

//ian
import javax.naming.*;
import javax.sql.DataSource;
 
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.Vector;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import com.spon.ems.vacation.tools.EMS_AttendanceAction;
import com.spon.utils.sc.actions.SC005A;
import com.spon.utils.sc.actions.SC006A;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.struts.form.BA_FILECONTENTF;
import com.spon.utils.work_schedule.EMS_WorkScheduleControl;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;

import vtgroup.ems.common.vo.AuthorizedBean;

/**
 * 公用元件
 * 
 * @version 1.0
 * @created 10-四月-2006 下午 09:16:47
 */
public class BA_TOOLS {

	private volatile static BA_TOOLS ba_tools; 
	
	
	public static BA_TOOLS getInstance() {
        /*if (ba_tools == null)
        	ba_tools = new BA_TOOLS();*/
        if(ba_tools == null) {
            synchronized(EMS_AttendanceAction.class) {
            	ba_tools = new BA_TOOLS();
            }
        }
        return ba_tools;
    }
	private String user;
	private String pwd;
	
	private BA_TOOLS(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 比對未編碼的文字plainText與以MD5編碼後的文字是否相同
	 * 
	 * @param plainText    未編碼的文字
	 * @param encodedText    編碼後的文字
	 */
	public static boolean chkTextInMD5(String plainText, String encodedText){
		if (encodeInMD5(plainText).equals(encodedText)){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 以MD5編碼字串
	 * 
	 * @param text    待編碼的字串
	 */
	public static String encodeInMD5(String text){
		final StringBuffer buffer = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
		    md.update(text.getBytes());

		    byte[] digest = md.digest();
		    for (int i = 0; i < digest.length; ++i)
		    {
		        final byte b = digest[i];
		        final int value = (b & 0x7F) + (b < 0 ? 128 : 0);
		        buffer.append(value < 16 ? "0" : "");
		        buffer.append(Integer.toHexString(value));
		    }
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("BA_TOOLS.encodeInMD5()");
		}
	    return buffer.toString();
	}
	
	/**
	 * 轉換true->"Y" false->"N"
	 * @param bln 要轉換的布林值
	 * @return "Y" or "N"
	 */
	public String booleanToYN(boolean bln){
		if (bln){
			return "Y";
		}else{
			return "N";
		}
	}
	/**
	 * 轉換1->"Y" 0->"N"
	 * @param num 要轉換的Int
	 * @return "Y" or "N"
	 */
	public String booleanToYN(int num){
		if (num==1){
			return "Y";
		}else{
			return "N";
		}
	}
	/**
	 * 轉換"1"->"Y" "0"->"N"
	 * @param num 要轉換的Int
	 * @return "Y" or "N"
	 */
	public String booleanToYN(String num){
		if (num.equals("1")){
			return "Y";
		}else{
			return "N";
		}
	}
	
	/**
	 * 轉換"Y"->true "N"->false
	 * @param YN 要轉換的字串 "Y" or "M"
	 * @return boolean true or false
	 */
	public boolean YNTOboolean(String num){
		if (num.equals("Y")){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 格式化數字，num是12，pattern是000000，就會回傳000012
	 * @param num 待轉換的數字
	 * @param pattern 格式
	 * @return 格式化後之字串
	 */
	public String strFormat(int num, String pattern){
		DecimalFormat format = new DecimalFormat(pattern);
		return format.format(num);
	}
	
	/**
	 * 傳入字串(如：094/11/10 1420)，轉換成java.util.Date
	 * @param datetime 094/11/10 1420
	 * @param format yyyy/MM/dd HHmm
	 * @return java.util.Date
	 */
	public java.util.Date stringToDate(String datetime, String format){
		java.util.Date date = null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(datetime);
			date.setYear(date.getYear() + 1911);
		}catch(Exception e){
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 傳入字串(如：094/11/10 1420)，轉換成java.util.Date
	 * @param datetime 094/11/10 1420
	 * @param format yyyy/MM/dd HHmm
	 * @return java.util.Date
	 */
	public java.sql.Timestamp stringToDatenew(String datetime, String format){
		java.util.Date date = null;
		java.sql.Timestamp datesql =null;
		long value=0;
		try{
			String[] times = datetime.split(" ");
			int yyyMMdd = Integer.parseInt(times[0]);
			int yyyyMMdd = yyyMMdd + 19110000;
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(yyyyMMdd+" "+times[1]);
			datesql=new java.sql.Timestamp(date.getTime());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return datesql;
	}
	
	/**
	 * 傳入年月日轉換為2010/01/01
	 * @param date
	 * @return String
	 */
	public String ymdTostring(int date)
	{
		String s=date+"";
		if(s.length()==8)
		{
			s=s.substring(0,4)+"/"+s.substring(4,6)+"/"+s.substring(6,8);
		}else if(s.length()==7)
		{
			s=s.substring(0,3)+"/"+s.substring(3,5)+"/"+s.substring(5,7);
		}
		else if(s.length()==6)
		{
			s=s.substring(0,2)+"/"+s.substring(2,4)+"/"+s.substring(4,6);
		}else
		{
			s="";
		}
		return s;
	}
	/**
	 * 取得兩日期的日期差距
	 * @param date1 ..如為0時，則以現在時間帶入
	 * @param date2
	 * @param kind ( YEAR or MONTH or DATE ) 
	 * @return
	 * @throws Exception
	 */
	public int yearBetweens(int date1,int date2,String kind) throws Exception{
		GregorianCalendar calendar2,calendar_tmp; 
		boolean falg=true;
		String[] ids = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000);
		SimpleTimeZone pdt = new SimpleTimeZone(-8 * 60 * 60 * 1000, ids[0]);
		pdt.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
		pdt.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
		GregorianCalendar calendar = new GregorianCalendar(pdt);
		int yyyyMMdd = date2 + 19110000;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmm");
		Date trialTime=null;
		if (date1==0)
			trialTime = new Date();
		else
		{	
			date1 += 19110000;
			trialTime = sdf.parse(date1+" "+"0101");
		}	
		calendar.setTime(trialTime);
		Date afterdate = sdf.parse(yyyyMMdd+" "+"0101");
		calendar2=new GregorianCalendar();
		calendar2.setTime(afterdate);
		if (calendar2.before(calendar))
		{
			calendar_tmp=calendar2;
			calendar2=calendar;
			calendar=calendar_tmp;
			falg=false;
		} 
		int vcnt=0;
		if (!kind.equals("") && (kind.equals("YEAR") || kind.equals("year")) )
		{ 
			while(calendar.before(calendar2))
			{
				calendar.add(Calendar.YEAR,1);
				vcnt++;
			}
		}else if (!kind.equals("") && (kind.equals("MONTH") || kind.equals("month")) )
		{	
			while(calendar.before(calendar2))
			{
				calendar.add(Calendar.MONTH,1);
				vcnt++;
			}
		}
		else if (!kind.equals("") && (kind.equals("DATE") || kind.equals("DATE")) )
		{	
			while(calendar.before(calendar2))
			{
				calendar.add(Calendar.DATE,1);
				vcnt++;
			}
		} 
		if (!falg)
			vcnt=vcnt*-1;
		return vcnt;
	}
	/**
	 * 傳入年月轉換為
	 * 201001=>2010/01
	 * 9501=>95/01
	 * @param date
	 * @return String
	 */
	public String ymTostring(int date)
	{
		String s=date+"";
		if(s.length()==6){
			s=s.substring(0,4)+"/"+s.substring(4,6);
		}else if(s.length()==5){
			s=s.substring(0,3)+"/"+s.substring(3,5);
		}else if(s.length()==4){
			s=s.substring(0,2)+"/"+s.substring(2,4);
		}else{
			s="";
		}
		return s;
	}
	
	/**
	 * 傳入月日轉換為06/01
	 * @param date
	 * @return String
	 */
	public String mdTostring(int date)
	{
		String s=date+"";
		if(s.length()==4)
		{
			s=s.substring(0,2)+"/"+s.substring(2,4);
		}
		else
		{
			s="";
		}
		return s;
	}
	/**
	 * 傳入095/06/01轉換為950601
	 * 傳入 2010/01/01 轉換為20100101
	 * @param String
	 * @return int
	 */
	public int stringToymd(String date)
	{
		int s=0;
		if (date!=null){
			String tt="";
			if (date.indexOf("/")>-1){
				 tt=date.replaceAll("/","").replaceAll(" ", "");
			}else if(date.indexOf("-")>-1){
				 tt=date.replaceAll("-","").replaceAll(" ", "");
			}
			
			try {
				s=Integer.parseInt(tt);
			} catch (Exception e) {
				
			}
		}
		return s;
	}
	
	/**
	 * 傳入095/06轉換為9506
	 * @param String
	 * @return int
	 */
	public int stringToym(String date)
	{
		int s=0;
		if (date!=null){
			String tt=date.replaceAll("/","").replaceAll(" ", "");
			try {
				s=Integer.parseInt(tt);
			} catch (Exception e) {
				
			}
		}
		return s;
	}
	/**
	 * 傳入06/06轉換為0606
	 * @param String
	 * @return int
	 */
	public int stringTomd(String date)
	{
		int s=0;
		if (date!=null){
			String tt=date.replaceAll("/","").replaceAll(" ", "");
			try {
				s=Integer.parseInt(tt);
			} catch (Exception e) {
				
			}
		}
		return s;
	}
	
	
	
	/**
	 * 西元年轉換民國年
	 * @param datetime (DATE 型態)
	 * @param format yyyy/MM/dd HHmm
	 * @return yyy/MM/dd HH:mm
	 */
	public String DateToChineseDate(Date datetime){
		
		if (datetime==null)
			return "";
		
		String ChineseDate ="";
		try{
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			ChineseDate+=Integer.parseInt(sdf.format(datetime))-1911;
			ChineseDate+="/";
			sdf = new SimpleDateFormat("MM/dd HH:mm");
			ChineseDate+=sdf.format(datetime);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return ChineseDate;
	}
	
	/**
	 * 取得使用者名稱 
	 * @param conn 
	 * @param uid 
	 * @return
	 */
	public String GetUserName(Connection conn,String uid)
	{
		String uname="";
		
		try {
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select SC0030_04 from SC0030 where SC0030_01='"+uid+"' ");
			if(rs.next())
			{
				uname=rs.getString(1);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();	
			
		}
		
		return uname;
	}
	
	/**
	 * 取得使用者名稱 -- 公司別
	 * @param conn 
	 * @param uid 
	 * @param SC0030_14  
	 * 				公司代碼
	 * @return
	 */
	public String GetUserName(Connection conn,String uid,String SC0030_14)
	{
		String uname="";
		
		try {
			Statement stmt=conn.createStatement();
			ResultSet rs = null;
			if(SC0030_14 != null && !SC0030_14.equals("")){
				rs=stmt.executeQuery("select SC0030_04 from SC0030 where SC0030_01='"+uid+"' " +
									 " and SC0030_14 = '"+SC0030_14+"' ");
			}else{
				rs=stmt.executeQuery("select SC0030_04 from SC0030 where SC0030_01='"+uid+"' ");
			}
			if(rs.next())
			{
				uname=rs.getString(1);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();	
			
		}
		
		return uname;
	}
	
	/**
	 * 日期天數相加，或相減 帶入95/06/06 
	 * @param date
	 * @param day		增加天數
	 * 					day = 5 增加5天, day = -3 減3天
	 * @return date 
	 */
	public String addday(String date,int day) {
		BA_TOOLS aa=BA_TOOLS.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.setTime(aa.stringToDate(date,"yyyy/MM/dd"));
 		cal.add(Calendar.DAY_OF_MONTH,day);
 		return aa.DateToChineseDate(cal.getTime()).split(" ")[0];
	}
	
	/**
	 * 日期月數相加 帶入 9601,-1 回傳 9512, 帶入9512,1 回傳 9601
	 * @param date
	 * @param month
	 * @return date 
	 */
	public int addmonth(int yymm,int month) {
		boolean isYM=false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		int yymm2 = 0;
		try {
			String yyyymmdd = (yymm + 191100) + "01";
			BA_TOOLS aa=BA_TOOLS.getInstance();
			cal.setTime(sdf.parse(yyyymmdd));
	 		cal.add(Calendar.MONTH,month);		
		} catch (Exception e) {
			// TODO: handle exception
		}
		yymm2 = Integer.parseInt(sdf.format(cal.getTime()));
		yymm2 = (int)Math.floor(yymm2 / 100) - 191100;
 		return yymm2;
	}	
	/**
	 * 日期月數相加 帶入95/06/06 
	 * @param date
	 * @param month
	 * @return date 
	 */
	public String addmonth(String date,int month) {
		boolean isYM=false;
		
		if(date.replaceAll("/","").length()<6)
		{
			date=date+"/01";
			isYM=true;
		}
		
		
		BA_TOOLS aa=BA_TOOLS.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.setTime(aa.stringToDate(date,"yyyy/MM/dd"));
 		cal.add(Calendar.MONTH,month); 
 		if(isYM)
 		{
 			String tt=aa.DateToChineseDate(cal.getTime()).split(" ")[0];
 			tt=tt.split("/")[0]+"/"+tt.split("/")[1];
 			return tt;
 		}
 		else
 		return aa.DateToChineseDate(cal.getTime()).split(" ")[0];	
	}
	/**
	 * 日期年數相加 帶入95/06/06 
	 * @param date
	 * @param day
	 * @return date 
	 */
	public String addyear(String date,int year) {
		boolean isYM=false;
		if(date.replaceAll("/","").length()<6)
		{
			date=date+"/01";
			isYM=true;
		}
		BA_TOOLS aa=BA_TOOLS.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.setTime(aa.stringToDate(date,"yyyy/MM/dd"));
 		cal.add(Calendar.YEAR,year); 
 		if(isYM)
 		{
 			String tt=aa.DateToChineseDate(cal.getTime()).split(" ")[0];
 			tt=tt.split("/")[0]+"/"+tt.split("/")[1];
 			return tt;
 		}
 		else
 		return aa.DateToChineseDate(cal.getTime()).split(" ")[0];	
	}
	
	
	/**
	 * 判斷是否為數值型態
	 * @param s
	 * @return
	 */
	public boolean isInteger( String s ){
	    try{
	          Integer d = new Integer( s );
	          return true;
	     }catch(NumberFormatException e){
	          return false; 
	     }//end try/catch
	}

	/**
	 * 讀取檔案內容，並將每一筆資料儲存於個別的com.spon.struts.form.
	 * BA_FILECONTENTF後，再將所有的BA_FILECONTENTF儲存成ArrayList後回傳
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 * @param filePath    檔案絕對路徑，須包含檔案名稱
	 */
	public ArrayList readFileContent(Connection conn, ActionForm form, HttpServletRequest request, String filePath){
		BA_FILECONTENTF Form = (BA_FILECONTENTF) form;
		ArrayList collection=new ArrayList();
		java.io.File targetFolder= new java.io.File(filePath);
		//if (!targetFolder.exists())
		//{	
		//	targetFolder.mkdir();
		//}
		if (targetFolder.isFile())
		{	
	   		try
	   		{	
	   			//RandomAccessFile randomAccessFile =                     
	   			//	new RandomAccessFile(targetFolder, "r");
	   			int fileline =0;
	   			String[] temp=null;
	   			
//   				FileReader fr = new FileReader(new File(filePath));
   				BufferedReader br = new BufferedReader( new InputStreamReader(new FileInputStream(filePath), "BIG5"));
   				BA_FILECONTENTF dataform=null;
   				String aaa = br.readLine();
   				int kkkkk=0;
   				if (request.getRequestURI().indexOf("SM031105M1")>0)
   				{
   					kkkkk=4;
   				}
   				String sql="";
	   			while(aaa != null)
	   			{
	   				fileline ++;
	   				dataform= new BA_FILECONTENTF();
	   				dataform.setCol01(fileline+"");   //行數
	   				//randomAccessFile.seek(0);
	   				//String aaa = new String(br.readLine().getBytes("iso-8859-1"), "UTF-8");
	   				temp = aaa.split("\\^");
	   				if (temp!=null) {
		   				if (temp.length>0) { 
		   					dataform.setCol_I(""+temp[0]);
		   					try {
		   						Statement stmt=conn.createStatement();
		   						switch (kkkkk){
		   						case 4:
		   							sql="select SA_SALAM_N from SA_SALAM where  SA_SALAM_I='"+temp[0].trim()+"'";
		   							break;
		   						default:
		   							sql="select SA_SALAM_N from SA_SALAM where  SA_SALAM_I='"+temp[1].trim()+"'";
		   							break;
		   						}
			   					ResultSet rs=stmt.executeQuery(sql);
			   					if(rs.next())
			   						dataform.setCol_N(rs.getString(1));
			   					rs.close();
			   					stmt.close();
							} catch (Exception e) {
								// TODO: handle exception
							}
		   					
		   				}
		   				if (temp.length>1) {
		   					dataform.setCol02(""+temp[1]);
		   				}
		   				if (temp.length>2) {
		   					dataform.setCol03(""+temp[2]);
		   				}
		   				if (temp.length>3) {
		   					dataform.setCol04(""+temp[3]);
		   				}
		   				if (temp.length>4) {
		   					dataform.setCol05(""+temp[4]);
		   				}
		   				if (temp.length>5) {
		   					dataform.setCol06(""+temp[5]);
		   				}
		   				if (temp.length>6) {
		   					dataform.setCol07(""+temp[6]);
		   				}
		   				if (temp.length>7) {
		   					dataform.setCol08(""+temp[7]);
		   				}
		   				if (temp.length>8) {
		   					dataform.setCol09(""+temp[8]);
		   				}
		   				if (temp.length>9) {
		   					dataform.setCol10(""+temp[9]);
		   				}
		   				if (temp.length>10) {
		   					dataform.setCol11(""+temp[10]);
		   				}
		   				if (temp.length>11) {
		   					dataform.setCol12(""+temp[11]);
		   				}
		   				if (temp.length>12) {
		   					dataform.setCol13(""+temp[12]);
		   				}
		   				if (temp.length>13) {
		   					dataform.setCol14(""+temp[13]);
		   				}
		   				if (temp.length>14) {
		   					dataform.setCol15(""+temp[14]);
		   				}
		   				if (temp.length>15) {
		   					dataform.setCol16(""+temp[15]);
		   				}
		   				if (temp.length>16) {
		   					dataform.setCol17(""+temp[16]);
		   				}
		   				if (temp.length>17) {
		   					dataform.setCol18(""+temp[17]);
		   				}
		   				if (temp.length>18) {
		   					dataform.setCol19(""+temp[18]);
		   				}
		   				if (temp.length>19) {
		   					dataform.setCol20(""+temp[19]);
		   				}
	   				}
	   				dataform.setFilePath(""+filePath);
	   				collection.add(dataform);
	   				aaa = br.readLine();
	   			}
			   	br.close();
//			   	fr.close();
	   		} 
            catch (Exception e)
			{
            	request.setAttribute("MSG", "匯入檔案格式錯誤!");
            	e.printStackTrace();
			}
   		}
		
		return collection;
	}//end isInteger() 
	
	/**
	 * 將數字轉為大寫國字 { "零", "壹", "貳", "參", "肆", "伍", "陸", "柒", "捌", "玖" }
	 * @param str
	 * @return
	 */
	public String NumberToBChinese(String str){
		
		return ConvertCHNum(str,"B");
		/*
		String addString = "";
		String[] str1 = { "零", "壹", "貳", "參", "肆", "伍", "陸", "柒", "捌", "玖" };
		String[] str2 = { "", "拾", "佰", "千" };
		String[] str3 = { "", "萬", "億", "兆" }; // 這裡再加即可
		// k為str3的index;
		// j為第幾位數
		int changeS_I = 0, k = 0, j = 0, L = 0;
		// flag是否為零
		boolean flag = false;
		
		for (int i = str.length() - 1; i >= 0; i--) {
			changeS_I = Integer.parseInt(str.toCharArray()[i] + "");// 這樣子是轉成str的ASII
			if (j % 4 == 0)// 每當萬,億, 兆時
			{
				if (changeS_I == 0)// 數字是零
				{
					L = 0;
					if (flag == true)// 零後面接 萬,億, 兆時的處理
						for (L = j; L < j + 4; L++)// 若千萬百萬拾萬都是零,則"萬"字不加
						{
							changeS_I = Integer.parseInt(str.toCharArray()[L] + "");
							if (changeS_I == 0)
								L = 100;
						}
					if (L == 100)
						addString = str3[k] + addString;
				} else
					addString = str1[changeS_I ] + str3[k] + addString;
				k++;
			} else if (changeS_I == 0) {
				if (flag == false)
					addString = str1[changeS_I] + addString;
				flag = true;
			} else {
				flag = false;
				addString = str1[changeS_I] + str2[(j) % 4] + addString;
			}
			j++;
		}

		return addString;
		*/
	}
	
	/**
	 * 將數字轉為小寫國字 { "○", "ㄧ", "二", "三", "四", "五", "六", "七", "八", "九" }
	 * @param str
	 * @return
	 */
	public String NumberToSChinese(String str){
		return ConvertCHNum(str,"S");
		
		/*
		String addString = "";
		String[] str1 = { "○", "ㄧ", "二", "三", "四", "五", "六", "七", "八", "九" };
		String[] str2 = { "", "十", "百", "千" };
		String[] str3 = { "○", "○", "○", "○" }; // 這裡再加即可
		// k為str3的index;
		// j為第幾位數
		int changeS_I = 0, k = 0, j = 0, L = 0;
		// flag是否為零
		boolean flag = false;
		
		for (int i = str.length() - 1; i >= 0; i--) {
			changeS_I = Integer.parseInt(str.toCharArray()[i] + "");// 這樣子是轉成str的ASII
			if (j % 4 == 0)// 每當萬,億, 兆時
			{
				if (changeS_I == 0)// 數字是零
				{
					L = 0;
					if (flag == true)// 零後面接 萬,億, 兆時的處理
						for (L = j; L < j + 4; L++)// 若千萬百萬拾萬都是零,則"萬"字不加
						{
							changeS_I = Integer.parseInt(str.toCharArray()[L] + "");
							if (changeS_I == 0)
								L = 100;
						}
					if (L == 100)
						addString = str3[k] + addString;
				} else
					addString = str1[changeS_I ] + str3[k] + addString;
				k++;
			} else if (changeS_I == 0) {
				if (flag == false)
					addString = str1[changeS_I] + addString;
				flag = true;
			} else {
				flag = false;
				addString = str1[changeS_I] + str2[(j) % 4] + addString;
			}
			j++;
		}

		return addString;
		*/
	}
	public String ConvertCHNum(String str,String kind){
		int Num = Integer.parseInt(str);
		String sZero="";
		if (kind.equals("B")){
			sZero= "零";
		}else{
			sZero= "○";
		}
		if (Num == 0){
			return sZero;
		} else{
			String[] cName;
			String[] ar;
			if (kind.equals("B")){
				 cName =new String[] {"", "", "拾", "佰", "仟", "萬", "拾", "佰", "仟", "億", "拾", "佰", "仟"};
				 ar = new String[] {"零", "壹", "貳", "參", "肆", "伍", "陸", "柒", "捌", "玖"};
			}else{
				 cName = new String[] {"", "", "十", "百", "千", "萬", "十", "百", "千", "億", "十", "百", "千"};
				 ar = new String[]{"○", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
			}
		int cNum ,i,j,cZero;
		String cunit="";
		String conver="";
		String cLast = "";
		String Text1  = Num+"";
	 	cZero = 0;
	 	i = 0;
	 	for (j = Text1.length() ; j > 0 ; j--) {
	 		cNum = Integer.parseInt(Text1.substring(i,i+1));
	 		cunit = cName[j]; //'取出位數 '
	 		if (cNum==0){ //判斷取出的數字是否為0,如果是0,則記錄共有幾0 
	 			cZero = cZero +1;
	 			if ((cunit.equals("萬") || cunit.equals("億")) && cLast==""){ //如果取出的是萬,億,則位數以萬億來補
	 				cLast = cunit;
	 			}
	 		}else{
	 			if (cZero>0){ //如果取出的數字0有n個,則以零代替所有的0 
	 				if (conver.toString().substring(conver.length()-1, conver.length()).equals("億") ||
	 						conver.toString().substring(conver.length()-1, conver.length()).equals("萬")){
	 					conver = conver + cLast;//如果最後一位不是億,萬,則最後一位補上"億萬" 
	 				}
	 				conver = conver + sZero;
	 				cZero=0;
	 				cLast="";
	 			}
	 			conver = conver + ar[cNum]+cunit;  //如果取出的數字沒有0,則是中文數字+單位 
	 		}
	 		i=i+1;
	 	}
	//判斷數字的最後一位是否為0,如果最後一位為0,則把萬億補上
	 	if (conver.toString().substring(conver.length()-1, conver.length()).equals("萬") || 
	 			conver.toString().substring(conver.length()-1, conver.length()).equals("億")) {
	 		conver = conver + cLast ;
	 	}
		return conver.toString();
		}
		
	}

	/**
	 * 阿拉伯年月日轉國字民國年月日  eq (960501 to 96年05月01日)
	 * @param date
	 * @return
	 */
	
	public String DATE_TO_TWDATE(String strdate){
		String TWDATE="";
		String date = strdate.replaceAll("/","");
		if (date.trim().equals("")) return "";
		date=Integer.parseInt(date)+"";
		
		if(date.length()<=5)
		{
			if(date.length()==4)
			{
				TWDATE=date.substring(0,2)+"年";
				TWDATE+=date.substring(2,4)+"月";
			}else
			{
				TWDATE=date.substring(0,3)+"年";
				TWDATE+=date.substring(3,5)+"月";
			}
		}else
		{
			if(date.length()==6)
			{
				TWDATE=date.substring(0,2)+"年";
				TWDATE+=date.substring(2,4)+"月";
				TWDATE+=date.substring(4,6)+"日";
			}else
			{
				TWDATE=date.substring(0,3)+"年";
				TWDATE+=date.substring(3,5)+"月";
				TWDATE+=date.substring(5,7)+"日";
			}
		}
		return TWDATE;
	}
	/**
	 * 數字四捨五入
	 * @param value 數字
	 * @param scale 到小數第幾位
	 * @return 字串
	 */
	public String ROUND_HALF_UP(String value,int scale)
	{
		
		String return_value="0";
		try {
			Double dSource=new Double(value);
			BigDecimal deSource = new BigDecimal(dSource.doubleValue());
			return_value=deSource.setScale(scale,BigDecimal.ROUND_HALF_UP).toString();	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return return_value;
	}
	/**
	 * 數字四捨五入
	 * @param value 數字
	 * @param scale 到小數第幾位
	 * @return 字串
	 */
	public int ROUND_HALF_UP(float value,int scale)
	{
		return Integer.parseInt(ROUND_HALF_UP((value+""),scale));
	}

	/**
	 * 數字無條件捨去
	 * @param value 數字
	 * @param scale 到小數第幾位
	 * @return 字串
	 */
	public String ROUND_DOWN(String value,int scale)
	{
		String return_value="0";
		try {
			Double dSource=new Double(value);
			BigDecimal deSource = new BigDecimal(dSource.doubleValue());
			return_value=deSource.setScale(scale,BigDecimal.ROUND_DOWN).toString();
		} catch (Exception e) {

		}
		return return_value;
	}

	
	/**
	 * 取得中國年系統日期 exp 0950608
	 */
	public int getChiSysDate(){
//		Calendar calendar =Calendar.getInstance();
//		calendar.setTime(new Date());
//		Date date=calendar.getTime();
		int chiY = 0;
		int chiM = 0;
		int chiD = 0;
		Calendar calendar =Calendar.getInstance();
		Date date = new Date();
		chiY = calendar.get(calendar.YEAR) - 1911;
		chiM = calendar.get(calendar.MONTH) + 1;
		chiD = calendar.get(calendar.DATE);
		
		return chiY * 10000 + chiM * 100 + chiD;
	}
	/**
	 * 取得系統日期 exp 0950608
	 */
	public int getSysDate(){
		int chiY = 0;
		int chiM = 0;
		int chiD = 0;
		Calendar calendar =Calendar.getInstance();
		Date date = new Date();
		chiY = calendar.get(calendar.YEAR) ;
		chiM = calendar.get(calendar.MONTH) + 1;
		chiD = calendar.get(calendar.DATE);
		
		return chiY * 10000 + chiM * 100 + chiD;
	}
	/**
	 * 取得西元年系統日期 exp 950607
	 */
	public int getSysDateYYMMDD(){
		int chiY = 0;
		int chiM = 0;
		int chiD = 0;
		Calendar calendar =Calendar.getInstance();
		Date date = new Date();
		chiY = calendar.get(calendar.YEAR);
		chiM = calendar.get(calendar.MONTH) + 1;
		chiD = calendar.get(calendar.DATE);
		return chiY * 10000 + chiM * 100 +chiD ;
	}
	/**
	 * 取得西元年系統日期 exp 9506
	 */
	public int getSysDateYYMM(){
		int chiY = 0;
		int chiM = 0;
		Calendar calendar =Calendar.getInstance();
		Date date = new Date();
		chiY = calendar.get(calendar.YEAR);
		chiM = calendar.get(calendar.MONTH) + 1;
		return chiY * 100 + chiM ;
	}
	/**
	 * 取得中國年系統日期 exp 9506
	 */
	public int getSysYYMM(){
//		Calendar calendar =Calendar.getInstance();
//		calendar.setTime(new Date());
//		Date date=calendar.getTime();
		int chiY = 0;
		int chiM = 0;
		int chiD = 0;
		Calendar calendar =Calendar.getInstance();
		Date date = new Date();
		chiY = calendar.get(calendar.YEAR) - 1911;
		chiM = calendar.get(calendar.MONTH) + 1;
		//chiD = calendar.get(calendar.DATE);
		return chiY * 100 + chiM ;
	}
	/**
	 * 取得傳入日期的年月日
	 * @param String date //中國日期格式 ex.095/01/05
	 * @param String part // YYY 年 MM 月 DD 日
	 * @param boolean Zero // 是否補零
	 * return YYY:95 MM:1 DD:5
	 */
	public String getDatePart(String date,String part,boolean Zero){
		String tmp = "";
		tmp = date.replaceAll("/","");
		switch (tmp.length()) {
			case 6: //傳入 950105
				tmp = "0" + tmp; 	
				break;
			case 7: //傳入 0950105
				break;
			default:
				tmp = "0000000";
				break;
		}
		//看使用者要哪一部份的資料
		if (Zero==false){
			if (part.equals("YYY")){
				return Integer.parseInt(tmp.substring(0,3))+"" ;
			}else if (part.equals("MM")){
				return Integer.parseInt(tmp.substring(3,5))+"";
			}else if (part.equals("DD")){
				return Integer.parseInt(tmp.substring(5,7))+"";
			}else{
				return "";
			}
		}else{
			if (part.equals("YYY")){
				return tmp.substring(0,3);
			}else if (part.equals("MM")){
				return tmp.substring(3,5);
			}else if (part.equals("DD")){
				return tmp.substring(5,7);
			}else{
				return "";
			}
		}
	}
	
	/**
	 * 產生下拉選單的選項(代碼資料表 SC_CODEM 專用)
	 * 以SC_CODEM_01語法查詢選項,程式帶入
	 * select SC_CODEM_02 as item_id,SC_CODEM_03 as item_value from SC_CODEM where SC_CODEM_01='SC_CODEM_01'
	 * @param conn
	 *            資料庫連結物件
	 * @param emptyOption
	 *            是否要有一個空白的選項 true: 要 false: 不要	 *            
	 * @param SC_CODEM.SC_CODEM_01
            
	 */
	public String getCodeName(Connection conn,String FM010501_01,String FM010501_02) {
		String reValue="";
		try {
			if (conn != null){
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select FM010501_04 as item_value from FM010501 where FM010501_01='" + FM010501_01 + "' and FM010501_03='" + FM010501_02 + "'");
				if (rs.next()) {
					reValue = rs.getString("item_value");
				}
				rs.close();
				stmt.close();
			}else{
				reValue="";
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("BA_TOOLS.getCodeName()" + e);
			e.printStackTrace();
			reValue="";
		}
		return reValue;
	}
	
	/**
	 * 產生下拉選單的選項(代碼資料表 SC_CODEM 專用)
	 * 以SC_CODEM_01語法查詢選項,程式帶入
	 * select SC_CODEM_02 as item_id,SC_CODEM_03 as item_value from SC_CODEM where SC_CODEM_01='SC_CODEM_01'
	 * @param conn
	 *            資料庫連結物件
	 * @param emptyOption
	 *            是否要有一個空白的選項 true: 要 false: 不要	 *            
	 * @param SC_CODEM.SC_CODEM_01
	 * @param SC_CODEM.SC_CODEM_06
	 * 		
            
	 */
	public String getCodeName(Connection conn,String FM010501_01,String FM010501_02,String FM010501_06) {
		String reValue="";
		try {
			if (conn != null){
				Statement stmt = conn.createStatement();
				ResultSet rs = null;
				if(FM010501_06 != null && !FM010501_06.equals("")){
					rs = stmt.executeQuery("select FM010501_04 as item_value from FM010501 where FM010501_01='" + FM010501_01 + "' " +
							" and FM010501_03='" + FM010501_02 + "' and FM010501_06='"+FM010501_06+"' ");
				}else{
					rs = stmt.executeQuery("select FM010501_04 as item_value from FM010501 where FM010501_01='" + FM010501_01 + "' and FM010501_03='" + FM010501_02 + "'");
				}			
				if (rs.next()) {
					reValue = rs.getString("item_value");
				}
				rs.close();
				stmt.close();
			}else{
				reValue="";
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("BA_TOOLS.getCodeName()" + e);
			e.printStackTrace();
			reValue="";
		}
		return reValue;
	}
	
	/**
	 * 傳入單位代碼回傳單位名稱
	 * 以SC_CODEM_01語法查詢選項,程式帶入
	 * select SC_CODEM_02 as item_id,SC_CODEM_03 as item_value from SC_CODEM where SC_CODEM_01='SC_CODEM_01'
	 * @param conn
	 *            資料庫連結物件
	 * @param SC_UNITM_01
	 *            單位代碼            
	 */
	public String getUnitName(Connection conn,String SC_UNITM_01) {
		String reValue="";
		try {
			if (conn != null){
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select SC_UNITM_02 from SC_UNITM where SC_UNITM_01='" + SC_UNITM_01 + "' ");
				if (rs.next()) {
					reValue = rs.getString("SC_UNITM_02");
				}
				rs.close();
				stmt.close();
			}else{
				reValue="";
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("BA_TOOLS.getCodeName()" + e);
			e.printStackTrace();
			reValue="";
		}
		return reValue;
	}
	
	/**
	 * 產生各種費用別UID按年月更新，重新計算
	 * @param conn
	 * @param type 費用別
	 * @param table 更新序號使用，資料庫table名稱
	 * @param col 更新序號使用，資料庫欄位
	 * @throws Exception 
	 */
	public String createUID(Connection conn,String type,String table,String col,HttpServletRequest request) throws Exception {
		String reValue="";
		if (conn!=null) {
			try {
				//2010.10.18 單號命名規則順序變更為:table key、年、月、日、流水號 edit by John
				SC005A form = new SC005A();
				reValue=(type.length()<2?"0" +type:type);	//table key
				reValue+=((getSysDateYYMMDD()+"").length()==8?""+getSysDateYYMMDD():"0"+getSysDateYYMMDD());	//年、月、日
				reValue+=form.getSequence(conn, table, col, "0000", request);	//流水號
			} catch (Exception e) {
				throw new Exception(e.toString()+"BA_TOOLS.createUID()應該是沒有設定自動更新每日歸零序號表!!");
			}
		}else{
			reValue="conn不見了!!請傳入connection";
		}
		return reValue;
	}
	
	/**
	 * 專門給宏家專用的UID產生元件
	 * 產生各種費用別UID按年月更新，重新計算
	 * @param conn
	 * @param type 費用別
	 * @param table 更新序號使用，資料庫table名稱
	 * @param col 更新序號使用，資料庫欄位
	 * @throws Exception 
	 */
	public int createUID_HC(Connection conn,String type,String table,String col,HttpServletRequest request) throws Exception {
		String reValue="";
		if (conn!=null) {
			try {
				//2010.10.18 單號命名規則順序變更為:table key、年、月、日、流水號 edit by John
				SC005A form = new SC005A();
				//reValue=(type.length()<2?"0" +type:type);	//table key
				reValue+=((getSysDateYYMMDD_HC()+"").length()==6?""+getSysDateYYMMDD_HC():"0"+getSysDateYYMMDD_HC());	//年、月、日
				reValue+=form.getSequence(conn, table, col, "0000", request);	//流水號
			} catch (Exception e) {
				throw new Exception(e.toString()+"BA_TOOLS.createUID()應該是沒有設定自動更新每日歸零序號表!!");
			}
		}else{
			reValue="conn不見了!!請傳入connection";
		}
		return Integer.valueOf(reValue);
	}
	/**
	 * 取得西元年系統日期 exp 950607
	 */
	public int getSysDateYYMMDD_HC(){
		int chiY = 0;
		int chiM = 0;
		int chiD = 0;
		Calendar calendar =Calendar.getInstance();
		Date date = new Date();
		chiY = calendar.get(calendar.YEAR);
		chiM = calendar.get(calendar.MONTH) + 1;
		chiD = calendar.get(calendar.DATE);
		return chiY * 100 + chiM * 1  ;
	}
	
	/**
	 * 產生客制化單號UID_C按帶入的年月日計算
	 * @param conn
	 * @param field 日期的資料庫欄位名稱
	 * @param date 日期
	 * @param table 資料庫table名稱
	 * @param field1 客制化單號的資料庫欄位名稱
	 * @param field2 公司別的資料庫欄位名稱
	 * @param comNum 公司別
	 * @return 日期+4碼流水號
	 */
	public String createUID_C(Connection conn,String field,String date,String table,String field1,String field2,String comNum) throws Exception {
		String reValue="";
		if (conn!=null) {
			try {
				String Num="";
				date = date.replace("/", "");
				date = date.replace("-", "");
				String sql = "SELECT max(substr("+field1+",-4)) AS NUM FROM "+table+" WHERE "+field+"="+date+" AND "+field2+"='"+comNum+"'";
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				rs.next();
				if(rs.getString("NUM")==null) Num = "0001";
				else Num = ((10001 + rs.getInt("NUM"))+"").substring(1);
				reValue = date + Num;
				stmt.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			reValue="conn不見了!!請傳入connection";
		}
		return reValue;
	}
	
	/**
	 * 產生公司別UID按年月更新，重新計算
	 * @param conn
	 * @param type 費用別
	 * @param table 更新序號使用，資料庫table名稱
	 * @param col 更新序號使用，資料庫欄位
	 * @throws Exception 
	 */
	public String createCompanyUID(Connection conn,String type,String table,String col,HttpServletRequest request) throws Exception {
		String reValue="";
		if (conn!=null) {
			try {
				SC005A form = new SC005A();
				reValue=((getSysDateYYMM()+"").length()==6?""+getSysDateYYMM():"0"+getSysDateYYMM());
				reValue+=(type.length()<2?"0" +type:type);
				reValue+=form.getSequence(conn, table, col, "000", request);
			} catch (Exception e) {
				throw new Exception(e.toString()+"BA_TOOLS.createUID()應該是沒有設定自動更新每月歸零序號表!!");
			}
		}else{
			reValue="conn不見了!!請傳入connection";
		}
		return reValue;
	}
	
	/**
	 * url傳進來時，只會把變數傳遞的變數值轉換，"=" "?" "&" 都不變
	 * foo.jsp?text=新增&type=t 轉換成 test.jsp?text=%E6%96%B0%E5%A2%9E&type=t
	 * 
	 * @param src 要轉換的字串
	 * @param charset 編碼方式 utf-8, big5, ...
	 * @return 轉換後的字串
	 */
	public static String encodeURL(String src, String charset){
		String name = "";
		try{
			String[] names = src.split("\\?");
			if (names != null && names.length == 2){
				String[] name1 = names[1].split("\\&");
				String aa = "";
				if (name1 != null && name1.length != 0){
					for (int i = 0; i < name1.length; i ++){
						String[] name2 = name1[i].split("=");
						if (name2 != null && name2.length == 2){
							aa += name2[0] + "=" + URLEncoder.encode(name2[1], charset);
						}
						aa += "&";
					}
				}
				name = names[0] + "?" + aa;
			}else{
				name=src;
			}
		}catch(Exception e){
		}
		return name;
	}
	/**
	 * 補滿文字格式
	 * 
	 * @param val 要轉換的字串
	 * @param 編排後的總長度含要轉換字元		
	 * @return 要排在前面的字元或字串 exp: "0" 則回傳 0000A1
	 */
	public static String formatstr(String val,int len, String toput){
		String tmp="";
		String retval="";
		if (toput.equals(""))
		{	
			tmp=" ";
		}else
		{
			tmp=toput;
		}	
		if (len>=val.length()) 
		{
			for (int i=0;i<len-val.length();i++) {
				retval+=tmp;
			}
			retval+=val;
		}else 
		{
			for (int i=0;i<len;i++) {
				retval+=tmp;
			}
		}
		return retval;
	}
	
	
	
		/**
		 * 取得訊息代碼的內容
		 * @param conn 資料庫連結物件
		 * @param code 訊息代碼
		 * @return 訊息內容
		 */
		public String errText(Connection conn, String code){
			String cod = "";
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select MSG_CODE, MSG_TEXT from MSGID" +
						" where MSG_CODE='" + code + "'");
				if (rs.next()){
					cod = rs.getString("MSG_TEXT");
				}
				rs.close();
				stmt.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
			return cod;
		}
		/**
		 * 實際將(錯誤)訊息寫入資料庫的MSGLOG中，如果寫入過程有錯，就會以File IO寫入
		 * System.getProperty("user.dir") + "/SPOSMSG.log"
		 * @param conn
		 * @param msg    訊息陣列 {訊息型態(ERR/MSG), 訊息編號(例如：0001), Exception原始的訊息e.getMessage}
		 * @param userid 使用者登入帳號
		 * @return 訊息字串，用於顯示在畫面上
		 */
		private String handleMsg(Connection conn, String[] msg, String userid){
			String errSql = "";
			String err = "";
			String msgReturn = "";
			try {
				if (msg != null && msg.length > 0){
					if (msg.length >= 3){
						String text = errText(conn, msg[1]);
						errSql = "insert into MSGLOG values('" + msg[0] + "', '" + msg[1] + "', '" +
							msg[2] + "', NOW(), '" + userid + "')";
						err = new Date().toString() + " ## " + msg[0] + " ## " + msg[1] + " ## " +
							msg[2] + " ## " + text + " ## " + userid;
						msgReturn = msg[1] + " - " + text;
					}else{
						errSql = "insert into MSGLOG values('ERR', '', '" +
							msg[0] + "', NOW(), '" + userid + "')";
						err = new Date().toString() + " ## " + "ERR" + " ## " + msg[0] + " ## " + userid;
						msgReturn = "ERR - " + msg[0];
					}
					Statement stmt = conn.createStatement();
					stmt.execute(errSql);
					stmt.close();
				}
			} catch (Exception ee) {
				try {
					File file = new File(System.getProperty("user.dir") + "/SPOSMSG.log");
					FileWriter fw = new FileWriter(file, true);
					fw.write(err);
					fw.flush();
					fw.close();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			return msgReturn;
		}
		
		/**
		 * 將訊息，寫入LOG，處理一般訊息
		 * 
		 * @param conn		資料庫聯結物件
		 * @param msg		要記錄的訊息
		 * @param others	紀錄額外訊息
		 * @param userid	使用者登入帳號
		 * @return  訊息字串，用於顯示在畫面上
		 */	
		public String writeMsg(Connection conn, String msg, String others, String userid){
			return handleMsg(conn, new String[]{"MSG", msg, others}, userid);
		}
		
		/**
		 * 將錯誤訊息，寫入LOG，僅處理Exception
		 * 以下是使用範例
		 * 	public void testMaster(){
		 *	
		 *		try {
		 *			....
		 *			testMsg(conn);
		 *		} catch (Exception e) {
		 *			String aaa = new BA_TOOLS().writeErr(conn, e, "999999");
		 *		}finally {
		 *			try {
		 *				if (conn != null && !conn.isClosed()){
		 *					conn.close();
		 *				}				
		 *			} catch (Exception e) {
		 *		}
		 *	}
		 *
		 *	public void testMsg(Connection conn) throws Exception{
		 *		try{
		 *			...
		 *		}catch (Exception e){
		 *			throw new Exception("ERR" + "§§" + "001" + "§§" + e.getMessage());
		 *		}
		 *	}
		 * @param conn		資料庫聯結物件
		 * @param smse		Exception 例外物件
		 * @param userid	使用者登入帳號
		 * @return  錯誤訊息字串，用於顯示在畫面上
		 */
		public String writeErr(Connection conn, Exception smse, String userid){
			try {
				//先將之前的異動取消
				String str = "";
				conn.rollback();
				String[] msg = smse.getMessage().split("§§");
				str =  handleMsg(conn, msg, userid);
				conn.commit();
				return str;
			} catch (Exception e ) {
				// TODO: handle exception
				return "";
			}
			
		}
		
		
		
		
		/**
		 * 帶入SQL語法 取得FormBean 
		 * @param conn
		 * @param sql
		 * @param bean
		 * @return
		 */
		
		public Object getFormBean(Connection conn,String sql,Object bean)
		{
			try {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				ResultSetMetaData rsm=rs.getMetaData();
				HashMap map=new HashMap();
				if(rs.next())
					
				for (int i = 1; i <=rsm.getColumnCount(); i++) {
					if(rsm.getColumnType(i)==java.sql.Types.DATE){
						map.put(rsm.getColumnName(i), (rs.getDate(i).toString()));//this.DateToChineseDate(rs.getTimestamp(i)));
					}else if(rsm.getColumnType(i)==java.sql.Types.VARCHAR | rsm.getColumnType(i)==java.sql.Types.CHAR )	{
						String ss="";
						if (rsm.getColumnName(i).equals("USER_CREATE") || rsm.getColumnName(i).equals("USER_UPDATE") ){
							ss= rs.getString(i)==null?"":this.GetUserName(conn,rs.getString(i));
						}else{
							ss= rs.getString(i)==null?"":rs.getString(i);
						}
						map.put(rsm.getColumnName(i), ss.equals("null")?"":ss);
						
					}
					else 
						map.put(rsm.getColumnName(i), rs.getString(i));
				}
				BeanUtils.populate(bean, map);
				rs.close();
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return bean;
		}
		
		/**
		 * 帶入民國年月 或 年月日  取得 當月最後一天的 年月日 
		 * @param date
		 * @return date 
		 */
		public String getLastDay(String date) {
			date=date.replaceAll("/", "");
			if(date.length()<6)
			{
				if(date.length()==5)
				{
					date=date.substring(0,3)+"/"+date.substring(3,5)+"/01";
				}
				else
				{
					date=date.substring(0,2)+"/"+date.substring(2,4)+"/01";
				}
			}else
			{
				if(date.length()==7)
				{
					date=date.substring(0,3)+"/"+date.substring(3,5)+"/01";
				}
				else
				{
					date=date.substring(0,2)+"/"+date.substring(2,4)+"/01";
				}
			}
			
			date=this.addmonth(date, 1);
			date=this.addday(date,-1);
			return date;
		}
		/**
		 * 取得年資
		 * @parma big_date 較大日期
		 * @parma small_date 較小日期
		 * 
		 */
		public int getYearCt(String big_date,String small_date) {
			boolean tmp=false;
			int ret_val=0;
			for (int i=0;i<50;i++) {  //以50年做為區間
				if (stringToymd(addyear(addday(small_date,-1),50-i))<=stringToymd(big_date) && tmp==false){
					tmp=true;
					ret_val=50-i;
				}
			}
			return ret_val;
		}
		
		/**
		 * 帶入民國年月   取得 當月天數 
		 * @param date
		 * @return days 
		 */
		public String getMonthDays(String date) {
			String days=this.getLastDay(date);
			days=days.substring(days.length()-2);
			return days;
		}
		
		/**
		 * 帶入民國年月   取得 兩個日期相減的天數 
		 * @param date
		 * @return days 
		 */
		public String getDateDiffDays(String date1,String date2) {
			String days="0";
			date1=date1.replaceAll("/", "");
			date2=date2.replaceAll("/", "");
			if(date1.length()==7)
			{
				date1=date1.substring(0,3)+"/"+date1.substring(3,5)+"/"+date1.substring(5,7);
			}
			else
			{
				date1=date1.substring(0,2)+"/"+date1.substring(2,4)+"/"+date1.substring(4,6);
			}
			if(date2.length()==7)
			{
				date2=date2.substring(0,3)+"/"+date2.substring(3,5)+"/"+date2.substring(5,7);
			}
			else
			{
				date2=date2.substring(0,2)+"/"+date2.substring(2,4)+"/"+date2.substring(4,6);
			}
			
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(this.stringToDate(date1,"yyyy/MM/dd"));
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(this.stringToDate(date2,"yyyy/MM/dd"));
			
			long tday=(cal2.getTimeInMillis()-cal1.getTimeInMillis())/(1000*60*60*24);
			days=tday+"";
			return days;
		}
		
		/**
		 * 帶入民國年月   取得 兩個日期相差的年 
		 * @param date
		 * @return days 
		 */
		public String getDateDiffYears(String date1,String date2) {
			int years=0;
			date1=date1.replaceAll("/", "");
			date2=date2.replaceAll("/", "");
			int yy1=0;
			int yy2=0;
			int mmdd1=0;
			int mmdd2=0;
			int mmdd3=0;
			
			if(date1.length()==7)
			{
				yy1=Integer.parseInt(date1.substring(0,3));
				mmdd1=Integer.parseInt(date1.substring(3,5)+date1.substring(5,7));
				date1=date1.substring(0,3)+"/"+date1.substring(3,5)+"/"+date1.substring(5,7);
			}
			else
			{
				yy1=Integer.parseInt(date1.substring(0,2));
				mmdd1=Integer.parseInt(date1.substring(2,4)+date1.substring(4,6));
				date1=date1.substring(0,2)+"/"+date1.substring(2,4)+"/"+date1.substring(4,6);
			}
			if(date2.length()==7)
			{
				yy2=Integer.parseInt(date2.substring(0,3));
				mmdd2=Integer.parseInt(date2.substring(3,5)+date2.substring(5,7));
				date2=date2.substring(0,3)+"/"+date2.substring(3,5)+"/"+date2.substring(5,7);
			}
			else
			{
				yy2=Integer.parseInt(date2.substring(0,2));
				mmdd2=Integer.parseInt(date2.substring(2,4)+date2.substring(4,6));
				date2=date2.substring(0,2)+"/"+date2.substring(2,4)+"/"+date2.substring(4,6);
			}
			
			years=(yy2-yy1);
			
			String tempyymm=this.addday(date1, -1).replaceAll("/", "");
			if(tempyymm.length()==7)
			{
				mmdd3=Integer.parseInt(tempyymm.substring(3,5)+tempyymm.substring(5,7));
				tempyymm=tempyymm.substring(0,3)+"/"+tempyymm.substring(3,5)+"/"+tempyymm.substring(5,7);
			}
			else
			{
				mmdd3=Integer.parseInt(tempyymm.substring(2,4)+tempyymm.substring(4,6));
				tempyymm=tempyymm.substring(0,2)+"/"+tempyymm.substring(2,4)+"/"+tempyymm.substring(4,6);
			}
			if (mmdd2<mmdd3)
					years--;
			
			if(mmdd1==101)
					years++;
			
			
			return years+"";
		}
		
		public static void main(String[] arg0) {
			
			BA_TOOLS aa=BA_TOOLS.getInstance();
			//System.out.println(aa.ROUND_DOWN("14.82999999999",3)); 
//			System.out.println(aa.getDateDiffYears("95/01/01", "97/01/01"));
			try{
			System.out.println(aa.yearBetweens(970330,960401,"month"));
			}catch(Exception E){
				E.printStackTrace();
			}
		}
		
		public String getTime() {
			DecimalFormat df = new DecimalFormat("00");
			Calendar c = Calendar.getInstance();
			int nHour = c.get(Calendar.HOUR_OF_DAY);
			int nMin = c.get(Calendar.MINUTE);
			int nSec = c.get(Calendar.SECOND);
			
			return df.format(nHour) + ":" + df.format(nMin) + ":" + df.format(nSec);
		}
		public String getlongTime() {
			DecimalFormat df = new DecimalFormat("00");
			Calendar c = Calendar.getInstance();
			int nHour = c.get(Calendar.HOUR_OF_DAY);
			int nMin = c.get(Calendar.MINUTE);
			int nSec = c.get(Calendar.SECOND);
			int ss = c.get(Calendar.MILLISECOND);
			return df.format(nHour) + ":" + df.format(nMin) + ":" + df.format(nSec) + "-" + ss;
		}
		
		/**
		 * 送出Email的Method，經由SMTP驗證
		 * @param smtpServer 經由郵件伺服器
		 * @param to 收件人 多人用Vactor 裝起來
		 * @param from 寄件人
		 * @param subject 主題
		 * @param body 內容
		 * @param user 帳號
		 * @throws Exception 
		 * @Param pwd 密碼
		 */
		public void sendmail(String smtpServer, Vector tos, String from,
				String subject, String body, String user, String pwd) throws Exception {
			this.user = user;
			this.pwd = pwd;
			
			StringBuffer sb=new StringBuffer();
			for (int i = 0; i < tos.size(); i++) {
				HashMap hm=(HashMap)tos.get(i);
				if(!((String)hm.get("users_email")).trim().equals(""))
				sb.append(hm.get("users_email")+",");
			}
			sendmail(smtpServer, sb.toString(), from, subject, body);
		}
		
		/**
		 * 送出Email的Method，經由SMTP驗證
		 * @param smtpServer 經由郵件伺服器
		 * @param to 收件人
		 * @param from 寄件人
		 * @param subject 主題
		 * @param body 內容
		 * @param user 帳號
		 * @throws Exception 
		 * @Param pwd 密碼
		 */
		public void sendmail(String smtpServer, String to, String from,
				String subject, String body, String user, String pwd) throws Exception {
			this.user = user;
			this.pwd = pwd;
			sendmail(smtpServer, to, from, subject, body);
		}
		/**
		 * 送出Email的Method
		 * @param smtpServer 經由郵件伺服器
		 * @param to 收件人
		 * @param from 寄件人
		 * @param subject 主題
		 * @param body 內容
		 */
		public void sendmail(String smtpServer, String to, String from,
				String subject, String body) throws Exception {
			try {
				Properties props = System.getProperties();
//				body=body.replaceAll("\r","<br>");
//				body=body.replaceAll("\r\n","<br>");
//				body=body.replaceAll(" ","&nbsp;");
				// -- 可以使用預設的smtp server 或是指定 --
				props.put("mail.smtp.host", smtpServer);
				// -- 指定是否需要SMTP驗證 --
				if (!user.equals("")){
					props.put("mail.smtp.auth", "true");
				}
				
				Session session = Session.getDefaultInstance(props, null);

				// -- 新建一個新的郵件  --
				Message msg = new MimeMessage(session);

				// -- 設定收件人與寄件人欄位 --
				msg.setFrom(new InternetAddress(from));
				msg.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(
						to, false));

				// -- 也可以加入CC的功能  --
				// if (cc != null)
				// msg.setRecipients(Message.RecipientType.CC
				// ,InternetAddress.parse(cc, false));

				// -- 設定郵件主題與內容 --
				msg.setSubject(subject);
				msg.setText(body);
				// -- 設定其他Header的訊息 --
				msg.setHeader("X-Mailer", "SPOSEmail");
				msg.setSentDate(new Date());
			    msg.setContent(body,"text/html;charset=big5");
				
				// -- 送出郵件 --
				if (!user.equals("")){
					Transport transport = session.getTransport("smtp");
					transport.connect(smtpServer, user, pwd);
					transport.sendMessage(msg, msg.getAllRecipients());
				}else{
					Transport.send(msg);
				}
				
				System.out.println("郵件已送出.");
			} catch (Exception ex) {
				ex.printStackTrace();
				throw ex;
			}
		}
		/**
		 * 擷取定長字串(包含中文時一個中文字兩個位子)
		 * @param inputStr
		 * @param startpos
		 * @param endpos
		 * @return
		 */
		public  String SubString(String inputStr,int startpos,int endpos){ 
			
			int ts=startpos;
			
			for (int i = 0; i < startpos; i++) {
			if (Integer.parseInt(Integer.toHexString(inputStr.charAt(i)),16) >= 128)
			{
				startpos--;
				endpos--;
			}
		}
			
			
			if(ts>0)
			{
				if(Integer.parseInt(Integer.toHexString(inputStr.charAt(startpos)),16) >= 128)
				{
					startpos++;
					endpos++;
				}
			}
			
		for (int i = startpos; i < endpos; i++) {
			if (Integer.parseInt(Integer.toHexString(inputStr.charAt(i)),16) >= 128)
			{
				endpos--;
			}
			
		}
		
		return inputStr.substring(startpos,endpos);
		}
		
		//ian=======================================
		/**
		 * 取得Connection
		 * 
		 * @param JNDIName
		 * @return Connection
		 * @throws Exception
		 */
		public Connection getConnection(String JNDIName) throws Exception {
			
			Connection conn = null;
			Context initCtx;

			initCtx = new InitialContext();
			DataSource ds = null;

			try {
				ds = (DataSource) initCtx.lookup("java:/" + JNDIName);
			} catch (Exception e) {
				try {
					ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/" + JNDIName);
				} catch (Exception e1) {
					//loger.error(e1);
					e1.printStackTrace();
				}
			}

			conn = ds.getConnection();

			conn.setAutoCommit(false);

			return conn;
		}
		
		/**
		 * 取得 POS 資料庫連線
		 * @return
		 * @throws Exception
		 */
		public Connection getConnection() throws Exception {
			
			Connection conn = null;

			try {
				conn = this.getConnection("SPOS");
			} catch (Exception e) {
				e.printStackTrace();
			}

			return conn;
		}
		
		/**
		 * 取得 DataSource
		 * @param JNDIName
		 * @return
		 * @throws Exception
		 */
		public DataSource getDataSource(String JNDIName) throws Exception{
			
			Context initCtx;

			initCtx = new InitialContext();
			DataSource ds = null;

			try {
				ds = (DataSource) initCtx.lookup("java:/" + JNDIName);
			} catch (Exception e) {
				try {
					ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/" + JNDIName);
				} catch (Exception e1) {
					//loger.error(e1);
					e1.printStackTrace();
				}
			}
			
			return ds;
		}
		
		/**
		 * 取得 SPOS DataSource
		 * @return
		 * @throws Exception
		 */
		public DataSource getDataSource() throws Exception{
			
			DataSource ds = null;

			try {
				ds = this.getDataSource("SPOS");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return ds;
		}
		//ian===================================================
		
		/**
		 * 將字串轉換為布林值 input string "true" return boolean true, input string "false" return boolean false.
		 * @param Boolean
		 * @return
		 */
		public boolean StringToBoolean(String Boolean){
			
			boolean value = false;
			
			try{
				
				if("true".equals(Boolean) || "on".equals(Boolean) || "1".equals(Boolean) ){
					value = true;
				}else{
					value = false;
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return value;
		}
		
		/**
		 * 將布林值轉換為字串
		 * @param Boolean
		 * @param type
		 * @return
		 */
		public String BooleanToString(boolean Boolean, String type){
			
			String value = "";
			
			try{
				
				if(Boolean){
					if("1".equals(type)){
						value = "true";
					}else if("2".equals(type)){
						value = "on";
					}
					
				}else{
					
					if("1".equals(type)){
						value = "false";
					}else if("2".equals(type)){
						value = "off";
					}
					
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			return value;	
		}
		
	/**
	* 產生下拉選單
	* @param num
	* @param pattern
	* @return
	*/
	public List getOptions(Connection conn, boolean emptyOption, String classKey, String comp_id ){
			
		List optionlist = new ArrayList();
			
		try{
				
			String sql = "" +
			" SELECT EMS_CategoryT1_04 AS ITEM_ID, EMS_CategoryT1_05 AS ITEM_VALUE " +
			" FROM EMS_CategoryT0 " +
			" LEFT JOIN EMS_CategoryT1 ON EMS_CategoryT1_01 = EMS_CategoryT0_01 AND EMS_CategoryT1_09 = EMS_CategoryT0_06 " +
			" WHERE 1=1 " +
			" AND EMS_CategoryT0_01 = '"+classKey+"' " +
			" AND EMS_CategoryT0_06 = '"+comp_id+"' " +
			" ORDER BY EMS_CategoryT1_07 ";		

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
				
			if(emptyOption){
					
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id("");
				bform.setItem_value("一請選擇一");
				optionlist.add(bform);
					
			}
				
			while(rs.next()){
					
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id(rs.getString("ITEM_ID"));
				bform.setItem_value(rs.getString("ITEM_VALUE"));
				optionlist.add(bform);
					
			}
				
			rs.close();
			stmt.close();
				
		}catch(Exception e){
			e.printStackTrace();
		}
			
		return optionlist;
	}
	
	/**
	 * 依據傳入的日期與日期格式產生 String
	 * @param date
	 * @param date_format
	 * @return
	 */
	public String covertDateToString(Date date, String date_format ){
		
		String return_date = "";
		SimpleDateFormat sdf = new SimpleDateFormat(date_format);
		try {
			
			if( date != null){
				return_date = sdf.format(date);
			}else{
				throw new Exception("傳入的日期格式:"+date_format+" 有問題!!");
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		 
		return return_date;
	}
	
	/**
	 * 依據Table資料產生下拉選單項目
	 * @param conn
	 * @param col1
	 * @param col2
	 * @param table
	 * @param comp_col
	 * @param comp_id
	 * @return
	 */
	public List getSelectOptionByTable( Connection conn, String col1, String col2, String table, String comp_col, String comp_id, String others,
									    String empty_type){
		
		List datas = new ArrayList();

		//依據Table資料產生下拉選單項目
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = " SELECT "+col1+" as COL1, "+col2+" as COL2 " +
						 " FROM "+table+" " +
					     " WHERE 1=1 " +
					     " AND "+comp_col+" = '"+comp_id+"' " +
					     " "+others+" ";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			
			if("01".equals(empty_type)){
				bform.setItem_id("");
				bform.setItem_value("一請選擇一");
				datas.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("-1");
				bform.setItem_value("假日");
				datas.add(bform);	
			}else if("02".equals(empty_type)){
				bform.setItem_id("");
				bform.setItem_value("");
				datas.add(bform);
			}
			
			while(rs.next()){
				bform = new BA_ALLKINDForm();
				bform.setItem_id(rs.getString("COL1"));
				bform.setItem_value(rs.getString("COL2"));
				datas.add(bform);	
			}
			
			rs.close();
			stmt.close();
							
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return datas;
	}
	
	/**
	 * 取得系統參數(EMS) --> Table SC0060
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 * @param param
	 * @throws SQLException 
	 */
	public String getSysParam(Connection conn, String comp_id, String param){
		
		String value="";
		
		try{
			String sql = " SELECT SC0060_03 FROM SC0060  " +
						 " WHERE 1=1 " +
						 " AND SC0060_01 = '"+param+"' " +
						 " AND SC0060_05 = '"+comp_id+"' ";
		
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(	rs.next())
			{
				value=rs.getString(1);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return value; 
	}
	
	/**
	 * 依據MODE修正數字, mode:HALF_UP(四捨五入), FLOOR(無條件捨去)...
	 * @param value
	 * @param mode
	 * @return
	 */
	public int fixNumByMode( float value, String mode ){
		
		int return_value = 0;
		
		try{
			if("HALF_UP".equals(mode)){
				//四捨五入
				return_value = Math.round(value);
			}else if("FLOOR".equals(mode)){
				//無條件捨去
				return_value = ((Double)(Math.floor(value))).intValue();
			}else if("CEIL".equals(mode)){
				//無條件進位
				return_value = ((Double)(Math.ceil(value))).intValue();
			}else{
				//若無設定MODE, 預設使用四捨五入的方式
				return_value = Math.round(value);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_value;
	}
	
	/**
	 * 產生EMS各種單號UID按年月更新，重新計算
	 * @param conn
	 * @param type 單號別
	 * @param table 更新序號使用，資料庫table名稱
	 * @param col 更新序號使用，資料庫欄位
	 * @param comp_id 公司代碼
	 * @throws Exception 
	 */
	public String createEMSUID(Connection conn,String type,String table,String col,String comp_id) throws Exception {
		String reValue="";
		if (conn!=null) {
			try {
				SC005A form = new SC005A();
				reValue+=type;
				reValue+=getSysDateYYMMDD();//西元年月日
				reValue+=form.getSequence(conn, table, col, "0000", comp_id);//流水號
			} catch (Exception e) {
				throw new Exception(e.toString()+"BA_TOOLS.createUID()應該是沒有設定自動更新每月歸零序號表!!");
			}
		}else{
			reValue="conn不見了!!請傳入connection";
		}
		return reValue;
	}
	
	/**
	 * 產生EMS各種單號UID按年月更新，重新計算
	 * @param conn
	 * @param type 單號別
	 * @param table 更新序號使用，資料庫table名稱
	 * @param col 更新序號使用，資料庫欄位
	 * @param comp_id 公司代碼
	 * @throws Exception 
	 */
	public String createJOBUID(Connection conn,String type,String table,String col,String comp_id) throws Exception {
		String reValue="";
		if (conn!=null) {
			try {
				SC005A form = new SC005A();
				reValue+=type;
				reValue+=form.getSequence(conn, table, col, "000", comp_id);//流水號
			} catch (Exception e) {
				throw new Exception(e.toString()+"BA_TOOLS.createUID()應該是沒有設定自動更新每月歸零序號表!!");
			}
		}else{
			reValue="conn不見了!!請傳入connection";
		}
		return reValue;
	}
	/**
	 * 依據傳入的日期與日期格式產生 Calendar
	 * @param date
	 * @param date_format
	 * @return
	 */
	public Calendar covertStringToCalendar(String date, String date_format ){
		
		Calendar cal = null;
		SimpleDateFormat sdf = new SimpleDateFormat(date_format);
		try {
			
			if(!"".equals(date) && date != null){
				Date return_date = sdf.parse(date);
				cal = Calendar.getInstance();
				cal.setTime(return_date);
			}else if("".equals(date)){
				cal = null;
				return cal;
			}else{
				throw new Exception("傳入的日期格式:"+date_format+" 有問題!!");
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return cal;
	}
	
	
	/**
	 * String to Calendar Convert 傳入參數 "yyyy/MM/dd" (西元年)
	 * @param date
	 * @return
	 */
	public Calendar covertStringToCalendar(String date){
		
		Calendar cal = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			if(!"".equals(date) && date != null){
				Date return_date = sdf.parse(date);
				cal = Calendar.getInstance();
				cal.setTime(return_date);
			}else if("".equals(date)){
				cal = null;
				return cal;
			}else{
				throw new Exception("傳入的西元年格式有問題!!");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return cal;
	}
	/**
	 * 取得中國年系統日期 (年) exp 95
	 */
	public int getSysYY(){
//		Calendar calendar =Calendar.getInstance();
//		calendar.setTime(new Date());
//		Date date=calendar.getTime();
		int chiY = 0;
		Calendar calendar = Calendar.getInstance();
		//Date date = new Date();
		chiY = calendar.get(calendar.YEAR) - 1911;
		//chiD = calendar.get(calendar.DATE);
		return chiY;
	}
	
	
	/**
	 * 依據傳入的西元年日期(yyyy/MM/dd) 產生 String
	 * @param date
	 * @return
	 */
	public String convertADDateToStrng(Date date){
		
		String return_date = "";
		
		try{
			
			return_date = this.covertDateToString(date, "yyyy/MM/dd");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_date;
	}
	/**
	 * 每個月第一天的日期
	 * @param calendar
	 * @return
	 */
	 public static Date getFirstMonthDay(Calendar calendar) {
	        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
	        return calendar.getTime();
	 }


	 /**
	  * 每個月最後一天日期
	  * @param calendar
	  * @return
	  */
	 public static Date getLastMonthDay(Calendar calendar) {
	        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
	        return calendar.getTime();
	 }
	 
	/**
	* Time convert to Sec 時間轉換為秒數  return int
	* @param time
	* @return
	*/
	public int TimeToSecs(String time){
			
		int sec = 0;
			
		try{
			if( time != null ){
				int hour = Integer.parseInt(time.substring(0, 2));  //時
				int minute = Integer.parseInt(time.substring(2, 4));  //分
					
				sec = (hour*60*60) + (minute*60);  //秒
			}
				
		}catch(Exception e){
			e.printStackTrace();
		}
			
		return sec;
	}
	
	/**
	 * Hour convert to Sec 小時轉換秒數 return int
	 * @param shour
	 * @return
	 */
	public int HourToSecs(String shour){
		
		int sec = 0;
		try{
			float hour = Float.parseFloat(shour);  //小時
			
			sec = (int) (hour*60*60);  //秒
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return sec;
	}
	
	/**
	 * 取得傳入西元日期的年份 ex: 傳入 2010/11/09  return (String) 2010
	 * @param ad_date
	 * @param type   true ==> return AD , false ==> return Chi
	 * @return
	 */
	public String getDateADYear(String ad_date, boolean type){
		
		ad_date = ad_date.replaceAll( "/", "");
		
		if(type){
			return ad_date.substring(0, 4);
		}else{
			return String.valueOf(Integer.parseInt(ad_date.substring(0, 4)) - 1911);
		}
	}
	
	/**
	 * Minute convert to Sec 分鐘轉換為秒數  return int
	 * @param time
	 * @return
	 */
	public int MinuteToSecs(String sminute){
		
		int sec = 0;
		try{
			int minute = Integer.parseInt(sminute);  //分鐘
			
			sec = (minute*60);  //秒
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return sec;
	}
	
	/**
	 * 判斷班別是否有設定記錄中午打卡
	 * @param class_no
	 * @return
	 */
	public boolean hasNoonRecord( Map classMap ){
		
		boolean chk_falg = false;
		
		try{
			if( (Boolean) classMap.get("SIESTA_FLAG") ){
				chk_falg = true;
			}
			
		}catch(Exception e){
			chk_falg = false;
			e.printStackTrace();
		}
		
		return chk_falg;
	}
	
	/**
	 * 取得打卡異常狀態中文名稱
	 * @param status_no
	 * @return
	 */
	public String getCardErrorStatus(int error_status_no){
		
		String error_status_name = "";
		try{
			switch(error_status_no){
				
			case 1:
				error_status_name = "正常";
				break;
			case 2:
				error_status_name = "遲到";
				break;
			case 3:
				error_status_name = "早退";
				break;
			case 4:
				error_status_name = "未打卡";
				break;
			case 5:
				error_status_name = "打卡異常";
				break;
			default:
				error_status_name = "未知";
			}	
			
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return error_status_name;
	}
	
	/**
	 * 取得門禁刷卡狀態中文名稱
	 * @param status_no
	 * @return
	 */
	public String getDoorAccessStatus(int status_no){
		
		String status_name = "";
		try{
			switch(status_no){
			case 0:
				status_name = "";
				break;
			case 1:
				status_name = "上班";
				break;
			case 2:
				status_name = "下班";
				break;
			case 3:
				status_name = "加班上班";
				break;
			case 4:
				status_name = "加班下班";
				break;
			}	
			
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return status_name;
	}
	
	/**
	 * 檢核當天是否為休假日
	 * @param conn
	 * @param date 西元年月日
	 * @param comp_id
	 * @return
	 */
	public boolean isHoliday(Connection conn, String date, String comp_id){
		
		boolean chk_flag = false;
		
		try{
			
			String sql = "" +
			" SELECT EHF000500T0_01 " +
			" FROM EHF000500T0 " +
			" WHERE 1=1 " +
			" AND EHF000500T0_03 = '"+(Integer.parseInt((date.split("/"))[0])-1911)+"' " +  //年度
			" AND '"+date+"' BETWEEN EHF000500T0_05 AND EHF000500T0_06 " +  //休假日期區間
			" AND EHF000500T0_11 = '"+comp_id+"' ";  //公司代碼
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				chk_flag = true;
				
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 檢核是否為休假日
	 * @param conn
	 * @param employee_id
	 * @param start_date 輸入2016/01   或是   2016/01/01    只接受以上兩種格式，其餘皆不做動作
	 * @param end_date 2016/01/01    只接受完整年月日 (只做為兩日期中間的判斷)，其餘皆不做動作(一天或是一個月  該格為"")
	 * @param comp_id
	 * @return
	 */
	public boolean isHoliday(Connection conn, String employee_id, String start_date, String end_date, String comp_id){
		
		boolean chk_flag = false;
		//產生預排換班處理元件
		EMS_WorkScheduleControl ems_wsc_tools = EMS_WorkScheduleControl.getInstance();
		Map NotWorkDay = new HashMap();
		Map WorkDay = new HashMap();
		
		try{
			//準備emp_authbean
			AuthorizedBean emp_authbean = new AuthorizedBean();
			emp_authbean.setEmployeeID(employee_id);
			emp_authbean.setCompId(comp_id);
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			//員工Map
			Map empMap = hr_tools.getEmpNameMap(comp_id);
			//部門Map
			Map depMap = hr_tools.getDepNameMap(comp_id);
			
			hr_tools.close();
								
			//取得排班表與行事曆(職員)
			ems_wsc_tools.getVacations(conn, empMap, depMap, comp_id, start_date, end_date, NotWorkDay);			
			
			WorkDay = (Map) NotWorkDay.get(employee_id);
			
			if(WorkDay.get(start_date)==null){
				//表示要上班
				chk_flag = false;
			}else if(WorkDay.get(start_date).equals("-1")){
				//表示假日
				chk_flag = true;
			}else{
				//表示要上班
				chk_flag = false;
			}			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
//	/**
//	 * 檢核當天是否為休假日
//	 * @param conn
//	 * @param date 西元年月日
//	 * @param comp_id
//	 * @return
//	 */
//	public boolean isHoliday(Connection conn, String date, String comp_id, String EHF000400T0_17){
//		
//		boolean chk_flag = false;
//		
//		try{
//			
//			String sql = "" +
//			" SELECT EHF000500T0_01, EHF000500T0_12 " +
//			" FROM EHF000500T0 " +
//			" WHERE 1=1 " +
//			" AND EHF000500T0_03 = '"+(Integer.parseInt((date.split("/"))[0])-1911)+"' " +  //年度
//			" AND '"+date+"' BETWEEN EHF000500T0_05 AND EHF000500T0_06 " +  //休假日期區間
//			" AND EHF000500T0_11 = '"+comp_id+"' ";  //公司代碼
//			
//			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
//			ResultSet rs = stmt.executeQuery(sql);
//			
//			if(rs.next()){
//				if("2".equals(rs.getString("EHF000500T0_12")) && "2".equals(EHF000400T0_17)){
//					//EHF000500T0_12 ==1  大禮拜，EHF000500T0_12==2  小禮拜
//					//EHF000400T0_17 ==1  周休二日  EHF000400T0_17 ==2 隔周休二日
//					//不做任何動作，小禮拜+隔周休二日，表示要上班
//					
//				}else{
//					chk_flag = true;
//				}
//			}
//			
//			rs.close();
//			stmt.close();
//			
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		
//		return chk_flag;
//	}
	
	/**
	 * 轉化代碼為系統內碼，顯示代碼必須是02，系統代碼必須是01
	 * @param conn
	 * @param table
	 * @param show_id
	 * @param comp_id
	 * @return
	 */
	public String ShowIdtoRealId(Connection conn, String table,
			String show_id, String comp_id) {
		// TODO Auto-generated method stub
		
		String RID = "";
		
		try{
			String sql = "" +
			" SELECT * " +
			" FROM "+table+" " +
			" WHERE 1=1 " +
			" AND "+table+"_02 = '"+show_id+"' " +
			" AND HR_CompanySysNo = '"+comp_id+"' ";  //公司代碼
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				String temp = table+"_01";
				RID = rs.getString(temp);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return RID;
	}

	/**
	 * 取得打卡狀態中文名稱
	 * @param status_no
	 * @return
	 */
	public String getCardStatus(int status_no){
		
		String status_name = "";
		try{
			switch(status_no){
				
			case 1:
				status_name = "上午上班";
				break;
			case 2:
				status_name = "下午下班";
				break;
			case 3:
				status_name = "外出";
				break;
			case 4:
				status_name = "返回";
				break;
			case 5:
				status_name = "加班上班";
				break;
			case 6:
				status_name = "加班下班";
				break;
			case 7:
				status_name = "上午下班";
				break;
			case 8:
				status_name = "下午上班";
				break;
			default:
				status_name = "未知";
			}	
			
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return status_name;
	}
	
	/**
	 * 轉換  Datetime to Calendar
	 * @param date
	 * @param time
	 * @return
	 */
	public Calendar datetimeToCalendar( String date, String time ){
		
		Calendar cal = null; 
		
		try{
			cal = this.covertStringToCalendar(date+" "+time, "yyyy/MM/dd HH:mm:ss");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return cal;
	}
	
	/**
	 * 轉換  Datetime to Calendar
	 * @param datetime
	 * @return
	 */
	public Calendar datetimeToCalendar( String datetime ){
		
		Calendar cal = null; 
		
		try{
			cal = this.covertStringToCalendar(datetime, "yyyy/MM/dd HH:mm:ss");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return cal;
	}
	
	/**
	 * MilliSecs convert to Sec 毫秒轉換為秒數 return int
	 * @param milli_sec
	 * @return
	 */
	public int MilliSecsToSecs(long milli_sec){
		
		int sec = 0;
		
		try{
			sec = (int) milli_sec / (1000);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return sec;
	}
	
	/**
	 * 計算兩個Calendar之間相差的微秒數, 取絕對值
	 * @param start_cal
	 * @param end_cal
	 * @return
	 */
	public int getDiffMilliWithABS( Calendar start_cal, Calendar end_cal ){
		
		int diffmilli = 0;
		
		try{
			
			diffmilli = (int) Math.abs(start_cal.getTimeInMillis() - end_cal.getTimeInMillis());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return diffmilli;
	}
	
	/**
	 * 判斷班別是否有設定加班
	 * @param class_no
	 * @return
	 */
	public boolean hasOverTime( Map classMap ){
		
		boolean chk_falg = false;
		
		try{
			//班別設定加班資料且加班基本資料設定為可加班
			if( ((Boolean)classMap.get("OVERTIME_KEY_SWITCH")) && ((Boolean)classMap.get("OVERTIME_SWITCH"))){
				chk_falg = true;
			}
			
		}catch(Exception e){
			chk_falg = false;
			e.printStackTrace();
		}
		
		return chk_falg;
	}
	
	/**
	 * 計算兩日期(格式為 Calendar) 所相差的微秒(MSec)  return long
	 * @param begin_cal
	 * @param end_cal
	 * @return
	 */
	public long caculateDiffMSec(Calendar begin_cal, Calendar end_cal){
		
		long msec = 0;
		try{
			begin_cal.getTime();
			end_cal.getTime();
			
			msec = (end_cal.getTimeInMillis() - begin_cal.getTimeInMillis() );  //日期相距的微秒(MSec)
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return msec;
	}
	
	/**
	 * 計算兩個 Calendar 之間相差的時數
	 * @param begin_cal
	 * @param end_cal
	 * @return
	 */
	public float caculateDiffHour(Calendar begin_cal, Calendar end_cal){			
		  
		long msec = 0;
		float hour = 0;
			
		try{
			msec = this.caculateDiffMSec(begin_cal, end_cal);  //計算兩個Calendar相差的微秒	
			hour = (float) (msec/((float)(1000*60*60)));  //換算成時數
				
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return hour;
	}
	
	/**
	 * 不足補0
	 * @param str
	 * @param strLength
	 * @return
	 */
	public String addZeroForNum(String str, int strLength) {
	    int strLen = str.length();
	    if (strLen < strLength) {
	        while (strLen < strLength) {
	            StringBuffer sb = new StringBuffer();
	            sb.append("0").append(str);// 左補0
	            // sb.append(str).append("0");//右補0
	            str = sb.toString();
	            strLen = str.length();
	        }
	    }

	    return str;
	}
	
	
	
	
	/**
	 * 取得薪資狀態的說明
	 * @param status
	 * @return
	 */
	public String getSalaryStatus( String status ){
		
		String return_status = "";
		
		try{
			
			if("01".equals(status)){
				return_status = "處理中";
			}else if("02".equals(status)){
				return_status = "已完成薪資計算";
			}else if("03".equals(status)){
				return_status = "已確認";
			}else if("04".equals(status)){
				return_status = "已出帳";
			}else if("05".equals(status)){
				return_status = "已結算";
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_status;
	}
	/**
	 * 西元==>民國 日期轉換 (格式:2010/10/21 ==> 99/10/21)
	 * @param date
	 * @return
	 */
	public String ADtoCHI(String date){
		
		//進行轉換 
		String chidate = "";
		try {
			if(!"".equals(date) && date != null){
//				chidate = date.replaceAll("/","").replaceAll(" ", "");
				String temp = String.valueOf((Integer.parseInt(date.substring(0, 4)) - 1911));
				chidate = temp+date.substring(4);
			}
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return chidate;
		
	}
	
	/**
	 * 計算兩日期(格式為 String) 相差的天數   return int
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public int caculateDiffCalDays( String start_date, String end_date ){
		
		int days = 0;
		
		try{
			//取得區間的實際天數
			Calendar count_start_cal = this.covertStringToCalendar(start_date);
			Calendar count_end_cal = this.covertStringToCalendar(end_date);

			days = this.caculateDiffCalDays(count_start_cal, count_end_cal);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return days;
	}
	
	/**
	 * 計算兩日期(格式為 Calendar) 相差的天數   return int
	 * @param start_cal
	 * @param end_cal
	 * @return
	 */
	public int caculateDiffCalDays( Calendar start_cal, Calendar end_cal ){
		
		int days = 0;
		
		try{
			//結束日期 - 開始日期 + 1
			days = (end_cal.get(Calendar.DAY_OF_MONTH) - start_cal.get(Calendar.DAY_OF_MONTH) + 1);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return days;
	}
	
	/**
	 * 帶入SQL語法 取得FormBean 
	 * @param conn
	 * @param sql
	 * @param bean
	 * @return
	 */
	
	public Object getFormBean(Connection conn,String sql,Object bean, String user_name)
	{
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData rsm=rs.getMetaData();
			HashMap map=new HashMap();
			if(rs.next())
				
			for (int i = 1; i <=rsm.getColumnCount(); i++) {
				if(rsm.getColumnType(i)==java.sql.Types.DATE){
					map.put(rsm.getColumnName(i), (rs.getDate(i).toString()));//this.DateToChineseDate(rs.getTimestamp(i)));
				}else if(rsm.getColumnType(i)==java.sql.Types.VARCHAR | rsm.getColumnType(i)==java.sql.Types.CHAR )	{
					String ss="";
					if (rsm.getColumnName(i).equals("USER_CREATE") || rsm.getColumnName(i).equals("USER_UPDATE") ){
						ss= rs.getString(i)==null?"":user_name;
					}else{
						ss= rs.getString(i)==null?"":rs.getString(i);
					}
					map.put(rsm.getColumnName(i), ss.equals("null")?"":ss);
					
				}
				else 
					map.put(rsm.getColumnName(i), rs.getString(i));
			}
			BeanUtils.populate(bean, map);
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bean;
	}
	
	
	
	/**
	 * Convert StringArray To Map
	 * @param string_array
	 */
	public Map stringArrayToMap( String[] string_array ){
		
		Map return_map = new HashMap();
		
		try{
			if( string_array != null && string_array.length > 0){
				for(int i=0; i<string_array.length; i++ ){
					return_map.put(i, string_array[i]);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_map;
	}
	/**
	 * 依據所需要的 cross_type 判斷是否跨午夜十二點
	 * @param classMap
	 * @param cross_type
	 * @return
	 */
	public boolean hasCrossMidnight( Map classMap, int cross_type ){
		
		boolean chk_flag = false;
		
		try{
			//取得是否跨午夜十二點的FLAG
			switch(cross_type){
			
			//下班
			case 2:
				chk_flag = (Boolean) classMap.get("CROSS_MIDNIGHT_WORK_END_TIME");
				break;
				
			//上午下班
			case 3:
				chk_flag = (Boolean) classMap.get("CROSS_MIDNIGHT_SIESTA_START_TIME");
				break;
			
			//下午上班
			case 4:
				chk_flag = (Boolean) classMap.get("CROSS_MIDNIGHT_SIESTA_END_TIME");
				break;
			
			//加班上班
			case 5:
				chk_flag = (Boolean) classMap.get("CROSS_MIDNIGHT_OVERTIME_START_TIME");
				break;
				
			//加班下班
			case 6:
				chk_flag = (Boolean) classMap.get("CROSS_MIDNIGHT_OVERTIME_END_TIME");
				break;	
				
			//提前加班
			case 7:
				chk_flag = (Boolean) classMap.get("CROSS_MIDNIGHT_BEFORE_OVERTIME_START_TIME");
				break;
				
			}
			
		}catch(Exception e){
			chk_flag = false;
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 取得測試ID所需要的SQL
	 * @param table_column
	 * @param emp_id
	 * @return
	 */
	public String getTestIdSQL( String table_column, String[] emp_id ){
		
		String test_sql = "";
		
		try{
			
			//產生測試所需的SQL
			if (!"".equals(emp_id) && emp_id != null && emp_id.length > 0 ){
				test_sql = " AND ( ";
				for(int i=0; i<emp_id.length; i++){
					if(i==0){
						test_sql += " "+table_column+" = '" +emp_id[i]+ "' ";  //員工工號
					}else{
						test_sql += " OR "+table_column+" = '" +emp_id[i]+ "' ";  //員工工號
					}
				}
				test_sql += " ) ";
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return test_sql;
	}

	/**
	 * 依據傳入的西元年日期時間(yyyy/MM/dd HH:mm) 產生 String
	 * @param date
	 * @return
	 */
	public String convertADDatetimeToStrng(Date date){
		
		String return_date = "";
		
		try{
			
			return_date = this.covertDateToString(date, "yyyy/MM/dd HH:mm");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_date;
	}
}
