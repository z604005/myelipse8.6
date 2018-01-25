package com.spon.utils.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;


public class BA_Vaildate {

	private static BA_Vaildate ba_vaildate; 

	/**
	 * 檢核是否為空值
	 * @param in_actionErrors
	 * @param in_parameter
	 * @param in_nameParameter
	 * @param message
	 */
	
	public void isEmpty(ActionErrors in_actionErrors, String in_parameter, String in_nameParameter,String message)
	{
		message=message.equals("")?"不可空白":message;
		  if(GenericValidator.isBlankOrNull(in_parameter))
	        {
	            in_actionErrors.add(in_nameParameter,new ActionMessage(message));
	        }
	}
	
	/**
	 *  * 檢核數字型態是否有值，如果為數值型態必填的數值 zerochk true 可為零, false 不得為零
	 * @param in_actionErrors
	 * @param in_parameter
	 * @param in_nameParameter
	 * @param message
	 * @param zerochk
	 */
	public void isNumEmpty(ActionErrors in_actionErrors, String in_parameter, String in_nameParameter,String message,boolean zerochk)
	{
		message=message.equals("")?"請輸入數值":message;
		if (GenericValidator.isInt(in_parameter) || GenericValidator.isDouble(in_parameter) || GenericValidator.isLong(in_parameter) || GenericValidator.isShort(in_parameter ) ){
		  if(GenericValidator.isBlankOrNull(in_parameter)){
			  in_actionErrors.add(in_nameParameter,new ActionMessage(message));
		  }else if (!zerochk){
			  if ( in_parameter.equals("0") | in_parameter.equals("0.0")) {
				  in_actionErrors.add(in_nameParameter,new ActionMessage(message));
			  }
		  }
		
		}else{
		    in_actionErrors.add(in_nameParameter,new ActionMessage("型態不符"));
		}
		
	}
	/**
	 * 檢核Email格式
	 * @param in_actionErrors
	 * @param in_parameter
	 * @param in_nameParameter
	 * @param message
	 */
	
	public void isEmail(ActionErrors in_actionErrors, String in_parameter, String in_nameParameter,String message)
	{
		message=message.equals("")?"Email格式不正確":message;
		
			 if(!GenericValidator.isEmail(in_parameter))
		     {
				 in_actionErrors.add(in_nameParameter,new ActionMessage(message));
		     }
		
	}
    
