package fr.improve.struts.taglib.layout.policy;

import javax.servlet.jsp.PageContext;

import fr.improve.struts.taglib.layout.LayoutTag;

public interface GenericPolicy {
	short getAuthorizedDisplayMode(String in_policy, LayoutTag tag, PageContext in_pageContext);
}
