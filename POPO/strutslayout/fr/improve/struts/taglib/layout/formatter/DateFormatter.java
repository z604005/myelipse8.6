package fr.improve.struts.taglib.layout.formatter;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import javax.servlet.jsp.PageContext;


/**
 * Example of a formatter. This one only knows how to format a date.
 * 
 * @version 	1.0
 * @author 	JN Ribette
 */
public class DateFormatter extends DispatchFormatter {
	/**
	 * If in_value is not null, 
	 * cast in_value in a java.util.Date object and format it 
	 * according to the DateFormat.SHORT pattern and the request locale.
	 */
	public String date(Object in_value, PageContext in_pageContext) {
		Date lc_date = (Date) in_value;
		if (lc_date==null) {
			return null;
		} else {
	     	DateFormat lc_format = DateFormat.getDateInstance(DateFormat.SHORT, in_pageContext.getRequest().getLocale());
	     	return lc_format.format(lc_date);     
		}
	}
	
	//Add by John 2012.08.30
	public String money(Object in_value, PageContext in_pageContext) {
		String lc_money =  in_value.toString();
		if (lc_money==null) {
			return null;
		} else {
			DecimalFormat lc_format = new DecimalFormat("###,###");
			double lc_money_d=new Double(lc_money).doubleValue();
			return lc_format.format(lc_money_d);     
		}
	}
}
