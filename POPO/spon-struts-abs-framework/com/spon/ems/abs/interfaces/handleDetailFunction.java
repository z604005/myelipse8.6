package com.spon.ems.abs.interfaces;

import java.sql.Connection;
import java.util.Map;

import org.apache.struts.action.ActionForm;

public interface handleDetailFunction {
	
	public abstract boolean executeAddDetailData( Connection conn, String type, ActionForm form, Map paramMap );
	public abstract boolean executeSaveDetailData( Connection conn, String type, ActionForm form, Map paramMap );
	public abstract boolean executeDelDetailData( Connection conn, String type, ActionForm form, Map paramMap );
	public abstract boolean executeQueryDetailData( Connection conn, String type, ActionForm form, Map paramMap );
	
}
