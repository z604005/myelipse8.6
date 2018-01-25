package fr.improve.struts.taglib.layout.skin;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import fr.improve.struts.taglib.layout.formatter.AbstractFormatter;
import fr.improve.struts.taglib.layout.policy.FieldPolicy;
import fr.improve.struts.taglib.layout.policy.GenericPolicy;
import fr.improve.struts.taglib.layout.sort.SortRules;
import fr.improve.struts.taglib.layout.treegrid.ITreeGridRenderer;
import fr.improve.struts.taglib.layout.util.FieldInterface;
import fr.improve.struts.taglib.layout.util.FormUtilsInterface;
import fr.improve.struts.taglib.layout.util.HTMLUtils;
import fr.improve.struts.taglib.layout.util.IButtonImageRenderer;
import fr.improve.struts.taglib.layout.util.ICrumbRenderer;
import fr.improve.struts.taglib.layout.util.ILayoutRenderer;
import fr.improve.struts.taglib.layout.util.IMenuRenderer;
import fr.improve.struts.taglib.layout.util.IPagerRenderer;
import fr.improve.struts.taglib.layout.util.IToolbarRenderer;
import fr.improve.struts.taglib.layout.util.IWizardRenderer;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;
import fr.improve.struts.taglib.layout.util.TreeviewInterface;

/**
 * This class represents a skin. A skin is a set of properties that can be
 * changed to get a different visual rendering.<br>
 * The customable properties include:
 * <ul>
 * <li>The css skin to include in an HTML file</li>
 * <li>The web directory in which are located the images</li>
 * <li>The web directory in which are located the css file and the javascripts</li>
 * <li>The implementor class of the PanelInterface to use to render a panel</li>
 * <li>The implementor class of the CollectionInterface to use to render a
 * collection</li>
 * <li>The formatter class to use to format text, number and dates</li>
 * <li>The policy class to use to check for user habilitations</li>
 * <li>The pictogram to use to sort a collection</li>
 * 
 * @author jnribette
 */
public class Skin {
	public static final String CSS_FILE = "skin";
	public static final String IMAGE_DIR = "directory.images";
	public static final String CONFIG_DIR = "directory.config";
	public static final String CSS_DIR = "directory.css";
	public static final String SCRIPTS_DIR = "directory.scripts";
	public static final String IMPORTED_SCRIPTS = "skin.scripts";
	public static final String NULL_FIELDS = "display.null.fields";
	public static final String PANEL_CLASS = "panel.class";
	public static final String COLLECTION_CLASS = "collection.class";
	public static final String TABS_CLASS = "tabs.class";
	public static final String TAB_CLASS = "tab.class";
	public static final String FIELD_CLASS = "field.class";
	public static final String FORMATTER_CLASS = "formatter.class";
	public static final String POLICY_CLASS = "policy.class";
	public static final String LINK_CLASS = "link.class";

	public static final String COLLAPSIBLE_CLASS = "collapsible.class";

	public static final String FOLLOW_LINKS_IF_CHANGED = "follow.change";
	public static final String DISPLAY_ERRORS_MESSAGE = "error.display";
	public static final String FOCUS_FIRST_ERROR_FIELD = "error.focus";
	public static final String SORT_KEEP_ERROR_MESSAGE = "sort.error.keep";
	public static final String SORT_TOKEN_REQUIRED = "sort.token.required";
	public static final String LINK_TOKEN_INCLUDE = "link.token.include";
	public static final String FORMUTIL_CLASSS = "formutils.class";
	public static final String TREEVIEW_CLASSS = "treeview.class";
	public static final String PAGER_CLASS = "pager.class";
	public static final String POPUP_CLASS = "popup.class";
	public static final String CRUMB_CLASS = "crumb.class";
	public static final String BUTTON_CLASS = "button.class";
	public static final String MENU_CLASS = "menu.class";
	public static final String GRID_CLASS = "grid.class";
	public static final String TOOLBAR_CLASS = "toolbar.class";
	public static final String WIZARD_CLASS = "wizard.class";
	public static final String TREEGRID_CLASS = "treegrid.class";
	public static final String GENERIC_POLICY_CLASS = "genericPolicy.class";

