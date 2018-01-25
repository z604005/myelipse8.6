package fr.improve.struts.taglib.layout.grid;

import fr.improve.struts.taglib.layout.util.CSSElement;
import fr.improve.struts.taglib.layout.util.Widget;

/**
 * Cell of grid<br>
 * <br>
 * 24 nov. 08
 *
 * @author Gilles Rossi
 *
 */
public class Cell extends Widget implements CSSElement {
	
	private int x;
	private int y;
	private String style;
	private String styleClass;
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getStyleClass() {
		return styleClass;
	}
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public Line getLine() {
		return (Line)getParent();
	}
}
