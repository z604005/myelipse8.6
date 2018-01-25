//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.0.1/xslt/JavaClass.xsl

package com.spon.utils.sc.forms;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;


import com.spon.utils.util.BA_Vaildate;



/** 
 * MyEclipse Struts
 * Creation date: 02-01-2006
 * 
 * XDoclet definition:
 * @struts.form name="FM0102Form"
 */
public class FU001F extends ActionForm {
	
	
	  protected FormFile FILE1;
	

	public FormFile getFILE1() {
		return FILE1;
	}

	public void setFILE1(FormFile file1) {
		FILE1 = file1;
	}

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request,Connection conn) {
		 ActionErrors l_actionErrors = new ActionErrors();
		 //檢查不可為空白
		 BA_Vaildate ve=BA_Vaildate.getInstance();
		 ve.isEmpty(l_actionErrors,FILE1.getFileName(),"FILE1","");
		
		 //判斷上傳的檔案型態
		 String type=FILE1.getFileName().substring(FILE1.getFileName().lastIndexOf(".")+1,FILE1.getFileName().length()).toUpperCase();
		 if(!type.equals("XLS"))
		 {
			 l_actionErrors.add("FILE1",new ActionMessage("檔案型態不符，請上傳Excel檔案"));
			 
		 }
		 
		 return l_actionErrors;
	}
	
	
	 
		

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
			
		} catch (Exception e) {
		}
	}

	
	

	
}


