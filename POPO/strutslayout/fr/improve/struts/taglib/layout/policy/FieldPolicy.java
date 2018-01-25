package fr.improve.struts.taglib.layout.policy;

import javax.servlet.jsp.PageContext;

public interface FieldPolicy {
	public short getAuthorizedDisplayMode(String in_policy, String in_name, String in_property, PageContext in_pageContext);
}
