package fr.improve.struts.taglib.layout.wizard;
/**
 * @author mjawhar
 *
 */
public class WizardToolBar {
	private WizardButton previousButton;
	private WizardButton nextButton;
	private WizardButton cancelButton;
	private WizardButton finishButton;

	public WizardToolBar() {
		previousButton = new WizardButton("previous");
		nextButton = new WizardButton("next");
		cancelButton = new WizardButton("cancel");
		finishButton = new WizardButton("finish");
	}
	
	public WizardButton getPreviousButton() {
		return previousButton;
	}
	public WizardButton getNextButton() {
		return nextButton;
	}
	public WizardButton getCancelButton() {
		return cancelButton;
	}
	public WizardButton getFinishButton() {
		return finishButton;
	}
	
}
