package fr.improve.struts.taglib.layout.util;

import fr.improve.struts.taglib.layout.wizard.WizardStep;
import fr.improve.struts.taglib.layout.wizard.WizardTag;
import fr.improve.struts.taglib.layout.wizard.WizardToolBar;

/**
 * 
 * 29 janv. 08
 * 
 * @author Gilles Rossi
 * 
 */
public interface IWizardRenderer extends TabsInterface{

	void init(WizardTag wizardTag);

	void renderStartWizard(StringBuffer buffer);

	void renderEndWizard(StringBuffer buffer);

	void renderStartSteps(StringBuffer buffer);

	void renderEndSteps(StringBuffer buffer);

	void renderStartStepsHeader(StringBuffer buffer);

	void renderEndStepsHeader(StringBuffer buffer);

	void renderStartStep(StringBuffer buffer, WizardStep step);
	
	void renderEndStep(StringBuffer buffer, WizardStep step);

	void renderStartContent(StringBuffer buffer);

	void renderEndContent(StringBuffer buffer);

	void renderToolBar(StringBuffer buffer, WizardToolBar toolBar, int currentStep, int stepCount);

	void renderStepSpace(StringBuffer buffer);

	void renderStepHeader(StringBuffer buffer, WizardStep step);
}
