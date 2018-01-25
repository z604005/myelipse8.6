package fr.improve.struts.taglib.layout.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * Useful class to retrieve i18n messages with Struts. 
 * 
 * @author jnribette
 */
public class MessageUtils {
	private static MessageUtils instance = new MessageUtils();
	
	public static MessageUtils getInstance() {
		return instance;
	}
	
	/**
	 * Returns the error messages associated to an input field.
	 * @param pageContext	The page context.
	 * @param bundle		The bundle containing the messages.
	 * @param property		The input field property.
	 */
	public List retrieveErrors(PageContext pageContext, String bundle, String property) throws JspException {
		return retrieveRequestMessages(pageContext, Globals.ERROR_KEY, bundle, property);
	}
	
	/**
	 * Returns the information messages associated to an input field.
	 * @param pageContext	The page context.
	 * @param bundle		The bundle containing the messages.
	 * @param property		The input field property.
	 */
	public List retrieveMessages(PageContext pageContext, String bundle, String property) throws JspException {
		return retrieveRequestMessages(pageContext, Globals.MESSAGE_KEY, bundle, property);
	}
	
	/**
	 * Returns the messages in the request under the specified key associated to an input field.
	 * This method can be used to support additional type of messages (warning for example)
	 * 
	 * @param pageContext	The page context.
	 * @param key			The key to use to lookup for the ActionMessages in the request.
	 * @param bundle		The bundle containing the messages.
	 * @param property		The input field property.
	 */
	public List retrieveRequestMessages(PageContext pageContext, String key, String bundle, String property) throws JspException {
		ActionMessages errors = (ActionMessages) pageContext.getAttribute(key, PageContext.REQUEST_SCOPE);
		List localizedErrors = new ArrayList();
		if (errors != null && !errors.isEmpty()) {
			Iterator iterator = errors.get(property);
			while (iterator != null && iterator.hasNext()) {
				ActionMessage report = (ActionMessage) iterator.next();
				localizedErrors.add(LayoutUtils.getLabel(pageContext, bundle, report.getKey(), report.getValues(), false));
			}
		}
		return localizedErrors;
	}
}
