package fr.improve.struts.taglib.layout.renderer;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import fr.improve.struts.taglib.layout.FormTag;
import fr.improve.struts.taglib.layout.tab.TabHeader;
import fr.improve.struts.taglib.layout.util.BasicTabs;
import fr.improve.struts.taglib.layout.util.IWizardRenderer;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;
import fr.improve.struts.taglib.layout.wizard.WizardButton;
import fr.improve.struts.taglib.layout.wizard.WizardStep;
import fr.improve.struts.taglib.layout.wizard.WizardTag;
import fr.improve.struts.taglib.layout.wizard.WizardToolBar;

/**
 * @author mjawhar
 * 
 */
public class BasicWizardRenderer extends BasicTabs implements IWizardRenderer {

	protected static String STYLE_HEADER_ENA = "ongletTextEna";
	protected static String STYLE_HEADER_DIS = "ongletTextDis";
	protected static String STYLE_HEADER_ERR = "ongletTextErr";
	protected static String STYLE_TAB_SPACE = "ongletSpace";
	protected static String STYLE_TAB_MAIN =  "ongletMain";
	protected static String STYLE_TAB_MIDDLE = "ongletMiddle";
	/**
	 * the JSP wizard tag
	 */
	private WizardTag wizardTag;

	/**
	 * Returns the Wizard tag linked to the renderer.
	 * 
	 * @return the wizard tag
	 */
	protected WizardTag getWizardTag() {
		return wizardTag;
	}

	/**
	 * Initializes the renderer with the the tag to render.
	 * 
	 * @param aWizardTag
	 *            the tag to render
	 */
	public void init(WizardTag aWizardTag) {
		this.wizardTag = aWizardTag;
	}

	/**
	 * End wizard content rendering.
	 * 
	 * @param buffer
	 *            output HTML buffer
	 */
	public void renderEndContent(StringBuffer buffer) {
		buffer.append("</div>");
	}

	/**
	 * Start wizard content rendering.
	 * 
	 * @param buffer
	 *            output HTML buffer
	 */
	public void renderStartContent(StringBuffer buffer) {
		buffer.append("<div >");
	}

	/**
	 * Start wizard.
	 * 
	 * @param buffer
	 *            output HTML buffer
	 */
	public void renderStartWizard(StringBuffer buffer) {
		buffer.append("<input type=\"hidden\" id=\"");
		buffer.append(wizardTag.getCurrentStepProperty());
		buffer.append("\"/>");
		buffer.append("<table cellspacing=\"0\" cellpadding=\"0\" "
				+ "border=\"0\" class=\"FORM\">");
		buffer.append("<tr>");
		buffer.append("<td valign=\"top\">");
		buffer.append("<table cellspacing=\"1\" cellpadding=\"1\" "
				+ "border=\"0\" width=\"100%\">");
		buffer.append("<tr>");
		buffer.append("<td class=\"FORM\">");
	}

	public void doPrintHeaders(StringBuffer buffer, String in_tabId,
			Map in_headers) {
		// the headers.
		Iterator lc_it = in_headers.entrySet().iterator();
		while (lc_it.hasNext()) {
			Map.Entry lc_entry = (Map.Entry) lc_it.next();
			String lc_id = (String) lc_entry.getKey();
			TabHeader lc_header = (TabHeader) lc_entry.getValue();
			// 1 header
			buffer.append("<td id=\"tabs");
			buffer.append(in_tabId);
			buffer.append("head");
			buffer.append(lc_id);
			buffer.append("\" class=\"");
			buffer.append(lc_header.styleClass);
			if (lc_header.width != null) {
				buffer.append("\" width=\"");
				buffer.append(lc_header.width);
			}

			buffer.append("\">");

			if (lc_header.href != null) {
				buffer.append("<a href=\"");
				buffer.append(lc_header.href);
				buffer.append("\" class=\"");
				buffer.append(lc_header.styleClass);
				buffer.append("\">");
			}
			buffer.append(lc_header.title);
			if (lc_header.href != null) {
				buffer.append("</a>");
			}
			buffer.append("</td>");

			// 1 separator
			buffer.append("<td width=\"5\" class=\"");
			buffer.append(STYLE_TAB_SPACE);
			buffer.append("\">&nbsp;</td>");
		}

		// 1 separator
		buffer.append("<td class=\"");
		buffer.append(STYLE_TAB_SPACE);
		buffer.append("\">&nbsp;</td></tr>");

		// 1 separator line.
		buffer.append("<tr><td height=\"5\" colspan=\"");
		buffer.append(in_headers.size() * 2 + 1);
		buffer.append("\" class=\"");
		buffer.append(STYLE_TAB_MIDDLE);
		buffer.append("\">&nbsp;</td></tr>");
	}

