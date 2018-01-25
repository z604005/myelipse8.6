package fr.improve.struts.taglib.layout.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import fr.improve.struts.taglib.layout.PanelTag;
import fr.improve.struts.taglib.layout.skin.Skin;

/**
 * Insert the type's description here. Creation date: (06/06/01 11:40:59)
 * 
 * @author Jean-Noel Ribette
 * @author bbu
 * @author Gilles Rossi
 */
public class BasicPanel implements PanelInterface {
	protected String styleClass;
	private boolean expanded;
	private boolean expandable;
	private PageContext pageContext;
	private String oImg;
	private String namePanel;
	private String idPanel;
	private String cImg;
	private String type;

	private Skin skin;
	protected int colspan;
	protected boolean isNested = false;
	private static final String COLLAPSIBLEPANEL = "collapsible";
	private static final String DEFAULT_LINK = "Tcollapsible";
	private static final String NOM_FUNC = "collapsiblePanelF";

	public void doAfterBody(StringBuffer buffer) {
		doEndBodyContent(buffer);
		buffer.append("</td></tr>");
	}

	public void doBeforeBody(StringBuffer buffer, String align) {
		buffer.append("<tr><td");
		if (styleClass != null) {
			buffer.append(" class=\"").append(styleClass).append("\"");
		}
		// MOD+
		// buffer.append("><table width=\"100%\"");
		buffer.append(">");

		doStartBodyContent(buffer, align);

		// buffer.append("<table width=\"100%\"");

		// if (align!=null) {
		// buffer.append(" align=\"");
		// buffer.append(align);
		// buffer.append("\"");
		// }
		// buffer.append(" border=\"0\">\n");
		// MOD-
	}

	/**
	 * doEndPanel method comment.
	 */
	public void doEndPanel(StringBuffer buffer) {
		buffer.append("</table>");

		buffer.append("</td></tr></table>\n");

	}

	/**
	 * Insert a blank line in the body of the panel
	 */
	public void doPrintBlankLine(StringBuffer buffer, int cols) {
		buffer.append("<tr><td colspan=\"" + cols + "\">&nbsp;</td></tr>\n");
	}

	/**
	 * doPrintTitle method comment.
	 */
	public void doPrintTitle(StringBuffer buffer, String title) {

		if (title != null) {
			buffer.append("<tr><th align=\"center\"");
			if (styleClass != null) {
				buffer.append(" class=\"").append(styleClass).append("\"");
			}
			buffer.append(">");
			if (expandable) {
				String s1 = (String) pageContext.getAttribute(COLLAPSIBLEPANEL);
				if (s1 == null) {
					s1 = "1";
					pageContext.setAttribute(COLLAPSIBLEPANEL, s1);
				} else {
					int i = Integer.parseInt(s1) + 1;
					s1 = "" + i;
					pageContext.setAttribute(COLLAPSIBLEPANEL, s1);
				}

				setIdPanel(s1);
				setNamePanel(COLLAPSIBLEPANEL + s1);
				if (expanded) {
					buffer.append("<input type='hidden' id='" + getNamePanel()
							+ "_Hide'  value='true'/>\n");
				} else {
					buffer.append("<input type='hidden' id='" + getNamePanel()
							+ "_Hide'  value='false'/>\n");
				}
				if (type == null) {
					buffer.append("<img id='icon" + getIdPanel()
							+ "' name='icon" + getIdPanel() + "' src=\"");
				
					if (expanded) {
						buffer.append(oImg);
					} else {
						buffer.append(cImg);
					}

					buffer.append("\"  onClick=\"" + NOM_FUNC + "('icon"
							+ getIdPanel() + "','" + getNamePanel() + "','"+oImg+"','"+cImg+"')\"");

					buffer.append(">");
					if (title != null) {
						buffer.append(" " + title);
					}
					buffer.append("\n");

				} else if (type.equals("radio")) {
					buffer.append("<input type=\"radio\" name=\""
							+ getNamePanel() + getIdPanel() + "\""
							//Add id by John 2014/07/17
							+ " id=\"" + getNamePanel() + getIdPanel() + "\""
							+ " onClick=\"" + NOM_FUNC + "('null','"
							+ getNamePanel() + "','"+oImg+"','"+cImg+"')\"");
					if (expanded) {
						buffer.append(" checked");
					}
					buffer.append(">");
					if (title != null) {
						buffer.append(" " + title);
					}
					buffer.append("\n");
				} else if (type.equals("checkbox")) {
					buffer.append("<input type=\"checkbox\" name=\""
							+ getNamePanel() + getIdPanel() + "\""
							//Add id by John 2014/07/17
							+ " id=\"" + getNamePanel() + getIdPanel() + "\""
							+ " onClick=\"" + NOM_FUNC + "('null','"
							+ getNamePanel() + "','"+oImg+"','"+cImg+"')\"");
					if (expanded) {
						buffer.append(" checked");
					}
					buffer.append(">");
					if (title != null) {
						buffer.append(" " + title);
					}
					buffer.append("\n");
				} else if (type.equals("link")) {
					buffer.append("<a href=\"javascript:void(0)\" "
							+ "onClick=\"" + NOM_FUNC + "('null','"
							+ getNamePanel() + "','"+oImg+"','"+cImg+"')\" >");
					if (title != null) {
						buffer.append(title);
					} else {
						buffer.append(DEFAULT_LINK);
					}
					buffer.append("</a>\n");
				} else {
					buffer.append("<input type=\"checkbox\" name=\""
							+ getNamePanel() + getIdPanel() + "\""
							//Add id by John 2014/07/17
							+ " id=\"" + getNamePanel() + getIdPanel() + "\""
							+ " onClick=\"" + NOM_FUNC + "('null','"
							+ getNamePanel() + "','"+oImg+"','"+cImg+"')\"");
					if (expanded) {
						buffer.append(" checked");
					}
					buffer.append(">");
					if (title != null) {
						buffer.append(" " + title);
					}
					buffer.append("\n");
				}
				// buffer.append(title);
				buffer.append("</th></tr>\n");

			} else {
				buffer.append(title);
				buffer.append("</th></tr>\n");
			}
		}
		// buffer.append("<tr>");
	}