	public static final String TREE_NUMBER_MENUS_LOADED = "tree.numberOfMenusLoaded";
	public static final String NESTED_COMPATILIBTY_PROPERTY = "nested.compatibility";
	public static final String TREEVIEW_ACTION = "treeview.action";
	public static final String EL_CHARACTER = "el.character";
	public static final String AUTO_SKIP = "autoskip.active";
	public static final String COOKIE = "cookie.active";
	public static final String FIELD_HELP = "field.help";

	private static String resourcesName = "Struts-Layout";

	private static Map skins = new Hashtable();

	private ResourceBundle resources;
	private ResourceBundle defaultResources;
	private Locale locale;
	private AbstractFormatter formatter;
	private FieldPolicy fieldPolicy;
	private GenericPolicy genericPolicy;
	private FormUtilsInterface formutils;
	private TreeviewInterface treeview;
	private String configDir;
	private String imageDir;
	private String cssDir;
	private String scriptsDir;
	private boolean nestedCompatbility;

	/**
	 * Require a valid transaction token to sort collections. (ie allow or not
	 * to use the back button).
	 */
	private boolean sortTokenRequired;

	/**
	 * Include the transaction token in links.
	 */
	private boolean linkTokenRequired;

	/**
	 * Number of menus loaded at the same time.
	 */
	private int numberOfMenusLoaded;

	private FieldInterface fieldInterface;
	private IPagerRenderer pagerRenderer;
	private ICrumbRenderer crumbRenderer;
	private IButtonImageRenderer buttonRenderer;
	private IMenuRenderer menuRenderer;
	private ILayoutRenderer gridRenderer;
	private IToolbarRenderer toolbarRenderer;
	private IWizardRenderer wizardRenderer;
	private ITreeGridRenderer treeGridRenderer;

	/**
	 * Sorting rules.
	 */
	private Map sortingRules = new HashMap();

	/**
	 * Is the skin xhtml ?
	 */
	private boolean xhtml;

	public static void setResourcesName(String in_name) {
		resourcesName = in_name;
	}

