package com.spon.utils.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.spon.struts.NewDispatchAction;
 
public class LanguageSelect extends NewDispatchAction{
	public ActionForward simplechinese(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.getSession().setAttribute(Globals.LOCALE_KEY, Locale.CHINA);
 
		return mapping.findForward("success");
	}
 
	public ActionForward english(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		request.getSession().setAttribute(Globals.LOCALE_KEY, Locale.US);
 
		return mapping.findForward("success");
	}
 
	public ActionForward traditionalchinese(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
 		request.getSession().setAttribute(Globals.LOCALE_KEY, Locale.TAIWAN);
 
		return mapping.findForward("success");
	}
 
	public ActionForward japanese(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
 		request.getSession().setAttribute(Globals.LOCALE_KEY, Locale.JAPAN);
 
		return mapping.findForward("success");
	}
}