	/**
	 * 檢核身分證字號
	 * @param in_actionErrors
	 * @param in_parameter
	 * @param in_nameParameter
	 * @param message
	 */
	public void isTWID(ActionErrors in_actionErrors, String in_parameter, String in_nameParameter,String message)
	{
		message=message.equals("")?"身分證字號不正確":message;
		if( !"".equals(in_parameter) && in_parameter.length()==10)
		{
			String EA="ABCDEFGHJKLMNPQRSTUVWXYZIO";
			if(EA.indexOf(String.valueOf(in_parameter.charAt(0)))== -1){
				 in_actionErrors.add(in_nameParameter,new ActionMessage(message));
			}
			String EB="0123456789";
			for(int j = 2; j <= 10; j++){
				if(EB.indexOf(String.valueOf(in_parameter.charAt(j-1)))== -1){
					 in_actionErrors.add(in_nameParameter,new ActionMessage(message));
				}
			}
			if (in_actionErrors.isEmpty()){
				String v[][] = {{"A", "台北市"}, {"B", "台中市"}, {"C", "基隆市"},
				         {"D", "台南市"}, {"E", "高雄市"}, {"F", "台北縣"}, {"G", "宜蘭縣"},
				         {"H", "桃園縣"}, {"J", "新竹縣"}, {"K", "苗栗縣"}, {"L", "台中縣"},
				         {"M", "南投縣"}, {"N", "彰化縣"}, {"P", "雲林縣"}, {"Q", "嘉義縣"},
				         {"R", "台南縣"}, {"S", "高雄縣"}, {"T", "屏東縣"}, {"U", "花蓮縣"},
				         {"V", "台東縣"}, {"X", "澎湖縣"}, {"Y", "陽明山"}, {"W", "金門縣"},
				         {"Z", "連江縣"}, {"I", "嘉義市"}, {"O", "新竹市"}
				      };
				 
				      int inte = -1;
				      String s1 = String.valueOf(Character.toUpperCase(in_parameter.charAt(0)));
				      for(int i = 0; i < 26; i++){
				         if(s1.compareTo(v[i][0]) == 0){
				            inte = i;
				         }
				      }
				      int total = 0;
				      int all[] = new int[11];
				      String E = String.valueOf(inte + 10);
				      int E1 = Integer.parseInt(String.valueOf(E.charAt(0)));
				      int E2 = Integer.parseInt(String.valueOf(E.charAt(1)));
				      all[0] = E1;
				      all[1] = E2;
				      try{
				         for(int j = 2; j <= 10; j++)
				            all[j] = Integer.parseInt(String.valueOf(in_parameter.charAt(j - 1)));
				         for(int k = 1; k <= 9; k++)
				            total += all[k] * (10 - k);
				         total += all[0] + all[10];
				         if(total % 10 != 0)
				         {
				        	 in_actionErrors.add(in_nameParameter,new ActionMessage(message));
				         }
				      }
				      catch(Exception ee){;
				      }
			}
		}
		else
		{
			 in_actionErrors.add(in_nameParameter,new ActionMessage(message));
		}

	}
	/**
	 * 檢核公司行號統一編號
	 * @param in_actionErrors
	 * @param in_parameter
	 * @param in_nameParameter
	 * @param message
	 */
	public void isTWCompanyId(ActionErrors in_actionErrors, String in_parameter, String in_nameParameter,String message)
	{
		try{
			
			message=message.equals("")?"統一編號不正確":message;
			if(!in_parameter.equals("") & in_parameter.length()==8){
				int v[] = {1,2,1,2,1,2,4,1};
	            int temp=0;
	            int sum=0;
	            int sum1=0;
	            int aAddition=0;
	            for (int i=0;i<v.length;i++){
	                temp= Integer.parseInt(String.valueOf(in_parameter.charAt(i))) * v[i];
	                aAddition = ((temp / 10) + (temp % 10));
	
	                sum += (aAddition == 10)? 1: aAddition;
	                sum1 += (aAddition == 10)? 0: aAddition;
	            }
	            if (sum%10 ==0 ){
	            	
	            }else if(sum1%10 ==0){
	            	
				}else{
	            	in_actionErrors.add(in_nameParameter,new ActionMessage(message));
	            }
			}else{
				 in_actionErrors.add(in_nameParameter,new ActionMessage(message));
			}
			
			
			
//			message=message.equals("")?"統一編號不正確":message;
//			if(!in_parameter.equals("") & in_parameter.length()==8){
//				int v[] = {1,2,1,2,1,2,4,1};
//	            int temp=0;
//	            int sum=0;
//	            int aAddition=0;
//	            for (int i=0;i<v.length;i++){
//	                temp= Integer.parseInt(String.valueOf(in_parameter.charAt(i))) * v[i];
//	                aAddition = ((temp / 10) + (temp % 10));
//	
//	                sum += (aAddition == 10)? 1: aAddition;
//	            }
//	            if (sum%10 ==0 ){
//	            	
//	            }else{
//	            	in_actionErrors.add(in_nameParameter,new ActionError(message));
//	            }
//			}else{
//				 in_actionErrors.add(in_nameParameter,new ActionError(message));
//			}
		} catch (Exception e) {
			in_actionErrors.add(in_nameParameter,new ActionMessage(message));
			
		} finally {
			
		}

	}
	/**
	 * 檢核固定長度
	 * @param in_actionErrors
	 * @param in_parameter
	 * @param in_nameParameter
	 * @param length
	 * @param message
	 */
	public void isFixLength(ActionErrors in_actionErrors, String in_parameter, String in_nameParameter,int length,String message)
	{
		message=message.equals("")?"長度必須為 "+length+" 位":message;
		 if(!GenericValidator.isInRange(in_parameter.getBytes().length,length,length))
	     {
			 in_actionErrors.add(in_nameParameter,new ActionMessage(message));
	     }
	}
	/**
	 * 檢核最小長度
	 * @param in_actionErrors
	 * @param in_parameter
	 * @param in_nameParameter
	 * @param length
	 * @param message
	 */
	public void isLeastLength(ActionErrors in_actionErrors, String in_parameter, String in_nameParameter,int length,String message)
	{
		message=message.equals("")?"長度至少必須為 "+length+" 位":message;
		 if(!GenericValidator.isInRange(in_parameter.getBytes().length,length,99))
	     {
			 in_actionErrors.add(in_nameParameter,new ActionMessage(message));
	     }
	}
	/**
	 * 檢核長度
	 * @param in_actionErrors
	 * @param in_parameter
	 * @param in_nameParameter
	 * @param length
	 * @param message
	 */
	public void isLength(ActionErrors in_actionErrors, String in_parameter, String in_nameParameter,int length,String message)
	{
		message=message.equals("")?"長度不得超過 "+length+" 位":message;
		 if(!GenericValidator.isInRange(in_parameter.length(),0,length))
	     {
			 in_actionErrors.add(in_nameParameter,new ActionMessage(message));
	     }
	}
	/**
	 * 檢核是否為民國日期格式 eq (095/04/17)
	 * @param in_actionErrors
	 * @param in_parameter
	 * @param in_nameParameter
	 * @param length
	 * @param message
	 */
	public void isTWDate(ActionErrors in_actionErrors, String in_parameter, String in_nameParameter,String message)
	{
		in_parameter=in_parameter.replaceAll("/","");
		message=message.equals("")?"日期格式不正確":message;
		
		 if(in_parameter.length()==6)
		 {
			 in_parameter="0"+in_parameter;
		 }
		
		 if(in_parameter.length()<6)
	     {
			 in_actionErrors.add(in_nameParameter,new ActionMessage(message));
	     }
		 else
		 {
			 SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				try {
					in_parameter=(Integer.parseInt(in_parameter.substring(0,3))+1911)+in_parameter.substring(3);
					Date date = (Date) format.parse(in_parameter);
					
					if(!format.format(date).equals(in_parameter))
					{
						in_actionErrors.add(in_nameParameter,new ActionMessage(message));
					}
					
				} catch (ParseException e) {
					in_actionErrors.add(in_nameParameter,new ActionMessage(message));
				}
			 
		 }
		 
	}
	/**
	 * 檢核是否為民國年月格式 eq (095/04)
	 * @param in_actionErrors
	 * @param in_parameter
	 * @param in_nameParameter
	 * @param length
	 * @param message
	 */
	public void isTWYYMM(ActionErrors in_actionErrors, String in_parameter, String in_nameParameter,String message)
	{
		message=message.equals("")?"年月格式不正確":message;
		if (in_parameter.indexOf("/")>-1)
			in_parameter=(BA_TOOLS.getInstance().stringToym(in_parameter)+"");
		
		 if(in_parameter.length()<4)
	     {
			 in_actionErrors.add(in_nameParameter,new ActionMessage("年月欄位不正確"));
	     }
		 else
		 {	
			in_parameter=in_parameter.replaceAll("/","");
			try {
				if ((Integer.parseInt(in_parameter.replaceAll("/",""))+"").length()>5)	
					in_actionErrors.add(in_nameParameter,new ActionMessage(message));
				if (Integer.parseInt(in_parameter.substring(in_parameter.length()-2,in_parameter.length()))>13)
					in_actionErrors.add(in_nameParameter,new ActionMessage(message));
			} catch (Exception e) {
				in_actionErrors.add(in_nameParameter,new ActionMessage(message));
			}
			 
		 }
		 
	}
	/**
	 * 檢核是否為整數格式 eq (integer)
	 * @param in_actionErrors
	 * @param in_parameter
	 * @param in_nameParameter
	 * @param message
	 */
	public void isTypeInt(ActionErrors in_actionErrors, String in_parameter, String in_nameParameter,String message)
	{
		message=message.equals("")?"輸入格式不正確":message;
			 if(!GenericValidator.isInt(in_parameter))
		     {
				 in_actionErrors.add(in_nameParameter,new ActionMessage(message));
		     }
	}
	/**
	 * 檢核是否為長整數格式 eq (integer)
	 * @param in_actionErrors
	 * @param in_parameter
	 * @param in_nameParameter
	 * @param message
	 */
	public void isTypeLong(ActionErrors in_actionErrors, String in_parameter, String in_nameParameter,String message)
	{
		message=message.equals("")?"輸入格式不正確":message;
			 if(!GenericValidator.isLong(in_parameter))
		     {
				 in_actionErrors.add(in_nameParameter,new ActionMessage(message));
		     }
	}
	/**
	 * 檢核是否為長整數格式 eq (integer)
	 * @param in_actionErrors
	 * @param in_parameter
	 * @param in_nameParameter
	 * @param message
	 */
	public void isTypeFloat(ActionErrors in_actionErrors, String in_parameter, String in_nameParameter,String message)
	{
		message=message.equals("")?"輸入格式不正確":message;
			 if(!GenericValidator.isFloat(in_parameter))
		     {
				 in_actionErrors.add(in_nameParameter,new ActionMessage(message));
		     }
	}
	/**
	 * 檢核是否為長整數格式 eq (integer)
	 * @param in_actionErrors
	 * @param in_parameter
	 * @param in_nameParameter
	 * @param message
	 */
	public void isTypeDouble(ActionErrors in_actionErrors, String in_parameter, String in_nameParameter,String message)
	{
		message=message.equals("")?"輸入格式不正確":message;
			 if(!GenericValidator.isDouble(in_parameter))
		     {
				 in_actionErrors.add(in_nameParameter,new ActionMessage(message));
		     }
	}
	
