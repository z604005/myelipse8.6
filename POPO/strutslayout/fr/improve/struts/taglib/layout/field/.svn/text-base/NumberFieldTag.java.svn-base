package fr.improve.struts.taglib.layout.field;

public class NumberFieldTag extends TextFieldTag {
	private String initialOnKeyPress;
	
	public String getJavascriptType() {
		return "NUMBER";
	}
	
	protected void initDynamicValues() {
		super.initDynamicValues();
		initialOnKeyPress = getOnkeypress();
		String checkNumberCode = "return checkNumber(this, event);";
		setOnkeypress(checkNumberCode);
	}
	
	protected void reset() {
		setOnkeypress(initialOnKeyPress);
		super.reset();
	}
	
}
