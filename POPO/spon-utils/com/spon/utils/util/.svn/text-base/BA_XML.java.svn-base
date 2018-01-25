package com.spon.utils.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.spon.utils.sc.actions.SC006A;

/**
 * 列印報表XML元件
 * @version 1.0
 * @updated 25-八月-2006 下午 01:45:45
 */
public class BA_XML {
	/**
	 * 存放setTxt傳進來的標題和內容資料
	 */
	private StringBuffer txtSb = null;
	/**
	 * 存放setBody傳進來的Vector
	 * Vector裡面固定存放hashmap
	 * 第一組 hashmap 固定存放欄位名稱
	 * 第二組以上才是內容
	 * hashmap的內容key值一定是字串流水號
	 * 例如 HashMap temptxt = new HashMap();
	 * 		temptxt.put("1","會員編號");
	 * 		temptxt.put("2","會員姓名");
	 * 		contens.add(temptxt);
	 * 		temptxt.put("1","001");
	 * 		temptxt.put("2","管理員");
	 * 		contens.add(temptxt);
	 *		將來產生的XML為
	 *		<會員編號>001</會員編號><會員姓名>管理員</會員姓名> 
	 */
	private Vector bodyVector = null;
	
	/**
	 * 存放Excel樣板的名稱如 TEXT.xls
	 */
	public String strExcel_Name = "";

	public BA_XML(){
		txtSb = new StringBuffer();
		bodyVector = new Vector();
	}

	public void finalize() throws Throwable {

	}

	/**
	 * 取得處理後的XML資料
	 */
	public String getXML(){
		StringBuffer sb = new StringBuffer();
		String strTmp = "";
		
		try {
			//組成XML的表頭
			System.out.flush();
			sb.append(strExcel_Name + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sb.append("<Workbook ");
			sb.append(txtSb.toString());	
			sb.append(">");
			//這個是Excel巨集要用的測試資料
//			strTmp +="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
//			strTmp +="<Workbook ";
//			strTmp +=txtSb.toString();
//			strTmp +=">";
//			System.out.println(" str = str & \"" + strTmp.replaceAll("\"","\"\"") + "\"");
			
			HashMap Head1= null;
			//先組表身的標題   <xx></xx> xx的部份
			if (bodyVector.size()>=1){
				Head1=(HashMap)bodyVector.get(0);
			}
			//判斷是否有資料
			//如果沒有資料就傳回一組空的資料
			if (bodyVector.size()>=2){
				for(int i=1;i<bodyVector.size();i++){
					HashMap data1=(HashMap)bodyVector.get(i);
					
//					strTmp = "<Row >";
					sb.append("<Row >");
					for (int j=1;j<=data1.size();j++){
						sb.append("<" + (String)Head1.get(j+"") + ">" + (String)data1.get(j+"") + "</" + (String)Head1.get(j+"") + ">");
//						strTmp+="<" + (String)Head1.get(j+"") + ">" + (String)data1.get(j+"") + "</" + (String)Head1.get(j+"") + ">";
//						if (j % 10==0){
//							System.out.println(" str = str & \"" + strTmp.replaceAll("\"","\"\"") + "\"");
//							strTmp = "";
//						}
					}
					sb.append("</Row>");
//					strTmp +="</Row>";
//					System.out.println(" str = str & \"" + strTmp.replaceAll("\"","\"\"") + "\"");
				}
			}else if(bodyVector.size()==1){
				//表示只有標題沒有資料，此時回傳一組空的資料
				sb.append("<Row >");
				for (int j=1;j<=Head1.size();j++){
					sb.append("<" + (String)Head1.get(j+"") + "></" + (String)Head1.get(j+"") + ">");	
				}
				sb.append("</Row>");
			}
			sb.append("</Workbook>");	
//			strTmp = "</Workbook>";
//			System.out.println(" str = str & \"" + strTmp.replaceAll("\"","\"\"") + "\"");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return sb.toString();
	}

	/**
	 * 傳入查詢後的結果
	 * Vector裡面固定存放hashmap
	 * 第一組 hashmap 固定存放欄位名稱
	 * 第二組以上才是內容
	 * hashmap的內容key值一定是字串流水號
	 * 例如 HashMap temptxt = new HashMap();
	 * 		temptxt.put("1","會員編號");
	 * 		temptxt.put("2","會員姓名");
	 * 		contens.add(temptxt);
	 * 		temptxt.put("1","001");
	 * 		temptxt.put("2","管理員");
	 * 		contens.add(temptxt);
	 *		將來產生的XML為
	 *		<會員編號>001</會員編號><會員姓名>管理員</會員姓名>
	 * 
	 * @param content
	 */
	public void setBody(Vector content){
		bodyVector = content;
	}

	/**
	 * 設定標題與內容
	 * 例如 	BA_XML.setText("年度","95");
	 * 		BA_XML.setText("列印日期","095/08/25");
	 * 		產生出來的XML為
	 * 		<?xml version=\"1.0\" encoding=\"UTF-8\"?>
	 * 		<Workbook 年度="95" 列印日期="095/08/25" ></Workbook>
	 * 
	 * @param content    內容
	 * @param title    標題
	 * 
	 */
	public void setText( String title,String content){
		txtSb.append(" " + title + "=\"" + content +"\" ");
	}

	
	/**
	 * 設定樣本檔Excel的檔名
	 * 例如 	Text.xls
	 * 
	 * @param File_Name  Excel的檔名
	 * 
	 */
	public void setExcelName(String File_Name,HttpServletRequest request,Connection conn){
		SC006A sc006a = new SC006A();
		String str = "";
		try {
			str = sc006a.getSysParam(conn,null, request, "Excel_template_Path");
        } catch (SQLException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        if (!str.substring(str.length()-1,str.length()).equals("/"))str = str +"/";
        strExcel_Name = str + File_Name;
          
	}
}