	private Skin(String in_name, String in_locale) {
		// Create a fake locale.
		locale = new Locale(in_name, in_locale);
		try {
			// Load the resources.
			resources = loadBundles(resourcesName, locale, this);
			defaultResources = loadBundles("Struts-Layout", null, this);

			// Initialize the formatter.
			String lc_formatterClass = getProperty(FORMATTER_CLASS);
			if (lc_formatterClass != null && lc_formatterClass.length() > 0) {
				formatter = (AbstractFormatter) loadClass(lc_formatterClass,
						this).newInstance();
			}

			// Initialize the policy.
			String lc_policyClass = getProperty(POLICY_CLASS);
			if (lc_policyClass != null && lc_policyClass.length() > 0) {
				fieldPolicy = (FieldPolicy) loadClass(lc_policyClass, this)
						.newInstance();
			}

			// Initialize the field interface.
			String lc_fieldInterfaceClass = getProperty(FIELD_CLASS);
			fieldInterface = (FieldInterface) loadClass(lc_fieldInterfaceClass,
					this).newInstance();

			// Initialize the formutil interface
			String lc_formUtilsClass = getProperty(FORMUTIL_CLASSS);
			formutils = (FormUtilsInterface) loadClass(lc_formUtilsClass, this)
					.newInstance();

			// Initialize the treeview interface
			String lc_treeviewClass = getProperty(TREEVIEW_CLASSS);
			treeview = (TreeviewInterface) loadClass(lc_treeviewClass, this)
					.newInstance();

			// Initialize the pager renderer.
			String lc_pagerRendererClass = getProperty(PAGER_CLASS);
			pagerRenderer = (IPagerRenderer) loadClass(lc_pagerRendererClass,
					this).newInstance();

			// Initialize the crumb renderer.
			String lc_crumbRendererClass = getProperty(CRUMB_CLASS);
			crumbRenderer = (ICrumbRenderer) loadClass(lc_crumbRendererClass,
					this).newInstance();

			// Initialize the button renderer.
			String lc_buttonRendererClass = getProperty(BUTTON_CLASS);
			buttonRenderer = (IButtonImageRenderer) loadClass(
					lc_buttonRendererClass, this).newInstance();

			// Initialize the menu renderer.
			String lc_menuRendererClass = getProperty(MENU_CLASS);
			menuRenderer = (IMenuRenderer) loadClass(lc_menuRendererClass, this)
					.newInstance();

			// Initialize the grid renderer.
			String lc_gridRendererClass = getProperty(GRID_CLASS);
			gridRenderer = (ILayoutRenderer) loadClass(lc_gridRendererClass,
					this).newInstance();

			// Initialize the toolbar renderer.
			String lc_toolbarRendererClass = getProperty(TOOLBAR_CLASS);
			toolbarRenderer = (IToolbarRenderer) loadClass(
					lc_toolbarRendererClass, this).newInstance();

			// Initialize the wizard renderer.
			String lc_wizardRendererClass = getProperty(WIZARD_CLASS);
			wizardRenderer = (IWizardRenderer) loadClass(
					lc_wizardRendererClass, this).newInstance();

			// Initialize the tree-grid renderer.
			String lc_treeGridRendererClass = getProperty(TREEGRID_CLASS);
			treeGridRenderer = (ITreeGridRenderer) loadClass(
					lc_treeGridRendererClass, this).newInstance();

			// Initialize the generic policy renderer.
			String lc_genericPolicyClass = getProperty(GENERIC_POLICY_CLASS);
			genericPolicy = (GenericPolicy) loadClass(lc_genericPolicyClass,
					this).newInstance();

			// Initialize overable variables
			configDir = getProperty(CONFIG_DIR);
			imageDir = getProperty(IMAGE_DIR);

			sortTokenRequired = Boolean.valueOf(
					getProperty(SORT_TOKEN_REQUIRED)).booleanValue();
			linkTokenRequired = Boolean
					.valueOf(getProperty(LINK_TOKEN_INCLUDE)).booleanValue();
			numberOfMenusLoaded = Integer.valueOf(
					getProperty(TREE_NUMBER_MENUS_LOADED)).intValue();
			nestedCompatbility = Boolean.valueOf(
					getProperty(NESTED_COMPATILIBTY_PROPERTY)).booleanValue();

		} catch (MissingResourceException e) {
			throw new BadSkinConfigurationException(e);
		} catch (ClassNotFoundException cnfe) {
			throw new BadSkinConfigurationException(cnfe);
		} catch (IllegalAccessException iae) {
			throw new BadSkinConfigurationException(iae);
		} catch (InstantiationException ie) {
			throw new BadSkinConfigurationException(ie);
		} catch (ClassCastException cce) {
			throw new BadSkinConfigurationException(cce);
		}

		loadStringRules();

		try {
			cssDir = getProperty(CSS_DIR);
			scriptsDir = getProperty(SCRIPTS_DIR);
		} catch (MissingResourceException e) {
			// cssDir not required.
		}
	}

	protected static ResourceBundle loadBundles(String in_name,
			Locale in_locale, Object in_object) {
		ClassLoader lc_loader = Thread.currentThread().getContextClassLoader();
		if (lc_loader == null) {
			lc_loader = in_object.getClass().getClassLoader();
		}
		Locale lc_locale = in_locale;
		if (lc_locale == null) {
			lc_locale = Locale.getDefault();
		}
		return ResourceBundle.getBundle(resourcesName, lc_locale, lc_loader);
	}

	protected static Class loadClass(String in_name, Object in_object)
			throws ClassNotFoundException {
		ClassLoader lc_loader = Thread.currentThread().getContextClassLoader();
		if (lc_loader == null) {
			lc_loader = in_object.getClass().getClassLoader();
		}
		return lc_loader.loadClass(in_name);
	}

