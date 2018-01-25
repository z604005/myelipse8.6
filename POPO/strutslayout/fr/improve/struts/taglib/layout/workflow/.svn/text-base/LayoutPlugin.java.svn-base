package fr.improve.struts.taglib.layout.workflow;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ModuleConfig;

import fr.improve.struts.taglib.layout.skin.Skin;
import fr.improve.struts.taglib.layout.sort.SortAction;
import fr.improve.struts.taglib.layout.tab.TabAction;
import fr.improve.struts.taglib.layout.treeview.TreeviewAction;

/**
 * Layout plugin for struts 1.1+.
 * 
 * The plugin only allows to specify a different configuration file.
 * People still using struts 1.0 can subclass the ActionServlet and
 * call Skin.setResourcesName("the_skin_properties_file_name") in the init method.
 * 
 * @author jnribette
 */
public class LayoutPlugin implements PlugIn {
	/**
	 * Log
	 */
	private static final Log LOG = LogFactory.getLog(LayoutPlugin.class);
	
	/**
	 * Skin name.
	 * Default is "Struts-Layout".
	 */
	private String skinResources = "Struts-Layout";
	
	/**
	 * Destroy the module.
	 */
    public void destroy() {
    	// nothing to destroy	
    	LOG.debug("Destroying Struts-Layout plugin");
    }
    
    private static final String SORT_PATH = "/sort";
    private static final String TREEVIEW_PATH = "/treeview";
    private static final String TAB_PATH = "/tab";
    
    /**
     * Initialize Struts-Layout :
     * Reads in struts-config the name of the Skin name.
     * Add the sort and treeview actions if there are not alreay here. 
     */
	public void init(ActionServlet in_servlet, ModuleConfig in_config)
		throws ServletException {
		
			// Set the skin properties file to use.
			LOG.debug("Setting skin properties name to " + skinResources);
        	Skin.setResourcesName(skinResources);
        	
        	// Check for Struts-Layout actions.
        	boolean sortAction = false;
        	boolean treeAction = false;
        	boolean tabAction = false;
        	boolean sortPath = false;
        	boolean treePath = false;
        	boolean tabPath = false;
        	ActionConfig[] configs = in_config.findActionConfigs();
        	for (int i=0;i<configs.length;i++) {
        		if (SortAction.class.getName().equals(configs[i].getType())) {
        			sortAction = true;
        			LOG.debug("Found Struts-Layout sort action mapped to " + configs[i].getPath());
        		}
        		if (SORT_PATH.equals(configs[i].getPath())) {
        			sortPath = true;
        		}
        		if (TreeviewAction.class.getName().equals(configs[i].getType())) {
        			treeAction = true;
        			LOG.debug("Found Struts-Layout treeview action mapped to " + configs[i].getPath());
        		}
        		if (TREEVIEW_PATH.equals(configs[i].getPath())) {
        			treePath = true;
        		}
        		if (TabAction.class.getName().equals(configs[i].getType())) {
        			tabAction = true;
        			LOG.debug("Found Struts-Layout tab action mapped to " +configs[i].getPath());
        		}
        		if (TAB_PATH.equals(configs[i].getPath())) {
        			tabPath = true;
        		}
        	}
        	
        	// Add sort action.
        	if (!sortAction) {
        		if (!sortPath) {
	        		ActionConfig config = new ActionMapping();
	        		config.setPath(SORT_PATH);
	        		config.setType(SortAction.class.getName());
	        		in_config.addActionConfig(config);
	        		LOG.debug("Mapping Struts-Layout sort action to /sort");
        		} else {
        			LOG.warn("Don't mapping Struts-Layout sort action : /sort is already used");
        		}
        	}
        	
        	// Add tree action.
        	if (!treeAction) {
        		if (!treePath) {
	        		ActionConfig config = new ActionMapping();
	        		config.setPath(TREEVIEW_PATH);
	        		config.setType(TreeviewAction.class.getName());
	        		in_config.addActionConfig(config);
	        		LOG.debug("Mapping Struts-Layout sort action to /treeview");
        		} else {
        			LOG.warn("Don't mapping Struts-Layout treeview action : /treeview is already used");
        		}
        	}
        	
        	// Add tab action.
        	if (!tabAction) {
        		if (!tabPath) {
        			ActionConfig config = new ActionMapping();
	        		config.setPath(TAB_PATH);
	        		config.setType(TabAction.class.getName());
	        		in_config.addActionConfig(config);
	        		LOG.debug("Mapping Struts-Layout tab action to /tab");
        		} else {
        			LOG.warn("Don't mapping Struts-Layout tab action : /tab is already used");
        		}
        	}
        	
        	LOG.debug("Struts-Layout plugin initialized");
	}
	    
	/**
	 * Setter for initialization of the skin name.
	 * @param in_skinResources
	 */
    public void setSkinResources(String in_skinResources) {
    	skinResources = in_skinResources;	
    }

}
