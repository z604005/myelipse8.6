
package fr.improve.struts.taglib.layout.wizard;

import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.struts.Globals;

import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.skin.Skin;
import fr.improve.struts.taglib.layout.tab.TabsTag;
import fr.improve.struts.taglib.layout.util.IWizardRenderer;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * @author mjawhar
 *
 */
public class WizardTag extends TabsTag { 

	private static final long serialVersionUID = 1L;
	private String previousReqCode;
	private String nextReqCode;
	private String cancelReqCode;
	private String finishReqCode;
	
	private String previousEnabled;
	private String nextEnabled;
	private String cancelEnabled;
	private String finishEnabled;
	
	private String previousKey;
	private String nextKey;
	private String cancelKey;
	private String finishKey;
	private String bundle;

	private String model;
	private boolean hideHeader;
	private String currentStepProperty;

	// Attributes for EL
	private String jspPreviousReqCode;
	private String jspNextReqCode;
	private String jspCancelReqCode;
	private String jspFinishReqCode;
	
	private String jspPreviousEnabled;
	private String jspNextEnabled;
	private String jspCancelEnabled;
	private String jspFinishEnabled;

	private String jspModel;
	private String jspCurrentStepProperty;

	// Other attributes
	private IWizardRenderer renderer;
	
	
	
	protected Class getRendererClass(Skin skin) {
		return skin.getWizardClass(model);
	}

    protected void initDynamicValues() throws JspException {
    super.initDynamicValues();
    	jspPreviousReqCode = previousReqCode;
    	previousReqCode = Expression.evaluate(previousReqCode, pageContext);

    	jspNextReqCode = nextReqCode;
    	nextReqCode = Expression.evaluate(nextReqCode, pageContext);

    	jspCancelReqCode = cancelReqCode;
    	cancelReqCode = Expression.evaluate(cancelReqCode, pageContext);

    	jspFinishReqCode = finishReqCode;
    	finishReqCode = Expression.evaluate(finishReqCode, pageContext);
    	
    	jspPreviousEnabled = previousEnabled;
    	previousEnabled = Expression.evaluate(previousEnabled, pageContext);

    	jspNextEnabled = nextEnabled;
    	nextEnabled = Expression.evaluate(nextEnabled, pageContext);

    	jspCancelEnabled = cancelEnabled;
    	cancelEnabled = Expression.evaluate(cancelEnabled, pageContext);

    	jspFinishEnabled = finishEnabled;
    	finishEnabled = Expression.evaluate(finishEnabled, pageContext);

    	jspCurrentStepProperty = currentStepProperty;
    	currentStepProperty = Expression.evaluate(currentStepProperty, pageContext);
    	
    	jspModel = model;
    	model = Expression.evaluate(model, pageContext);
    	
    	hideHeader=true;
    	
    	
    }
	public int doAfterBody() throws JspException {								
		StringBuffer buffer = new StringBuffer();
		renderer=(IWizardRenderer)super.getTabsRenderer();
		renderer.init(this);

//		// Render the starts (Wizard and Steps)
		renderer.renderStartWizard(buffer);
		TagUtils.writePrevious(pageContext, buffer.toString());		
		super.doAfterBody();
		// Render the end of steps
  	    WizardToolBar toolBar = createToolBar();
  	    Map tabs = getTabs();
     	renderer.renderToolBar(buffer, toolBar, getSelectedTab(), tabs.size());
		renderer.renderEndWizard(buffer);
		TagUtils.writePrevious(pageContext, buffer.toString());		
		return SKIP_BODY;
	}

