package fr.improve.struts.taglib.layout.field;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.struts.Globals;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.taglib.html.FormTag;

import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.event.StaticCodeIncludeLayoutEvent;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;
/**
 * TextFieldTag which allows to type a date, set the formatter to "date" and display a calendar to help typing the date.
 *
 * @author: Jean-No?l Ribette
 */
public class AbstractDateFieldTag extends TextFieldTag {
	public static final String CALENDAR_KEY = "fr.improve.struts.taglib.layout.field.DateFieldTag.CLANDER_KEY";
	private static final Map dateSymbols = new HashMap();

	/**
	 * Special date format pattern overriding the default pattern for the current struts locale.
	 */
	protected String patternKey;
	protected String pattern;
	protected String startYear;
	protected String endYear; 
	protected String calendarTooltip;
	protected String calendarType;
	
	/**
	 * Find the Locale to use. 
	 * @return the Struts Locale if set, the browser Locale otherwise. 	 
	 */
	protected Locale findLocale() {
		Locale lc_locale = (Locale) pageContext.findAttribute(Globals.LOCALE_KEY);
		if (lc_locale==null) {
			lc_locale = pageContext.getRequest().getLocale();	
		}
	return lc_locale;
	}
	
	protected void buildCalendarTooltip(StringBuffer buffer) throws JspException {
		if (calendarTooltip==null) {
			buffer.append("pick up a date");
		} else {
			buffer.append(LayoutUtils.getLabel(pageContext, bundle, calendarTooltip, null, false));
		}
	}
	
	/**
	 * Include the html and javascript required code for the calendar.
	 */
	protected void includeClientCode(StringBuffer out_buffer, Calendar in_calendar, Locale in_locale) {
				
		//edit by joe 2012/09/14 add jQuery ui datepicker
    	if("datepicker".equals(this.calendarType)){
    		
    		//add jquery ui datepicker
    		out_buffer.append("<script>" +
    						  "jQuery(\"input[name='"+this.getProperty()+"']\").datepicker({" +    						  
    						  " changeYear : true, " +
    						  " changeMonth : true, " +
    						  " autoSize : true, " +	//Edit by Maybe(新日曆)
    						  " monthNamesShort:[\"一月\",\"二月\",\"三月\",\"四月\",\"五月\",\"六月\",\"七月\",\"八月\",\"九月\",\"十月\",\"十一月\",\"十二月\"], " +	//Edit by Maybe(新日曆)
    						  " dayNamesMin:[\"日\",\"一\",\"二\",\"三\",\"四\",\"五\",\"六\"], " +	//Edit by Maybe(新日曆)
    						  " buttonImage: '"+((HttpServletRequest)pageContext.getRequest()).getContextPath()+"/config/calendar.gif', " +
    						  " buttonImageOnly: true, " +
    						  " showOn: 'both', " +
    						  " yearRange: '"+this.startYear+":"+this.endYear+"', "  +
    						  " dateFormat: '"+this.patternKey+"'");	//default is yy/mm/dd --> ex:2013/08/04
    						  /*if(!"YYYY/MM/DD".equals(this.patternKey.toUpperCase())){
    							  out_buffer.append(" dateFormat: '"+this.patternKey+"' "); 
    						  }*/
    		out_buffer.append( "});" +
    						 "</script>");
    		
    		//因為JSCAL2需要付費, 取消此jQuery Plugin 使用
    		/*
    		out_buffer.append("<script>" +
    						  " Calendar.setup({ " +
    						  " trigger    : \""+this.getProperty()+"_trigger\"," +
    						  " inputField : \""+this.getProperty()+"\", " +
    						  " dateFormat : \""+this.patternKey+"\", " +
    						  " min : "+this.startYear+", " +
    						  " max : "+this.endYear+", " +
    						  " onSelect   : function() { this.hide(); } " +
    						  " }); " +
    		    			  "</script>");
    		*/
    	}else{
	        	out_buffer.append("<div id=\"slcalcod\" style=\"position:absolute; left:100px; top:100px; z-index:10; visibility:hidden;\"><script>printCalendar(");
	
				DateFormatSymbols lc_symbols = (DateFormatSymbols) dateSymbols.get(in_locale);
				if (lc_symbols==null) {
					lc_symbols = new DateFormatSymbols(in_locale);
					dateSymbols.put(in_locale, lc_symbols);
				}

				String[] lc_daySymbols = lc_symbols.getShortWeekdays();
				String[] lc_monthSymbols = lc_symbols.getShortMonths();								
				
				// Day names
				for (int i = Calendar.SUNDAY; i <= Calendar.SATURDAY; i++) {
					out_buffer.append("\"").append(lc_daySymbols[i]).append("\",");
				}
				out_buffer.append(in_calendar.getFirstDayOfWeek()).append(",");				
				
				// Month names
				for (int i = Calendar.JANUARY; i <= Calendar.DECEMBER; i++) {
					out_buffer.append("\"").append(lc_monthSymbols[i]).append("\",");
				}

				out_buffer.append(in_calendar.get(Calendar.DAY_OF_MONTH)).append(",");
				out_buffer.append(in_calendar.get(Calendar.MONTH)+1).append(",");
				out_buffer.append(in_calendar.get(Calendar.YEAR));
	        	out_buffer.append(");</script></div>");	
    	}
	        	
	}
	
	
	public void release() {
		super.release();
		//format = "date";
		patternKey = null;
		pattern = null;
		startYear = null;
		endYear = null;
		calendarTooltip = null;
	}
	
	protected Calendar parseDate(Object in_date, Locale in_locale) throws JspException {
		return parseDate(in_date, in_locale, false);
	}
	
	/**
	 * Try to parse the date...
	 */
	protected Calendar parseDate(Object in_date, Locale in_locale, boolean returnNull) throws JspException {
		Calendar lc_calendar = Calendar.getInstance(in_locale);		
		SimpleDateFormat lc_format = null;
		if (patternKey==null) {
			// Default format for the given locale.
			lc_format = (SimpleDateFormat) SimpleDateFormat.getDateInstance(DateFormat.SHORT, in_locale);				
			pattern = lc_format.toPattern();
		} else {
			// Custom format, first look in the specified bundle 
			pattern = LayoutUtils.getLabel(pageContext, bundle, patternKey, null, true);
			if (pattern==null) {
				// If not found, look in the default bundle.
				pattern = LayoutUtils.getLabel(pageContext, patternKey, null);
			}
			lc_format = new SimpleDateFormat(pattern);
		}
		if (in_date!=null && in_date.toString().length()>0) {
			ParsePosition lc_position = new ParsePosition(0);
			Date lc_date = lc_format.parse(in_date.toString(), lc_position);
			if (lc_position.getIndex()==in_date.toString().length() && lc_date!=null) {
				lc_calendar.setTime(lc_date);
			} else if (returnNull) {
				return null;
			}
		}
		return lc_calendar;
	}
	/**
	 * Sets the patternKey.
	 * @param patternKey The patternKey to set
	 */
	public void setPatternKey(String patternKey) {
		this.patternKey = patternKey;
	}

	public void setEndYear(String in_string) {
		endYear = in_string;
	}

	public void setStartYear(String in_string) {
		startYear = in_string;
	}
	
	public void setCalendarTooltip(String in_string) {
		calendarTooltip = in_string;
	}

	public void setCalendarType(String calendarType) {
		this.calendarType = calendarType;
	}
	
}
