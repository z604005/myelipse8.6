package fr.improve.struts.taglib.layout.wizard;

import java.util.ArrayList;
import java.util.List;
/**
 * @author mjawhar
 *
 */
public class WizardStep {
	private int index;
	private String title;
	private boolean selected;
	private boolean subStep;
	private List subSteps;

	public WizardStep(int index, String title) {
		this.index = index;
		this.title = title;
		this.subStep = false;
		this.subSteps = new ArrayList();
	}
	
	public int getIndex() {
		return index;
	}
	public String getTitle() {
		return title;
	}
	public boolean isSelected() {
		return selected;
	}
	void setSelected(boolean selected) {
		this.selected = selected;
	}
	public boolean isSubStep() {
		return subStep;
	}
	void setSubStep(boolean subStep) {
		this.subStep = subStep;
	}
	public List getSubSteps() {
		return subSteps;
	}
}
