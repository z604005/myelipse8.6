package fr.improve.struts.taglib.layout.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

public class HTMLUtils {
	/**
	 * Write an HTML tag with its attribute.
	 * If XHTML is set, the tag will be closed.
	 * Otherwise not. 
	 */
	public static void generateTag(PageContext pageContext, String tagName, String[][] attributes) throws JspException {
		TagUtils.write(pageContext, "<");
		TagUtils.write(pageContext, tagName);
		for (int i=0;i<attributes.length;i++) {
			TagUtils.write(pageContext, " ");
			writeAttribute(pageContext, attributes[i][0], attributes[i][1]);
		}
		if (TagUtils.isXHTML(pageContext)) {
			TagUtils.write(pageContext, "/>");
		} else {
			TagUtils.write(pageContext, ">");	
		}
	}
	
	private static void writeAttribute(PageContext pageContext, String name, String value) throws JspException {
		TagUtils.write(pageContext,name);
		TagUtils.write(pageContext,"=\"");
		TagUtils.write(pageContext,value);
		TagUtils.write(pageContext,"\"");
	}
	
	
	public static void openTag(PageContext pageContext, String tagName, String[][] attributes) throws JspException {
		TagUtils.write(pageContext, "<");
		TagUtils.write(pageContext, tagName);
		for (int i=0;i<attributes.length;i++) {
			TagUtils.write(pageContext, " ");
			writeAttribute(pageContext, attributes[i][0], attributes[i][1]);
		}
		TagUtils.write(pageContext, ">");
	}
	
	public static void closeTag(PageContext pageContext, String tagName) throws JspException {
		TagUtils.write(pageContext, "</");
		TagUtils.write(pageContext, tagName);
		TagUtils.write(pageContext, ">");
	}
	
	/**
	 * Convert a map of "name=value" into an HTML code like : name1="value1" name2="value2"...

	 * @param attributes A map of "name=value"
	 * @return The HTML representation of the attributes map
	 */
	public static String convertAttributesToHtml(Map attributes) {
		StringBuffer result = new StringBuffer();
		Iterator entries = attributes.entrySet().iterator();
		while (entries.hasNext()) {
			Entry entry = (Entry)entries.next();
			result.append(entry.getKey());
			result.append("=\"");
			result.append(entry.getValue());
			result.append("\"");
		}
		return result.toString();
	}
}