	/**
	 * 判斷兩個欄位值是否相等
	 * @param in_actionErrors
	 * @param in_parameter1
	 * @param in_nameParameter1
	 * @param in_parameter2
	 * @param in_nameParameter2
	 * @param message
	 */
	public void isEquals(ActionErrors in_actionErrors, String in_parameter1, String in_nameParameter1,String in_parameter2, String in_nameParameter2,String message)
	{
		message=message.equals("")?"兩個欄位值不相等":message;
		if(!in_parameter1.equals(in_parameter2))
		{
		 	 in_actionErrors.add(in_nameParameter1,new ActionMessage(message));
		 	 in_actionErrors.add(in_nameParameter2,new ActionMessage(message));
		}
	}
	/**
	 * 檢核是否為日期格式
	 * @param in_actionErrors
	 * @param in_parameter
	 * @param in_nameParameter
	 * @param message
	 */
	public void isTypeDate(ActionErrors in_actionErrors, String in_parameter, String in_nameParameter,String message)
	{ 
		message=message.equals("")?"輸入格式不正確":message;
		try {
			Date date =new Date();
			date.parse(in_parameter);
		}catch (Exception E) {
			in_actionErrors.add(in_nameParameter,new ActionMessage(message));
		}
//		Locale[] locales={Locale.TAIWAN};
//			 if(!GenericValidator.isDate(in_parameter,locales[0]))
//		     {
//				 in_actionErrors.add(in_nameParameter,new ActionError(message));
//		     }
	}
	
