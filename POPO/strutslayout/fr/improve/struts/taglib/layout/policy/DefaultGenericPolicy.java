package fr.improve.struts.taglib.layout.policy;

import javax.servlet.jsp.PageContext;

import fr.improve.struts.taglib.layout.LayoutTag;

public class DefaultGenericPolicy implements GenericPolicy {

	public short getAuthorizedDisplayMode(String in_policy, LayoutTag tag, PageContext in_pageContext) {
		return Policy.MODE_DISPLAY; // default must be !MODE_NODISPLAY
	}

}