	private WizardToolBar createToolBar() {
		WizardToolBar toolBar = new WizardToolBar();
		
		// Titles (from ressources)
		if (bundle == null) {
			bundle = Globals.MESSAGES_KEY;
		}
		if (previousKey == null) {
			previousKey = "wizard.previous";
		}
		toolBar.getPreviousButton().setTitle(LayoutUtils.getLabel(pageContext, bundle, previousKey, null, false));
		if (nextKey == null) {
			nextKey = "wizard.next";
		}
		toolBar.getNextButton().setTitle(LayoutUtils.getLabel(pageContext, bundle, nextKey, null, false));
		if (cancelKey == null) {
			cancelKey = "wizard.cancel";
		}
		toolBar.getCancelButton().setTitle(LayoutUtils.getLabel(pageContext, bundle, cancelKey, null, false));
		if (finishKey == null) {
			finishKey = "wizard.finish";
		}
		toolBar.getFinishButton().setTitle(LayoutUtils.getLabel(pageContext, bundle, finishKey, null, false));

		// ReqCode
		if (previousReqCode != null) {
			toolBar.getPreviousButton().setReqCode(previousReqCode);
		}
		if (nextReqCode != null) {
			toolBar.getNextButton().setReqCode(nextReqCode);
		}
		if (cancelReqCode != null) {
			toolBar.getCancelButton().setReqCode(cancelReqCode);
		}
		if (finishReqCode != null) {
			toolBar.getFinishButton().setReqCode(finishReqCode);
		}

		// Enabled or not
		if (previousEnabled != null) {
			toolBar.getPreviousButton().setEnabled(new Boolean(previousEnabled).booleanValue());
		}
		if (nextEnabled != null) {
			toolBar.getNextButton().setEnabled(new Boolean(nextEnabled).booleanValue());
		}
		if (cancelEnabled != null) {
			toolBar.getCancelButton().setEnabled(new Boolean(cancelEnabled).booleanValue());
		}
		if (finishEnabled != null) {
			toolBar.getFinishButton().setEnabled(new Boolean(finishEnabled).booleanValue());
		}
		return toolBar;
	}	

    public void release() {
    	super.release();
    	previousKey = null;
    	nextKey = null;
    	cancelKey = null;
    	finishKey = null;
    	bundle = null ;
    	
    	previousReqCode = null;
    	nextReqCode = null;
    	cancelReqCode = null;
    	finishReqCode = null;
    	
    	previousEnabled = null;
    	nextEnabled = null;
    	cancelEnabled = null;
    	finishEnabled = null;

    	currentStepProperty = null;
    	model = null;
    }
    
    public void reset() {
    	super.reset();
    	renderer = null;
    	
    	
    	previousKey = null;
    	nextKey = null;
    	cancelKey = null;
    	finishKey = null;
    	bundle = null;
    	
    	previousReqCode = jspPreviousReqCode;
    	jspPreviousReqCode = null;

    	nextReqCode = jspNextReqCode;
    	jspNextReqCode = null;
    	
    	cancelReqCode = jspCancelReqCode;
    	jspCancelReqCode = null;
    	
    	finishReqCode = jspFinishReqCode;
    	jspFinishReqCode = null;

    	previousEnabled = jspPreviousEnabled;
    	jspPreviousEnabled = null;

    	nextEnabled = jspNextEnabled;
    	jspNextEnabled = null;
    	
    	cancelEnabled = jspCancelEnabled;
    	jspCancelEnabled = null;
    	
    	finishEnabled = jspFinishEnabled;
    	jspFinishEnabled = null;
    	
    	currentStepProperty = jspCurrentStepProperty;
    	jspCurrentStepProperty = null;
    	
    	model = jspModel;
    	jspModel = null;
    }
	
	public void setPreviousReqCode(String previousReqCode) {
		this.previousReqCode = previousReqCode;
	}
	public void setNextReqCode(String nextReqCode) {
		this.nextReqCode = nextReqCode;
	}
	public void setCancelReqCode(String cancelReqCode) {
		this.cancelReqCode = cancelReqCode;
	}
	public void setFinishReqCode(String finishReqCode) {
		this.finishReqCode = finishReqCode;
	}
	public void setCurrentStepProperty(String currentStepProperty) {
		this.currentStepProperty = currentStepProperty;
	}
	public String getCurrentStepProperty() {
		return currentStepProperty;
	}

	public void setModel(String model) {
		this.model = model;
	}
	public void setPreviousEnabled(String previousEnabled) {
		this.previousEnabled = previousEnabled;
	}
	public void setNextEnabled(String nextEnabled) {
		this.nextEnabled = nextEnabled;
	}
	public void setCancelEnabled(String cancelEnabled) {
		this.cancelEnabled = cancelEnabled;
	}
	public void setFinishEnabled(String finishEnabled) {
		this.finishEnabled = finishEnabled;
	}
	public void setPreviousKey(String previousKey) {
		this.previousKey = previousKey;
	}
	public void setNextKey(String nextKey) {
		this.nextKey = nextKey;
	}
	public void setCancelKey(String cancelKey) {
		this.cancelKey = cancelKey;
	}
	public void setFinishKey(String finishKey) {
		this.finishKey = finishKey;
	}
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}

	public boolean isHideHeader() {
		return hideHeader;
	}

	public void setHideHeader(boolean hideHeader) {
		this.hideHeader = hideHeader;
	}

}