	/**
	 * doStartPanel method comment.
	 */
	public void doStartPanel(StringBuffer buffer, String align, String width) {

		buffer
				.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\"");
		if (align != null) {
			buffer.append(" align=\"");
			buffer.append(align);
			buffer.append("\"");
		}
		if (width != null) {
			buffer.append(" width=\"");
			buffer.append(width);
			buffer.append("\"");
		}
		if (styleClass != null) {
			buffer.append(" class=\"");
			buffer.append(styleClass);
			buffer.append("\"");
		}
		buffer.append("><tr><td valign=\"top\">");
		buffer
				.append("<table cellspacing=\"1\" cellpadding=\"1\" border=\"0\" width=\"100%\">\n");
	}

	public void init(PageContext pg, String in_styleClass, TagSupport in_panel)
			throws JspException {
		this.styleClass = in_styleClass;
		this.pageContext = pg;
		if (in_panel instanceof PanelTag) {
			PanelTag panelTag = (PanelTag) in_panel;
			this.skin = LayoutUtils.getSkin(pg.getSession());
			this.oImg = panelTag.getOpenedImage();
			this.cImg = panelTag.getClosedImage();
			this.expanded = panelTag.isExpanded();
			this.expandable = panelTag.isExpandable();
			this.type = panelTag.getType();
			// this.namePanel = COLLAPSIBLEPANEL;
		}
		colspan = LayoutUtils.getSkin(pg.getSession()).getFieldInterface()
				.getColumnNumber();
	}

	protected void doStartBodyContent(StringBuffer buffer, String align) {
		if (expandable) {
			

			buffer.append("\n  <span id=\"" + getNamePanel() + "\"");
			if (expanded) {
				buffer.append(" style=\"display:block\"");
			} else {
				buffer.append(" style=\"display:none\"");
			}
			buffer.append(">\n");
		}
		buffer.append("<table width=\"100%\"");

		if (align != null) {
			buffer.append(" align=\"");
			buffer.append(align);
			buffer.append("\"");
		}
		buffer.append("	border=\"0\">\n");
	}

	protected void doEndBodyContent(StringBuffer buffer) {
		buffer.append("</table>");
		if (expandable) {
			buffer.append("\n</span>\n");
		}
		
	}

	public String getNamePanel() {
		return namePanel;
	}

	public void setNamePanel(String namePanel) {
		this.namePanel = namePanel;
	}

	public String getIdPanel() {
		return idPanel;
	}

	public void setIdPanel(String idPanel) {
		this.idPanel = idPanel;
	}

}
