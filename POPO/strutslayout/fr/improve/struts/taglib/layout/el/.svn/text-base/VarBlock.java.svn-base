package fr.improve.struts.taglib.layout.el;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.beanutils.PropertyUtils;

import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.ParentFinder;

/**
 * @author jer80876
 */
public class VarBlock implements Block {
	
	private String name;
	private String property;
	
	public VarBlock(String in_name, String in_property) {
		name = in_name;
		property = in_property;	
	}

	/**
	 * @see com.beaufouripsen.seas.presentation.taglib.el.Block#evaluate(PageContext)
	 */
	public String evaluate(PageContext in_pg)  throws EvaluationException {
		Object lc_bean = null;
		String lc_name = Expression.evaluate(name, in_pg);
		String lc_property = Expression.evaluate(property, in_pg);		
			
		if ("this".equals(lc_name)) {
			lc_bean = ParentFinder.getLastTag(in_pg);
		} else {
			lc_bean = in_pg.findAttribute(lc_name);
		}
		
		// Check if there is an array, not efficient.
		if (lc_bean==null) {
			int lc_indexStart = lc_name.indexOf('[');
			int lc_indexEnd = lc_name.indexOf(']');
			if (lc_indexStart!=-1 && lc_indexEnd!=-1 && lc_indexStart < lc_indexEnd) {
				String lc_beanName = lc_name.substring(0, lc_indexStart);
				Iterator lc_it;
				try {
					lc_it = LayoutUtils.getIterator(in_pg, lc_beanName, null);
				} catch (JspException e) {
					throw new EvaluationException("Could not get iterator for bean " + lc_beanName);
				}
				int lc_pos = Integer.parseInt(lc_name.substring(lc_indexStart+1, lc_indexEnd));
				while (lc_it.hasNext() && lc_pos>=0) {
					lc_bean = lc_it.next();
					lc_pos--;
				}
			}
		}
		
		if (lc_bean==null) {
			return "";	
		}
		
		// Evaluate the property.
		return evaluteProperty(lc_bean, lc_property);
	}
	
	/**
	 * Evaluate the property.
	 * 
	 * @param in_bean		The bean we want a property value
	 * @param in_property	The property to evaluate
	 * @return
	 */
	private String evaluteProperty(Object in_bean, String in_property) {
		String lc_subProperty = null;
		// Check for special case : the bean is a collection and we want its size	
		if (in_bean instanceof Collection && "size".equals(in_property)) {
			Collection lc_collection = (Collection) in_bean;
			return String.valueOf(lc_collection.size());
		}
				
		// Check for a special case : the property is a nested property and the last 
		// nested property is "size", which means we may want the size of a nested collection.
		if (in_property!=null && in_property.endsWith(".size")) {
			lc_subProperty = "size";
			in_property = in_property.substring(0, property.length()-5);
		}
		
		if (in_property!=null) {
			Object lc_value = null;
			try {
				lc_value = PropertyUtils.getProperty(in_bean, in_property);
			} catch (NoSuchMethodException e) {
				throw new EvaluationException("No method to get the property " + in_property + " on a " + in_bean.getClass().getName());
			} catch (InvocationTargetException e) {
				throw new EvaluationException("Getter of property " + in_property + " failed");
			} catch (IllegalAccessException e) {
				throw new EvaluationException("Illegal access to property " + in_property);	
			}
			if (lc_value==null) {
				return "";	
			} else {
				if (lc_subProperty!=null) {
					return evaluteProperty(lc_value, lc_subProperty);
				} else {
					return lc_value.toString();
				}
			}
		}
		return in_bean.toString();
	}
	
	public String toString() {
		return new StringBuffer("[V(").append(name).append(".").append(property).append(")]").toString();
	}

}