	/**
	 * 判斷欄位是否為時間型態
	 * @param in_actionErrors
	 * @param in_parameter
	 * @param in_nameParameter
	 * @param message
	 * @return
	 */
	public void isTimeFormat(ActionErrors in_actionErrors, String in_parameter, String in_nameParameter,String message)
	{
		boolean result = true; 
		message=message.equals("")?"時間欄位輸入不正確":message;
		if (!in_parameter.equals("")){ 
		    try { 
		       // String value = ValidatorUtils.getValueAsString(bean,field.getProperty());
		    	String value =in_parameter;
		        if (!GenericValidator.isBlankOrNull(value)) { 
		            result = Pattern.matches("[0-2][\\d]:[0-5][\\d]:[0-5][\\d]", value); 
		            if (result == true) { 
		                String hour = value.substring(0, 2); 
		                String minute = value.substring(3,5);
		                String secend = value.substring(6,8);
		                int hourInt = Integer.parseInt(hour); 
		                int minuteInt = Integer.parseInt(minute);
		                int secendInt = Integer.parseInt(secend);
		                if (hourInt < 0 || hourInt >= 24 || minuteInt < 0 || minuteInt >= 60 || secendInt<0 || secendInt >=60) { 
		                    result = false; 
		                }  
		            } 
		        } 
		    } catch (NumberFormatException e) { 
		        result = false; 
		        e.printStackTrace(); 
		    } 
		    if (result == false) { 
		    	in_actionErrors.add(in_nameParameter,new ActionMessage(message));
		    } 
		}  
	}
	public static BA_Vaildate getInstance() {
         if (ba_vaildate == null)
        	 ba_vaildate = new BA_Vaildate();
          return ba_vaildate;
    }
	//判斷單位是否所屬單位
	public void startsWith(ActionErrors in_actionErrors, String in_super_unit, String in_unit, String in_nameParameter,String message) {
		message=message.equals("")?"單位欄位輸入不正確":message;
        try {
        	if (!in_unit.equals("") && !in_super_unit.equals("99")) {
	        	if (!in_super_unit.substring(0,2).equals(in_unit.substring(0,2)))	
	        		in_actionErrors.add(in_nameParameter,new ActionMessage(message));
	        	//是出納人員才可以看到所有的資料
	        	if(in_super_unit.length()!=2)
	        		if (in_unit.length()!=7)
	        			in_actionErrors.add(in_nameParameter,new ActionMessage("單位輸入錯誤!!"));
	        	//如果輸入錯誤 
	        	if (in_unit.length()==2 || in_unit.length()==7)
	        	{	        		
	        	}else{
	        		in_actionErrors.add(in_nameParameter,new ActionMessage("單位輸入錯誤!!"));
	        	}
        	}
        }catch (Exception E){
        	E.printStackTrace();
        }
       }
	
	
	
	
	public static void main(String[] age) {
		boolean result = true; 
		String value="07";
		try {
			Pattern p = Pattern.compile("07");
			 Matcher m = p.matcher("07");
			 boolean b = m.matches();
			 System.out.println(b);
		}catch(Exception E){
			System.out.println(E.getMessage());
		}
	}
	
	
	
