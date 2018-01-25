package fr.improve.struts.taglib.layout.field;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
public class DateFieldTag extends AbstractDateFieldTag {

protected void doAfterValue() throws JspException {
	
    if (getFieldDisplayMode() == MODE_EDIT) {
        Locale lc_locale = findLocale();
        StringBuffer buffer = new StringBuffer();
        
        Calendar lc_date = null;
        
      //edit by joe 2012/09/14 add jQuery ui datepicker
        if("datepicker".equals(this.calendarType)){
        	//buffer.append("<a href=\"javascript://\" onclick=\" return false; showCalendar(");
        	//buffer.append("<a href=\"javascript://\" onclick=\" return false; ");
        }else{
        	buffer.append("<a href=\"javascript://\" onclick=\"showCalendar(");
        	Object lc_value = getFieldValue();		
    		lc_date = parseDate(lc_value==null ? "" : lc_value.toString(), lc_locale);
    		String lc_year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

            buffer.append(lc_date.get(Calendar.YEAR));
            buffer.append(",");
            buffer.append(lc_date.get(Calendar.MONTH) + 1);
            buffer.append(",");
            buffer.append(lc_date.get(Calendar.DAY_OF_MONTH));
            buffer.append(",");
            if (pattern==null) {
            	buffer.append("null");
            } else {
            	buffer.append("'");
            	buffer.append(pattern);
            	buffer.append("'");
            }	        
            buffer.append(",'");
            FormTag lc_formTag = (FormTag) pageContext.findAttribute(Constants.FORM_KEY);
            if (lc_formTag==null) {
            	throw new JspException("<layout:date> tag is not nested into a <html:form> or <layout:form> tag");
            }
            String lc_formName = lc_formTag.getBeanName(); // For Struts 1.0, the property to use is name.         
            buffer.append(lc_formName);
            buffer.append("','");
            buffer.append(property);
            buffer.append("',event,");
            buffer.append(startYear==null ? String.valueOf(lc_date.get(Calendar.YEAR)) : Expression.evaluate(startYear,pageContext));
            buffer.append(",");
            buffer.append(endYear==null  ? (startYear==null ? String.valueOf(lc_date.get(Calendar.YEAR)+2) : lc_year) : Expression.evaluate(endYear, pageContext));
            buffer.append(");");
        }
        
      //edit by joe 2012/09/14 add jQuery ui datepicker
        if("datepicker".equals(this.calendarType)){
        	/*
        	buffer.append("\"><img id=\""+this.getProperty()+"_trigger\" alt=\"");
        	buildCalendarTooltip(buffer);
            buffer.append("\" border=\"0\" src=\"");

            buffer.append(getSkin().getImageDirectory(pageContext.getRequest()));
            buffer.append("/");
            buffer.append(getSkin().getProperty("layout.calendar"));
            buffer.append("\"></a>");
           	*/
        }else{
        	buffer.append("\"><img alt=\"");
        	buildCalendarTooltip(buffer);
            buffer.append("\" border=\"0\" src=\"");

            buffer.append(getSkin().getImageDirectory(pageContext.getRequest()));
            buffer.append("/");
            buffer.append(getSkin().getProperty("layout.calendar"));
            buffer.append("\"></a>");
        }
        
        
        
        if (pageContext.getRequest().getAttribute(CALENDAR_KEY)==null ) {
        	// include thre required HTML / javascript code.
        	StringBuffer lc_buffer2 = new StringBuffer();
        	includeClientCode(lc_buffer2, lc_date, lc_locale);
        	buffer.append(new StaticCodeIncludeLayoutEvent(this, lc_buffer2.toString()).send());
        	if(!"datepicker".equals(this.calendarType)){
        		pageContext.getRequest().setAttribute(CALENDAR_KEY, "");
        	}
        }
        TagUtils.write(pageContext, buffer.toString());
        
        super.doAfterValue();
    }
}

protected boolean doBeforeValue() throws JspException {
	
	boolean bln = false;
	bln  = super.doBeforeValue();
	
	if (bln){
		if (this.getOnblur()== null){
			this.setOnblur("layoutdateonblur(this);");
		}else{
			if (this.getOnblur().indexOf("layoutdateonblur") < 0){
				this.setOnblur(this.getOnblur()+";layoutdateonblur(this);");
			}
		}
		if (this.getOnfocus()== null){
			this.setOnfocus("layoutdateonfocus(this);");
		}else{
			if (this.getOnfocus().indexOf("layoutdateonfocus") < 0){
				this.setOnfocus(this.getOnblur()+";layoutdateonfocus(this);");
			}
		}
		return true;
	}else{
		return false;
	}
}

}
