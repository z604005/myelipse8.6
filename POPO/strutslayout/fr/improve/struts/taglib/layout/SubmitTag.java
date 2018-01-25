package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.skin.Skin;
import fr.improve.struts.taglib.layout.util.LayoutUtils;


/**
 * @author: Jean-No�l Ribette
 */
public class SubmitTag extends ActionTag {
	public SubmitTag() {
		tag = new org.apache.struts.taglib.html.SubmitTag();
	}
	
	protected void copyProperties() throws JspException {
		// Super copy
		super.copyProperties();

		//設定 confirm() or alert()
		String appwait = " return showEMSWait(); ";
			
		String old_onclick = this.getOnclick();
		if(!"".equals(old_onclick) && old_onclick != null){
			if((old_onclick.trim()).endsWith(";")){
				this.setOnclick(old_onclick+" "+appwait);
			}else{
				this.setOnclick(old_onclick+"; "+appwait);
			}
		}else{
			this.setOnclick(" "+appwait);
		}

	}
}