	/**
	 * 檢查單位代碼是否存在 (兩碼和七碼都檢查)
	 * @param conn 資料庫連結物件
	 * @param in_actionErrors ActionErrors
	 * @param unit 單位代碼
	 * @param in_nameParameter
	 * @param message
	 *   
     * 如果資料庫裡面有這個代碼 回傳 true           找不到回傳false       
	 */
	public void isUnit(Connection conn,ActionErrors in_actionErrors, String unit, String in_nameParameter,String message) throws Exception{
		message=message.equals("")?"所輸入的單位不存在!!":message;
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM SC_UNITM where SC_UNITM_01='" + unit + "' ");
		if (!rs.next()) {
			in_actionErrors.add(in_nameParameter,new ActionMessage(message));
		}
		rs.close();
		stmt.close();
	}
	
	/**
	 * 檢核是否為西元日期格式  ex (2011/01/01)
	 * @param in_actionErrors
	 * @param in_parameter
	 * @param in_nameParameter
	 * @param message
	 */
	public void isADDate(ActionErrors in_actionErrors, String in_parameter, String in_nameParameter,String message)
	{
		in_parameter=in_parameter.replaceAll("/","");
		message=message.equals("")?"yyyy/MM/dd日期格式不正確":message;
		
		 if(in_parameter.length()<8)
	     {
			 in_actionErrors.add(in_nameParameter,new ActionMessage(message));
	     }
		 else
		 {
			 SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				try {
					in_parameter=(Integer.parseInt(in_parameter.substring(0,4)))+in_parameter.substring(4);
					Date date = (Date) format.parse(in_parameter);
					
					if(!format.format(date).equals(in_parameter))
					{
						in_actionErrors.add(in_nameParameter,new ActionMessage(message));
					}
					
				} catch (ParseException e) {
					in_actionErrors.add(in_nameParameter,new ActionMessage(message));
				}
			 
		 }
		 
	}
	
	
	/**
	 * 檢核是否為西元年月格式  ex(2011/06)
	 * @param in_actionErrors
	 * @param in_parameter
	 * @param in_nameParameter
	 * @param length
	 * @param message
	 */
	public void isADYYMM(ActionErrors in_actionErrors, String in_parameter, String in_nameParameter,String message)
	{
		boolean t_flag = false;
		message=message.equals("")?"yyyy/MM年月格式不正確":message;
		
		if (in_parameter.indexOf("/")>-1){
			in_parameter=(BA_TOOLS.getInstance().stringToym(in_parameter)+"");
		}else{
			t_flag = true;
		}
		
		 if(in_parameter.length()<6 && !t_flag)
	     {
			 in_actionErrors.add(in_nameParameter,new ActionMessage("yyyy/MM年月欄位不正確"));
	     }
		 else if(!t_flag)
		 {	
			in_parameter=in_parameter.replaceAll("/","");
			try {
				if ((Integer.parseInt(in_parameter.replaceAll("/",""))+"").length()>6)	
					in_actionErrors.add(in_nameParameter,new ActionMessage(message));
				
//				if (Integer.parseInt(in_parameter.substring(in_parameter.length()-2,in_parameter.length()))>13)
//					in_actionErrors.add(in_nameParameter,new ActionMessage(message));
				
			} catch (Exception e) {
				in_actionErrors.add(in_nameParameter,new ActionMessage(message));
			}
			 
		 }
		 else
		 {
			 in_actionErrors.add(in_nameParameter,new ActionMessage("yyyy/MM年月格式不正確"));
		 }
		 
	}
	
}
