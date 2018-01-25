package fr.improve.struts.taglib.layout.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import fr.improve.struts.taglib.layout.datagrid.DatagridTag;
import fr.improve.struts.taglib.layout.field.DateFieldTag;

public class RequestUtils {
	private static Set specialAttributes;
	
	static {
		Set s = new HashSet();
		s.add(DateFieldTag.CALENDAR_KEY);
		s.add(DatagridTag.LOADED);
		specialAttributes = Collections.unmodifiableSet(s);		
	}
	
	/**
	 * Return a set of special indicators.
	 * When one of this indicator is present in the request, 
	 * it indicates that some Struts-Layout resource that should be loaded only once per request
	 * has already been loaded.  
	 */
	public static Set getLoadedIndicators() {
		return specialAttributes;
	}
}
