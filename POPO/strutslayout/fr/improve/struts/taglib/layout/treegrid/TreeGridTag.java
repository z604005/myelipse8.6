package fr.improve.struts.taglib.layout.treegrid;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import fr.improve.struts.taglib.layout.LayoutTagSupport;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * TreeGrid Tag.
 * <br>
 * Unlike collectionTag with model="treegrid", this implementation allow a dynamic number of sub levels.
 * <br><br>
 * 19 nov. 08
 *
 * @author Gilles Rossi
 *
 */
public class TreeGridTag extends LayoutTagSupport {
	
	private String name;
	private String property; // TODO EL ?
	private String model;
	
	public int doEndLayoutTag() throws JspException {
		TreeGrid treeGrid = (TreeGrid)LayoutUtils.getBeanFromPageContext(getPageContext(), name, property);
		treeGrid.view();

		ITreeGridRenderer renderer = LayoutUtils.getSkin(pageContext.getSession()).getTreeGridRenderer(model);
		StringBuffer buffer = new StringBuffer();
		renderer.render(buffer, treeGrid);
		TagUtils.write(getPageContext(), buffer.toString());
		
		return Tag.EVAL_PAGE;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public void setModel(String model) {
		this.model = model;
	}
}
