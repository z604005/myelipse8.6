/*
 * Created on 14 avr. 2004
 *
 * Copyright Improve SA 2004.
 * All rights reserved.
 */
package fr.improve.struts.taglib.layout.collection;

/**
 * Simple bean holding a collectionItem tag attributes.
 * 
 * The instance varibales are public only for compatibility reason, and will be moved to package.
 * 
 * @author jnribette
 */
public class SimpleItemContext implements ItemContext {
	private String arg0;
	private String arg1;
	private String title;
	
	private String onclick;
	private String property;
	private String sortProperty;
	private boolean sorted;
	private boolean ascending;
	private String sortUrl;
	private String filter;
	private String type;
	private String target;	
	private String url;
	private String width;
	
	private String footer;
	private String footerArg0;
	private String footerArg1;
	
	private String item;	
	
	private String mathOperation;
	private String mathPattern;
	
	public SimpleItemContext() {
		// Do nothing.
	}
	
	/**
	 * @param in_context
	 */
	public SimpleItemContext(ItemContext in_context) {
		arg0 = in_context.getArg0();
		arg1 = in_context.getArg1();
		title = in_context.getTitle();
		onclick = in_context.getOnclick();
		property = in_context.getProperty();
		sortProperty = in_context.getSortProperty();
		target = in_context.getTarget();
		url = in_context.getUrl();
		width = in_context.getWidth();
		footer = in_context.getFooter();
		footerArg0 = in_context.getFooterArg0();
		footerArg1 = in_context.getFooterArg1();
		
		item = in_context.getItem();
		mathOperation = in_context.getMathOperation();
		mathPattern = in_context.getMathPattern();
	}
	public String getArg0() {
		return arg0;
	}
	public String getArg1() {
		return arg1;
	}
	public String getItem() {
		return item;
	}
	public String getOnclick() {
		return onclick;
	}
	public String getProperty() {
		return property;
	}
	public String getSortProperty() {
		return sortProperty;
	}
	public String getFilter() {
		return filter;
	}
	public String getTarget() {
		return target;
	}
	public String getTitle() {
		return title;
	}
	public String getUrl() {
		return url;
	}
	public String getWidth() {
		return width;
	}
	public String getMathOperation() {
		return mathOperation;
	}
	public String getMathPattern() {
		return mathPattern;
	}
	public void reset() {
		arg0 = null;
		arg1 = null;		
		item = null;
		
		footerArg0 = null;
		footerArg1 = null;
		footer = null;
		
		onclick = null;
		property = null;
		sortProperty = null;
		target = null;
		title = null;
		url = null;
		width = null;
		mathOperation = null;
		mathPattern = null;
	}
	public String getFooter() {
		return footer;
	}
	public String getFooterArg0() {
		return footerArg0;
	}
	public String getFooterArg1() {
		return footerArg1;
	}
	public void setArg0(String arg0) {
		this.arg0 = arg0;
	}
	public void setArg1(String arg1) {
		this.arg1 = arg1;
	}
	public void setFooter(String footer) {
		this.footer = footer;
	}
	public void setFooterArg0(String footerArg0) {
		this.footerArg0 = footerArg0;
	}
	public void setFooterArg1(String footerArg1) {
		this.footerArg1 = footerArg1;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public void setMathOperation(String mathOperation) {
		this.mathOperation = mathOperation;
	}
	public void setMathPattern(String mathPattern) {
		this.mathPattern = mathPattern;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public void setSortProperty(String sortProperty) {
		this.sortProperty = sortProperty;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setWidth(String width) {
		this.width = width;
	}

	public String getSortUrl() {
		return sortUrl;
	}

	public void setSortUrl(String sortUrl) {
		this.sortUrl = sortUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isSorted() {
		return sorted;
	}

	public void setSorted(boolean sorted) {
		this.sorted = sorted;
	}

	public boolean isAscending() {
		return ascending;
	}

	public void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

}
