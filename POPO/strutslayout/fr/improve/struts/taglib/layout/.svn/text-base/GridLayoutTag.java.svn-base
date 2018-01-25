package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.event.ComputeLayoutSpanEvent;
import fr.improve.struts.taglib.layout.event.ComputeLayoutSpanEventListener;
import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.LayoutEventListener;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.event.StaticCodeIncludeLayoutEvent;
import fr.improve.struts.taglib.layout.event.StaticCodeIncludeListener;
import fr.improve.struts.taglib.layout.util.ILayoutRenderer;
import fr.improve.struts.taglib.layout.util.LayoutUtils;

/**
 * Grid layout component tag.
 * This component layouts its child components from left to right and top to bottom,
 * with the specified number of components per line.
 * @author jnribette
 */
public class GridLayoutTag extends LayoutTagSupport implements LayoutEventListener, ComputeLayoutSpanEventListener, StaticCodeIncludeListener {
	/**
	 * Name of the skin configuration property holding the 
	 * default CSS style class to use.
	 */
	private static final String SKIN_CLASS_PROPERTY = "styleclass.grid";
	
	/**
	 * Number of column.
	 * Default is 2.
	 * This variable is a tag attribute set in the JSP.
	 */
	protected String cols = "2";
	
	/**
	 * Should empty columns should be added to add space  between the child components ?
	 * Default is true.
	 * This variable is a tag attribute set in the JSP.
	 */
	protected boolean space = true;
	
	/**
	 * The CSS class attribute to use.
	 * Default to the value set in the Skin configuration.
	 * This variable is a tag attribute set in the JSP.
	 */
	protected String styleClass;
	
	/**
	 * Width of the grid.
	 * Default is null.
	 * This variable is a tag attribute set in the JSP.
	 */
	protected String width;
	
	/**
	 * Alignment of the grid.
	 * Default is null.
	 * This variable is a tag attribute set in the JSP.
	 */
	protected String align;
	
	/**
	 * HTML border of the grid.
	 * IE does not allows to set this property with css :(
	 * Default is 0.
	 * This variable is a tag attribute set in the JSP.
	 */
	protected int borderSpacing = 0;
	
	/**
	 * HTML id of the grid component.	
	 * Default is 0.
	 * This variable is a tag attribute set in the JSP.
	 */
	protected String id;
	
	/**
	 * HTML height of the grid component.
	 * Default is null.
	 * This variable is a tag attribute set in the JSP.
	 */
	protected String height;
	
	/**
	 * Number of columns for this component.
	 * This variable is reserved for internal use.
	 */
	protected int columns; 
	
	/**
	 * The number of the column we are currently generating.
	 * This variable is reserved for internal use.
	 */
	protected int currentCols = 1;
	
	/**
	 * The number of cols we should span this component.
	 * This variable is reserved for internal use.
	 */
	protected int currentSpan = 1;
	
	/**
	 * The initial value of the styleClass attribute.
	 * This variable is reserved for internal use.
	 */
	protected String jspStyleClass;
	
	/**
	 * The render being in use to generate the component.
	 * This variable is reserved for internal use.
	 */
	protected ILayoutRenderer renderer;
	
	/**
	 * Buffer holding the current static code.
	 * This variable is reserved for internal use.
	 * @see StaticCodeIncludeLayoutEvent
	 * @see StaticCodeIncludeListener
	 */
	protected StringBuffer staticCode = new StringBuffer();
	
	/**
	 * Compute default values.
	 */
	protected void initDynamicValues() {
		// Compute styleClass value.
		jspStyleClass = styleClass;
		if (styleClass==null){
			styleClass = LayoutUtils.getSkin(pageContext.getSession()).getProperty(SKIN_CLASS_PROPERTY, null);
		}
		
		// Compute number of columns.
		columns = Integer.parseInt(cols);
		
		// Get renderer.
		renderer = LayoutUtils.getSkin(pageContext.getSession()).getGridRenderer(null);
	}
	
	/**
	 * Reset values.
	 */
	protected void reset() {
		styleClass = jspStyleClass;
		jspStyleClass = null;
		staticCode = new StringBuffer();
		
		// Reset current column number.
		currentCols = 1;
		
		columns = 2;
		renderer = null;
	}
	
