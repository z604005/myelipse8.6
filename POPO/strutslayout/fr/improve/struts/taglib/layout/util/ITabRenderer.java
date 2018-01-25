package fr.improve.struts.taglib.layout.util;

import fr.improve.struts.taglib.layout.tab.TabTag;

public interface ITabRenderer {
	void startTab(StringBuffer buffer, TabTag tabTag, String tabId, boolean selected);
	void endTab(StringBuffer buffer, TabTag tabTag);
}
