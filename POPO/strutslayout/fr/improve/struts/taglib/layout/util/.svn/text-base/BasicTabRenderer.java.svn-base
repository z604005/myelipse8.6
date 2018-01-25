package fr.improve.struts.taglib.layout.util;

import fr.improve.struts.taglib.layout.tab.TabTag;

public class BasicTabRenderer implements ITabRenderer {

	public void startTab(StringBuffer buffer, TabTag tabTag, String tabId, boolean selected) {
		buffer.append("<div id=\"");
		buffer.append(tabId);
		if (!selected) {
			buffer.append("\" style=\"display:none;");	
		}
		buffer.append("\"><table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"clsAction\">");

	}

	public void endTab(StringBuffer buffer, TabTag tabTag) {
		buffer.append("</table></div>");
	}
}
