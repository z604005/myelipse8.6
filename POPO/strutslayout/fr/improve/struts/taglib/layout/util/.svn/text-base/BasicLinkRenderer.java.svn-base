package fr.improve.struts.taglib.layout.util;

import fr.improve.struts.taglib.layout.LinkTag;

public class BasicLinkRenderer implements ILinkRenderer {

	public void startLink(StringBuffer buffer, LinkTag linkTag, String url,
			boolean showLink, String styles, String eventHandlers) {

		// Generate the opening anchor element
		if (linkTag.getLinkName() != null) {
            StringBuffer results = new StringBuffer("<a name=\"");
            results.append(linkTag.getLinkName());
            //Add id by John 2014/07/17
            results.append("\" id=\"");
            results.append(linkTag.getLinkName());
            results.append("\">");
        } else {
    		buffer.append("<a ");
            
            if (showLink) {
            	buffer.append("href=\"");
            	buffer.append(url);
            	buffer.append("\"");
    	        if (linkTag.getTarget() != null) {
    	        	buffer.append(" target=\"");
    	        	buffer.append(linkTag.getTarget());
    	        	buffer.append("\"");
    	        }
            }
            buffer.append(styles);
            buffer.append(eventHandlers);
            buffer.append(">");
		}
	}

	public void endLink(StringBuffer buffer, LinkTag linkTag, String text) {
        if (text != null)
        	buffer.append(text);
        buffer.append("</a>");
	}
}