	public int doStartLayoutTag() throws JspException {
		// Send start layout event to parent tag.
		int lc_fieldColumnNumber = LayoutUtils.getSkin(pageContext.getSession()).getFieldInterface().getColumnNumber();
		new StartLayoutEvent(this, "<td colspan=\"" + lc_fieldColumnNumber + "\">").send();
		
		// Start layout.
		renderer.doStartLayout(this);
		
		return EVAL_BODY_INCLUDE;
	}
	public int doEndLayoutTag() throws JspException {				
		// End layout.
		renderer.doEndLayout(this);
		
		// Send end layout event to parent tag.
		new EndLayoutEvent(this, "</td>").send();
		return EVAL_PAGE;
	}
	public void release() {
		super.release();
		cols = "2";
		space = true;
		styleClass = null;
		height = null;
		id = null;
		width = null;
		align = null;
		borderSpacing = 0;
	}
	
	/**
	 * Return true if the column being generated is the last one.
	 */
	public boolean isLast() {
		return currentCols>Integer.parseInt(cols);
	}
	
	/**
	 * Return true if the column being generated is the first one.
	 */
	public boolean isFirst() {
		return currentCols == 1;
	}
	
	public Object processStartLayoutEvent(StartLayoutEvent in_event) throws JspException {
		Object result = renderer.doStartChildLayout(this, in_event);
		currentCols += currentSpan;
		currentSpan = 1;
		return result;
	}
	public Object processEndLayoutEvent(EndLayoutEvent in_event) throws JspException {
		Object result = renderer.doEndChildLayout(this, in_event);
		if (isLast()) {
			currentCols = 1;
		}
		return result;
	}
	
	public Integer computeColspan(ComputeLayoutSpanEvent in_event) throws JspException {
		currentSpan = in_event.getColspan();
		if (space) {
			// Get default colspan.
			int lc_value = ((Integer)in_event.consume(this)).intValue();
			
			// Add an extra cell for the space after each component. 
			lc_value += in_event.getColspan();
			
			// But we add the last one ourself.
			lc_value -= 1;
			
			// Return.
			return new Integer(lc_value);
		} else {
			// We don't alter the number of td.
			return (Integer) in_event.consume(this);
		}
		
	}
	
	public int getCurrentCols() {
		return currentCols;
	}
	
	/**
	 * Sets the cols.
	 * @param cols The cols to set
	 */
	public void setCols(String cols) {
		this.cols = cols;
	}

	/**
	 * Returns the space.
	 * @return boolean
	 */
	public boolean isSpace() {
		return space;
	}

	/**
	 * Sets the space.
	 * @param space The space to set
	 */
	public void setSpace(boolean space) {
		this.space = space;
	}

	/**
	 * Sets the styleClass.
	 * @param styleClass The styleClass to set
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	
	public String getStyleClass() {
		return styleClass;
	}

	/**
	 * Sets the height.
	 * @param height The height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}
	
	public String getHeight() {
		return height;
	}

	/**
	 * Sets the id.
	 * @param id The id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public void setWidth(String in_string) {
		width = in_string;
	}
	
	public String getWidth() {
		return width;
	}
	
	public void setBorderSpacing(int in_spacing) {
		borderSpacing = in_spacing;
	}
	
	public int getBorderSpacing() {
		return borderSpacing;
	}

	public String getAlign() {
		return align;
	}
	
	public void setAlign(String align) {
		this.align = align;
	}
	
	/**
	 * @see fr.improve.struts.taglib.layout.event.StaticCodeIncludeListener#processStaticCodeIncludeEvent(fr.improve.struts.taglib.layout.event.StaticCodeIncludeLayoutEvent)
	 */
	public Object processStaticCodeIncludeEvent(StaticCodeIncludeLayoutEvent in_event) throws JspException {
		String lc_value = (String) in_event.sendToParent(this);
		if (height==null && id==null) {
			return lc_value;
		} else {
			staticCode.append(lc_value);
			return "";
		}
	}
}