	/**
	 * Load String sorting rules.
	 */
	protected void loadStringRules() {
		Enumeration enumeration = resources.getKeys();
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			if (key.startsWith("sort.rules.") && key.endsWith(".class")) {
				// Found a rules.
				int start = 11;
				int end = key.lastIndexOf('.');
				String locale = end > start ? key.substring(start, end) : "";
				String className = resources.getString(key).trim();
				try {
					Class clazz = loadClass(className, this);
					SortRules rules = (SortRules) clazz.newInstance();
					sortingRules.put(locale, rules);
				} catch (ClassNotFoundException e) {
					throw new BadSkinConfigurationException(
							"String class rules " + className + " not found");
				} catch (InstantiationException e) {
					throw new BadSkinConfigurationException(
							"Could not instanciate class rules " + className
									+ " : " + e.getMessage());
				} catch (IllegalAccessException e) {
					throw new BadSkinConfigurationException(
							"Could not instanciate class rules " + className
									+ " : " + e.getMessage());
				} catch (ClassCastException e) {
					throw new BadSkinConfigurationException(
							"String class rules " + className
									+ " is not an instance of SortRules");
				}
			}
		}
	}

	/**
	 * Return String sorting rules.
	 */
	public SortRules getSortRules(Locale in_locale) {
		if (in_locale == null) {
			return (SortRules) sortingRules.get("");
		} else {
			String key = in_locale.toString();
			SortRules rules = null;
			while (rules == null && key != null) {
				rules = (SortRules) sortingRules.get(key);
				if (rules == null) {
					int position = key.indexOf('_');
					if (position != -1) {
						key = key.substring(0, position);
					} else if (key != null && key.length() > 0) {
						key = "";
					} else {
						key = null;
					}
				}
			}
			return rules;
		}
	}

	public static Skin getSkin(String in_name, String in_locale) {
		if (LayoutUtils.getNoErrorMode()) {
			// Create a new skin in no error mode
			// This allows to work on different project at the same in Eclipse.
			return new Skin(in_name, in_locale);
		}
		Skin lc_skin = (Skin) skins.get(in_name + "_" + in_locale);
		if (lc_skin == null) {
			lc_skin = new Skin(in_name, in_locale);
			skins.put(in_name + "_" + in_locale, lc_skin);
		}
		return lc_skin;
	}

	public String getImageDirectory(ServletRequest in_request) {
		HttpServletRequest lc_request = (HttpServletRequest) in_request;
		if (imageDir.charAt(0) == '/') {
			return imageDir;
		} else {
			return lc_request.getContextPath() + '/' + imageDir;
		}
	}

	public void setImageDir(String in_imageDir) {
		imageDir = in_imageDir;
	}

	public String getConfigDirectory(ServletRequest in_request) {
		HttpServletRequest lc_request = (HttpServletRequest) in_request;
		if (configDir.charAt(0) == '/') {
			return configDir;
		} else {
			return lc_request.getContextPath() + '/' + configDir;
		}
	}

	public String getCssDirectory(ServletRequest in_request) {
		if (cssDir == null || cssDir.length() == 0) {
			return getConfigDirectory(in_request);
		}
		HttpServletRequest lc_request = (HttpServletRequest) in_request;
		if (cssDir.charAt(0) == '/') {
			return cssDir;
		} else {
			return lc_request.getContextPath() + '/' + cssDir;
		}
	}

	public String getScriptsDirectory(ServletRequest in_request) {
		if (scriptsDir == null || scriptsDir.length() == 0) {
			return getConfigDirectory(in_request);
		}
		HttpServletRequest lc_request = (HttpServletRequest) in_request;
		if (scriptsDir.charAt(0) == '/') {
			return scriptsDir;
		} else {
			return lc_request.getContextPath() + '/' + scriptsDir;
		}
	}

	public void setConfigDir(String in_configDir) {
		configDir = in_configDir;
	}

	public boolean getDisplayNullFields() {
		return "true".equalsIgnoreCase(getProperty(NULL_FIELDS));
	}

	public boolean getFocusFirstErrorField() {
		return "true".equalsIgnoreCase(getProperty(FOCUS_FIRST_ERROR_FIELD,
				"false"));
	}

	public boolean getSortKeepErrorMessage() {
		return "true".equalsIgnoreCase(getProperty(SORT_KEEP_ERROR_MESSAGE,
				"true"));
	}

	public boolean getFollowLinkIfFormChanged() {
		return "true".equalsIgnoreCase(getProperty(FOLLOW_LINKS_IF_CHANGED));
	}

	public boolean getDisplayErrorMessage() {
		return "true".equalsIgnoreCase(getProperty(DISPLAY_ERRORS_MESSAGE));
	}

	public String getName() {
		return locale.getLanguage();
	}

	public String getLocale() {
		return locale.getCountry();
	}

	public String getCssFileName() {
		String lc_fileName = getProperty(CSS_FILE);
		if (lc_fileName == null || lc_fileName.length() == 0) {
			return getName() + ".css";
		} else {
			return getProperty(CSS_FILE);
		}
	}

	public Class getPanelClass(String in_model) {
		Class lc_class = null;
		try {
			if (in_model == null) {
				lc_class = loadClass(getProperty(PANEL_CLASS), this);
			} else {
				lc_class = loadClass(getProperty(PANEL_CLASS + "." + in_model),
						this);
			}
		} catch (ClassNotFoundException e) {
			throw new BadSkinConfigurationException(e);
		}
		return lc_class;
	}

	public Class getPopupClass(String in_model) {
		Class lc_class = null;
		try {
			if (in_model == null) {
				lc_class = loadClass(getProperty(POPUP_CLASS), this);
			} else {
				lc_class = loadClass(getProperty(POPUP_CLASS + "." + in_model),
						this);
			}
		} catch (ClassNotFoundException e) {
			throw new BadSkinConfigurationException(e);
		}
		return lc_class;
	}

	public Class getCollectionClass(String in_model) {
		Class lc_class = null;
		try {
			if (in_model == null) {
				lc_class = loadClass(getProperty(COLLECTION_CLASS), this);
			} else {
				lc_class = loadClass(getProperty(COLLECTION_CLASS + "."
						+ in_model), this);
			}
		} catch (ClassNotFoundException e) {
			throw new BadSkinConfigurationException(e);
		}
		return lc_class;
	}

	public Class getLinkClass(String in_model) {
		Class lc_class = null;
		try {
			if (in_model == null) {
				lc_class = loadClass(getProperty(LINK_CLASS), this);
			} else {
				lc_class = loadClass(getProperty(LINK_CLASS + "." + in_model),
						this);
			}
		} catch (ClassNotFoundException e) {
			throw new BadSkinConfigurationException(e);
		}
		return lc_class;
	}

	public Class getTabsClass() {
		return getTabsClass(null);
	}

	public Class getTabsClass(String in_model) {
		Class lc_class = null;
		try {
			if (in_model == null) {
				lc_class = loadClass(getProperty(TABS_CLASS), this);
			} else {
				lc_class = loadClass(getProperty(TABS_CLASS + "." + in_model),
						this);
			}
		} catch (ClassNotFoundException e) {
			throw new BadSkinConfigurationException(e);
		}
		return lc_class;
	}

	public Class getWizardClass() {
		return getWizardClass(null);
	}

	public Class getWizardClass(String in_model) {
		Class lc_class = null;
		try {
			if (in_model == null) {
				lc_class = loadClass(getProperty(WIZARD_CLASS), this);
			} else {
				lc_class = loadClass(
						getProperty(WIZARD_CLASS + "." + in_model), this);
			}
		} catch (ClassNotFoundException e) {
			throw new BadSkinConfigurationException(e);
		}
		return lc_class;
	}

	public Class getTabClass() {
		Class lc_class = null;
		try {
			lc_class = loadClass(getProperty(TAB_CLASS), this);
		} catch (ClassNotFoundException e) {
			throw new BadSkinConfigurationException(e);
		}
		return lc_class;
	}

	public FieldInterface getFieldInterface() {
		return getFieldInterface(null);
	}

	public FieldInterface getFieldInterface(String in_model) {
		if (in_model == null) {
			return fieldInterface;
		} else {
			FieldInterface lc_interface = null;
			try {
				Class lc_class = loadClass(getProperty(FIELD_CLASS + "."
						+ in_model), this);
				lc_interface = (FieldInterface) lc_class.newInstance();
			} catch (Exception e) {
				throw new BadSkinConfigurationException(e);
			}
			return lc_interface;
		}
	}

	public IPagerRenderer getPagerRenderer() {
		return pagerRenderer;
	}

	public ICrumbRenderer getCrumbRenderer(String in_model) {
		if (in_model == null) {
			return crumbRenderer;
		} else {
			ICrumbRenderer lc_interface = null;
			try {
				Class lc_class = loadClass(getProperty(CRUMB_CLASS + "."
						+ in_model), this);
				lc_interface = (ICrumbRenderer) lc_class.newInstance();
			} catch (Exception e) {
				throw new BadSkinConfigurationException(e);
			}
			return lc_interface;
		}
	}

	public IButtonImageRenderer getButtonRenderer(String in_model) {
		if (in_model == null) {
			return buttonRenderer;
		} else {
			throw new IllegalStateException("Not implemented");
		}
	}

	public IMenuRenderer getMenuRenderer(String in_model) {
		if (in_model == null) {
			return menuRenderer;
		} else {
			throw new IllegalStateException("Not implemented");
		}
	}

	public IToolbarRenderer getToolbarRenderer(String in_model) {
		if (in_model == null) {
			return toolbarRenderer;
		} else {
			IToolbarRenderer lc_renderer = null;
			try {
				Class lc_class = loadClass(getProperty(TOOLBAR_CLASS + "."
						+ in_model), this);
				lc_renderer = (IToolbarRenderer) lc_class.newInstance();
			} catch (Exception e) {
				throw new BadSkinConfigurationException(e);
			}
			return lc_renderer;
		}
	}

	public IWizardRenderer getWizardRenderer(String in_model) {
		if (in_model == null) {
			return wizardRenderer;
		} else {
			IWizardRenderer lc_renderer = null;
			try {
				Class lc_class = loadClass(getProperty(WIZARD_CLASS + "."
						+ in_model), this);
				lc_renderer = (IWizardRenderer) lc_class.newInstance();
			} catch (Exception e) {
				throw new BadSkinConfigurationException(e);
			}
			return lc_renderer;
		}
	}

	public ITreeGridRenderer getTreeGridRenderer(String in_model) {
		if (in_model == null) {
			return treeGridRenderer;
		} else {
			ITreeGridRenderer lc_renderer = null;
			try {
				Class lc_class = loadClass(getProperty(TREEGRID_CLASS + "."
						+ in_model), this);
				lc_renderer = (ITreeGridRenderer) lc_class.newInstance();
			} catch (Exception e) {
				throw new BadSkinConfigurationException(e);
			}
			return lc_renderer;
		}
	}

	public ILayoutRenderer getGridRenderer(String in_model) {
		if (in_model == null) {
			return gridRenderer;
		} else {
			throw new IllegalStateException("Not implemented");
		}
	}

	public AbstractFormatter getFormatter() {
		if (formatter == null) {
			throw new BadSkinConfigurationException("Null formatter");
		} else {
			return formatter;
		}
	}

	public GenericPolicy getGenericPolicy() {
		if (genericPolicy == null) {
			throw new BadSkinConfigurationException("Null genericPolicy");
		} else {
			return genericPolicy;
		}
	}

	/**
	 * 
	 * @deprecated Replaced bu getFieldPolicy()
	 */
	public FieldPolicy getPolicy() {
		return getFieldPolicy();
	}

	public FieldPolicy getFieldPolicy() {
		if (fieldPolicy == null) {
			throw new BadSkinConfigurationException("Null policy");
		} else {
			return fieldPolicy;
		}
	}

	public FormUtilsInterface getFormUtils() {
		return formutils;
	}

	public boolean isSortTokenRequired() {
		return sortTokenRequired;
	}

	public boolean isLinkTokenRequired() {
		return linkTokenRequired;
	}

	public String getProperty(String in_property)
			throws MissingResourceException {
		String lc_value;
		try {
			lc_value = resources.getString(in_property);
		} catch (MissingResourceException mre) {
			lc_value = defaultResources.getString(in_property);
		}
		return lc_value;
	}

	public String getProperty(String in_property, String in_defaultValue) {
		try {
			String lc_value = getProperty(in_property);
			return lc_value;
		} catch (MissingResourceException e) {
			return in_defaultValue;
		}

	}

	public int getNumberOfMenusLoaded() {
		return numberOfMenusLoaded;
	}

	public boolean isNestedCompatible() {
		return nestedCompatbility;
	}

	/**
	 * @return
	 */
	public TreeviewInterface getTreeviewInterface() {
		return treeview;
	}

	/**
	 * Retourne le caract�re utilis� pour les EL.
	 */
	public String getELCharacter() {
		return getProperty(EL_CHARACTER, "$");
	}

	/**
	 * Return true if autoskip is activated.
	 */
	public boolean isAutoskipActivated() {
		return Boolean.TRUE.toString().equals(
				getProperty(AUTO_SKIP, Boolean.FALSE.toString()));
	}

	public boolean isXhtml() {
		return xhtml;
	}

	/**
	 * Return true if cookie is activated.
	 */
	public boolean isCookieActivated() {
		return Boolean.TRUE.toString().equals(
				getProperty(COOKIE, Boolean.TRUE.toString()));
	}

	/**
	 * Append the imported scripts and style sheets in a PageContext
	 * 
	 * @param includeLayoutScripts
	 * @param pageContext
	 * @throws JspException
	 */
	public static void appendImportedResources(boolean includeLayoutScripts, PageContext pageContext) throws JspException {
		// Get the current skin.
		Skin lc_skin = LayoutUtils.getSkin(pageContext.getSession());
		
		// Load the CSS skin.
		String cssLocation = lc_skin.getCssDirectory(pageContext.getRequest())+"/"+lc_skin.getCssFileName(); 
		HTMLUtils.generateTag(pageContext, "link", new String[][]{{"rel", "stylesheet"}, {"href", cssLocation}, {"type", "text/css"}});
		
		// Generate various Javascript variables.
		HTMLUtils.openTag(pageContext, "script", new String[][]{{"type", "text/javascript"}});
		StringBuffer sb = new StringBuffer();
		sb.append("var imgsrc=\"");
		sb.append(lc_skin.getImageDirectory(pageContext.getRequest()));
		if (!lc_skin.getImageDirectory(pageContext.getRequest()).endsWith("/")) sb.append("/");
		sb.append("\"; var scriptsrc=\"");
		sb.append(lc_skin.getConfigDirectory(pageContext.getRequest()));
		if (!lc_skin.getConfigDirectory(pageContext.getRequest()).endsWith("/")) sb.append("/");		
		sb.append("\"; var langue=\"");
		sb.append(LayoutUtils.getLocale(pageContext).getLanguage());
		sb.append("\"; var contextPath=\"");
		sb.append(((HttpServletRequest)pageContext.getRequest()).getContextPath());
		sb.append("\";");
		TagUtils.write(pageContext, sb.toString());
		HTMLUtils.closeTag(pageContext, "script");

		String scriptsDir = lc_skin.getScriptsDirectory(pageContext.getRequest());
		if (!scriptsDir.endsWith("/")) scriptsDir = scriptsDir + "/";
		final String SCRIPTS_DIR_TAG = "\\{" + SCRIPTS_DIR + "\\}";
	
		String configDir = lc_skin.getConfigDirectory(pageContext.getRequest());
		if (!configDir.endsWith("/")) configDir = configDir + "/";
		final String CONFIG_DIR_TAG = "\\{" + CONFIG_DIR + "\\}";
	
		// Load Struts-Layout Javascript code if configured to do so.
		if (includeLayoutScripts) {
			sb = new StringBuffer(scriptsDir);
			sb.append("javascript.js");
			HTMLUtils.openTag(pageContext, "script", new String[][]{{"type", "text/javascript"}, {"src", sb.toString()}});
			HTMLUtils.closeTag(pageContext, "script");
		}
		
		// Add imported scripts
		String skinImportedScripts = lc_skin.getProperty(IMPORTED_SCRIPTS, null);
		if (skinImportedScripts != null & !"".equals(skinImportedScripts)) {
			String[] importedScripts = skinImportedScripts.split(",");
			for (int i = 0; i < importedScripts.length; i++) {
				String importedScript = importedScripts[i];
				importedScript = importedScript.replaceAll(SCRIPTS_DIR_TAG, scriptsDir) ;
				importedScript = importedScript.replaceAll(CONFIG_DIR_TAG, configDir) ;
				HTMLUtils.openTag(pageContext, "script", new String[][]{{"type", "text/javascript"}, {"src", importedScript}});
				HTMLUtils.closeTag(pageContext, "script");
			}
		}
	}
}
