package fr.improve.struts.taglib.layout.wizard;
/**
 * @author mjawhar
 *
 */
public class WizardButton {
	private String title;
	private String reqCode;
	private boolean enabled;
	private boolean highLighted;
	
	public WizardButton(String reqCode) {
		this.reqCode = reqCode;
		this.enabled = true;
		this.highLighted = false;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getReqCode() {
		return reqCode;
	}
	public void setReqCode(String reqCode) {
		this.reqCode = reqCode;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isHighLighted() {
		return highLighted;
	}
	public void setHighLight(boolean highLighted) {
		this.highLighted = highLighted;
		if (highLighted) {
			setEnabled(true);
		}
	}
	
	
}
