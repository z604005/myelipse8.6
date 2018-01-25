package fr.improve.struts.taglib.layout.util;

import java.util.List;
import java.util.MissingResourceException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.Globals;

import fr.improve.struts.taglib.layout.field.AbstractLayoutFieldTag;
import fr.improve.struts.taglib.layout.skin.Skin;

/**
 * Default implementation of the FieldInterface interface.
 * @author jer80876
 */
public class BasicField implements FieldInterface {
	/**
	 * Default implementation uses 2 columns: one for the label, one for the value.
	 */
	public int getColumnNumber() {
		return 2;
	}
	
	/**
	 * Start to display the field.
	 */
	public void doStartField(StringBuffer in_buffer, AbstractLayoutFieldTag in_tag, String in_label, Object in_value) throws JspException {
		// Start the label cell.
		in_buffer.append("<th valign=\"top");
		
		// Set the css class
		if (in_tag.getStyleClass()!=null) {
			in_buffer.append("\" class=\"");
			in_buffer.append(in_tag.getStyleClass());
		}
		
		// Set the layout id of the cell.
		String lc_layoutId = in_tag.getLayoutId();
		if (lc_layoutId!=null) {
			in_buffer.append("\" id=\"");
			in_buffer.append(lc_layoutId);
			in_buffer.append("L");
		}
		
		// Render a span in_tag as some browser don't support to set the style in the <td> in_tag.
		in_buffer.append("\"><span"); 
		if (in_tag.getStyleClass()!=null) {
			in_buffer.append(" class=\"");		
			in_buffer.append(in_tag.getStyleClass());
			in_buffer.append("\"");
		}
		
		// Render a tooltip.
		if (in_tag.getHint()!=null) {
			in_buffer.append(" title=\"");
			in_buffer.append(LayoutUtils.getLabel(in_tag.getPageContext(), in_tag.getBundle(), in_tag.getHint(), null, false));
			in_buffer.append("\"");
		}
		
		// Maybe use a specific style for the label
		if (in_tag.getStyle()!=null) {
			in_buffer.append(" style=\"");
			in_buffer.append(in_tag.getStyle());
			in_buffer.append("\"");
		}
		in_buffer.append(">");
		
		//判斷是否要在Label加入紅色*號 edit by joe 2012/03/26
		boolean chk_add_star = false;
		if(in_label!=null){
			if(in_label.startsWith("*")){
				chk_add_star = true;
				in_label = in_label.substring(1);  //移除原本Key的*號
			}
		}
		
		// Render the label and end of the label cell.
		if (in_label!=null) {
			StringBuffer lc_label = new StringBuffer(in_label);
			for (int i=0;i<lc_label.length();i++) {
				if (lc_label.charAt(i)==' ') lc_label.replace(i,i+1,"&nbsp;");
			}
			in_buffer.append(lc_label.toString());
		} else {
			in_buffer.append("&nbsp;");
		}
		
		//判斷是否要在Label加入紅色*號 edit by joe 2012/03/26
		if(chk_add_star){
			in_buffer.append("<font color=\"red\">*</font>");  //加入紅色*號
		}
		
		in_buffer.append("</span>");

		// help
		if ("AFTER_LABEL".equals(getSkin(in_tag).getProperty(Skin.FIELD_HELP))) {
			doHelp(in_buffer, in_tag);
		}
		
		in_buffer.append("</th>");

		// Prepare to print the value: start the value cell.
		in_buffer.append("<td valign=\"top\" class=\"");
		in_buffer.append(in_tag.getStyleClass());
		
		// Set the layoutId of the cell.
		if (lc_layoutId!=null) {
			in_buffer.append("\" id=\"");
			in_buffer.append(lc_layoutId);
			in_buffer.append("F");	
		}
		
		// Maybe use extra style information.
		String lc_style = getSkin(in_tag).getFormUtils().getFieldValueStyle(in_tag.getPageContext());
		if (lc_style!=null) {
			in_buffer.append("\" style=\"");
			in_buffer.append(lc_style);
		}
		in_buffer.append("\">");		
	}
	
	public void doEndField(StringBuffer in_buffer, AbstractLayoutFieldTag in_tag, Object in_value) throws JspException{
		// Display an image if is Required is set.
		String lc_property = in_tag.getProperty();
		PageContext lc_pageContext = in_tag.getPageContext();
		Skin lc_skin = LayoutUtils.getSkin(lc_pageContext.getSession());
		
		boolean lc_valueSet = in_tag.isFill(in_value);
				
		if (in_tag.isRequired() && !lc_valueSet) {
			in_buffer.append("<img name=\"" + lc_property + "required\" id=\"" + lc_property + "required\" src=\"" + lc_skin.getImageDirectory(lc_pageContext.getRequest()) + "/ast.gif\" alt=\"required\">");
		} else {
			in_buffer.append("<img name=\"" + lc_property + "required\" id=\"" + lc_property + "required\" src=\"" + lc_skin.getImageDirectory(lc_pageContext.getRequest()) + "/clearpixel.gif\">");
		}
		
		// end the field
		in_buffer.append("</td>");

//		if ("AFTER_VALUE".equals(getSkin(in_tag).getProperty(Skin.FIELD_HELP))) {
//			doHelp(in_buffer, in_tag);
//		}
	}

