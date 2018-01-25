package fr.improve.struts.taglib.layout.util;

import fr.improve.struts.taglib.layout.LinkTag;

public interface ILinkRenderer {
	void startLink(StringBuffer buffer, LinkTag linkTag, String url, boolean showLink, 
			String styles, String eventHandlers);
	void endLink(StringBuffer buffer, LinkTag linkTag, String text);
}
