package fr.improve.struts.taglib.layout.tab;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import fr.improve.struts.taglib.layout.util.TabsUtil;

/**
 * Standard tab action.
 * This action is used by the Javascript code
 * to inform the server of the client tab selection change
 * when there is no cookie support.
 * 
 * @author jnribette
 */
public class TabAction extends Action {
	/**
	 * Log.
	 */
	private static Log LOG = LogFactory.getLog(TabAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		// Get data.
		String tabGroup = request.getParameter("tabKey");
		String tabValue = request.getParameter("tabValue");
		
		// Update tab state.
		TabsUtil.setCurrentTab(tabGroup, tabValue, request, response);
		
		// Log.
		LOG.debug("Updating selected tab : " + tabValue + " in group " + tabGroup + " is now selected");
		
		// Don't return anything.
		return null;
	}
}