	public void doStartErrors(StringBuffer in_buffer, AbstractLayoutFieldTag in_tag, List in_errors) {
        in_buffer.append("<table><tr><td class=\"");
		in_buffer.append(in_tag.getErrorStyleClass());
		in_buffer.append("\">");		
	}

	public void doEndErrors(StringBuffer in_buffer, AbstractLayoutFieldTag in_tag, List in_errors) throws JspException{
		boolean useErrorsMessages = false;
		try {
			useErrorsMessages = "true".equalsIgnoreCase(getSkin(in_tag).getProperty("error.format"));
		} catch (MissingResourceException e) {
			// do nothing.
		}
		boolean headerPresent = useErrorsMessages && TagUtils.present(in_tag.getPageContext(), in_tag.getBundle(), Globals.LOCALE_KEY, "errors.header");
        boolean footerPresent = useErrorsMessages && TagUtils.present(in_tag.getPageContext(), in_tag.getBundle(), Globals.LOCALE_KEY, "errors.footer");
        boolean prefixPresent = useErrorsMessages && TagUtils.present(in_tag.getPageContext(), in_tag.getBundle(), Globals.LOCALE_KEY, "errors.prefix");
        boolean suffixPresent = useErrorsMessages && TagUtils.present(in_tag.getPageContext(), in_tag.getBundle(), Globals.LOCALE_KEY, "errors.suffix");

		if (headerPresent && in_errors.size()>0) {
			in_buffer.append(TagUtils.message(in_tag.getPageContext(),in_tag.getBundle(),Globals.LOCALE_KEY,"errors.header"));
		}
		for (int i=0;i<in_errors.size();i++) {
			if (prefixPresent) {
				in_buffer.append(TagUtils.message(in_tag.getPageContext(),in_tag.getBundle(),Globals.LOCALE_KEY,"errors.prefix"));
			}

			if ("true".equalsIgnoreCase(LayoutUtils.getSkin(in_tag.getPageContext().getSession()).getProperty("error.display.message"))) {
				in_buffer.append(in_errors.get(i));
			}
			
			if (suffixPresent) {
				in_buffer.append(TagUtils.message(in_tag.getPageContext(),in_tag.getBundle(),Globals.LOCALE_KEY,"errors.suffix"));
			} else {
				if (i<in_errors.size()) in_buffer.append("<br />");
			}
		}
		if (footerPresent && in_errors.size()>0) {
			in_buffer.append(TagUtils.message(in_tag.getPageContext(),in_tag.getBundle(),Globals.LOCALE_KEY,"errors.footer"));
		}
		in_buffer.append("</td></tr></table>");
	}
	
	public void doHelp(StringBuffer in_buffer, AbstractLayoutFieldTag in_tag) throws JspException {
		if ((in_tag.getHelp() != null) || (in_tag.getOnHelpMouseOver() != null)) {
			appendHelp(in_buffer, in_tag);
		}
	}
	
	protected void appendHelp(StringBuffer in_buffer, AbstractLayoutFieldTag in_tag) throws JspException {
		in_buffer.append("<span");
		if (in_tag.getHelpStyleClass() != null) {
			in_buffer.append(" class=\"");
			in_buffer.append(in_tag.getHelpStyleClass());
			in_buffer.append("\"");
		}
		if (in_tag.getOnHelpMouseOver() != null) {
			in_buffer.append(" onMouseOver=\"");
			in_buffer.append(in_tag.getOnHelpMouseOver());
			in_buffer.append("\"");
		}
		if (in_tag.getOnHelpMouseOut() != null) {
			in_buffer.append(" onMouseOut=\"");
			in_buffer.append(in_tag.getOnHelpMouseOut());
			in_buffer.append("\"");
		}
		in_buffer.append(">");
		in_buffer.append(LayoutUtils.getLabel(in_tag.getPageContext(), in_tag.getHelp(), null));
		in_buffer.append("</span>");
	}

	protected Skin getSkin(AbstractLayoutFieldTag tag) {
		return LayoutUtils.getSkin(tag.getPageContext().getSession());
	}	
}