	/**
	 * Renders a button
	 * 
	 * @param buffer
	 *            output HTML buffer
	 * @param button
	 *            The button to render
	 * @param firstButton
	 *            Is the button the first button in the wizard
	 */
	protected void renderToolBarButton(StringBuffer buffer,
			WizardButton button,boolean firstBT , int currentStep, int stepCount) {
		int id_Value = currentStep; 
		int nb_step = stepCount;

		buffer.append("<td>&nbsp;</td>");
		buffer.append("<td>");
		buffer.append("<input value=\"" + button.getTitle() + "\"");
		if (firstBT || !button.isEnabled()) {
			buffer.append(" disabled=\"disabled\" ");
		}
		buffer.append(" type=\"submit\"");
		buffer.append(" onclick=\"");		
		buffer.append(" this.form.elements['"+FormTag.computeActionParameter(wizardTag.getPageContext())+"']");
		buffer.append(".value='" + button.getReqCode() + "'");
		buffer.append(" ;");
		buffer.append(" selectTab(0," + nb_step + "," + id_Value + ",'"
				+ STYLE_HEADER_ENA + "','" + STYLE_HEADER_DIS + "','"
				+ STYLE_HEADER_ERR + "'");
		if (wizardTag.getSelectedTabKeyName() != null) {
			buffer.append(",'");
			buffer.append(wizardTag.getSelectedTabKeyName());
		} else {
			buffer.append(",'null'");
		}
		TabHeader header=wizardTag.getSelectedTabTitleKey(id_Value);
		if (header != null) {
			buffer.append("','" + header.titleKey
					+ "'");
		} else {
			buffer.append(",'null'");
		}
		buffer
				.append(LayoutUtils.getSkin(
						wizardTag.getPageContext().getSession())
						.isCookieActivated() ? ",'null'" : ",'"
						+ TagUtils.getActionMappingURL("/tab", pageContext)
						+ "'");
		buffer.append(")\"");
		buffer.append(">");

		buffer.append("</td>");

	}

	/**
	 * Renders the button toolbar.
	 * 
	 * @param buffer
	 *            output HTML buffer
	 * @param toolBar
	 *            the tag to render
	 */
	public void renderToolBar(StringBuffer buffer, WizardToolBar toolBar,
			int currentStep, int stepCount) {

		buffer.append("<div id=\"ToolBar\" >");
		buffer.append("<table width=\"100%\"");
		buffer.append(" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
		buffer.append("<tbody>");
		buffer.append("<tr align=\"center\" class=\"" + STYLE_TAB_MAIN + "\">");
		
		toolBar.getFinishButton().setEnabled(false);
		toolBar.getNextButton().setEnabled(false);
		toolBar.getPreviousButton().setEnabled(false);
		
		if (currentStep == stepCount - 1) {
			toolBar.getFinishButton().setEnabled(true);
			
		} else if (currentStep < stepCount) {
			toolBar.getNextButton().setEnabled(true);
		}
		if ((currentStep > 0) && (stepCount > 1)) {
			toolBar.getPreviousButton().setEnabled(true);
		}
		
		renderToolBarButton(buffer, toolBar.getPreviousButton(),false,
				currentStep - 1, stepCount);
		renderToolBarButton(buffer, toolBar.getNextButton(),false, currentStep + 1,
				stepCount);
		renderToolBarButton(buffer, toolBar.getCancelButton(),false, 0,
				stepCount);
		renderToolBarButton(buffer, toolBar.getFinishButton(),false, stepCount-1,
				stepCount);

		buffer.append("</tr>");
		buffer.append("</tbody>");
		buffer.append("</table>");
		buffer.append("</div>");
	}

	public void renderEndSteps(StringBuffer buffer) {
		// TODO Auto-generated method stub

	}

	public void renderEndWizard(StringBuffer buffer) {
		buffer.append("</tr>");
		buffer.append("</table>");
		buffer.append("</td>");
		buffer.append("</tr>");
		buffer.append("</table>");

	}

	public void renderStartSteps(StringBuffer buffer) {
		// TODO Auto-generated method stub

	}

	public void renderStep(StringBuffer buffer, WizardStep step) {
		// TODO Auto-generated method stub

	}

	public void setPageContext(PageContext pageContext) {
		// TODO Auto-generated method stub

	}

	public void renderEndStepsHeader(StringBuffer buffer) {
		// TODO Auto-generated method stub

	}

	public void renderStartStepsHeader(StringBuffer buffer) {
		// TODO Auto-generated method stub

	}

	public void renderStepHeader(StringBuffer buffer, WizardStep step) {
		// TODO Auto-generated method stub

	}

	public void renderStepSpace(StringBuffer buffer) {
		// TODO Auto-generated method stub

	}

	public void renderEndStep(StringBuffer buffer, WizardStep step) {
		// TODO Auto-generated method stub

	}

	public void renderStartStep(StringBuffer buffer, WizardStep step) {
		// TODO Auto-generated method stub

	}

}
