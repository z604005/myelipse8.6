package fr.improve.struts.taglib.layout.grid;

/**
 * Cell of grid body<br>
 * <br>
 * 24 nov. 08
 *
 * @author Gilles Rossi
 *
 */
public class BodyCell extends Cell {

	private String text;
	private Object model;

	public BodyCell() {
	}
	
	public BodyCell(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Object getModel() {
		return model;
	}
	public void setModel(Object model) {
		this.model = model;
	}
}
