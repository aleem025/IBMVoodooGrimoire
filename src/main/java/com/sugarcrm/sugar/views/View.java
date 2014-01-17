package com.sugarcrm.sugar.views;

import java.util.HashMap;

import com.sugarcrm.sugar.ControlNotDefinedException;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooSelect;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.modules.Module;

/**
 * Models a view in SugarCRM, which VoodooGrimoire defines as a logically discrete area of the page which contains controls the user may interact with.
 * View may be used as-is for generic views, or extended for views where custom functionality is desired (e.g. ListView, RecordView, etc.)  
 * @author David Safar <dsafar@sugarcrm.com>
 */
public class View {
	/**
	 * The module which owns the current instance of this view.
	 */
	public Module parentModule = null;
	/**
	 * A map of the controls in this view.  Keys are custom strings, user-defined names for these controls.
	 */
	HashMap<String, VoodooControl> controls;
	
	/**
	 * Initializes the view with no parent module.  
	 * @throws Exception 
	 */
	public View() throws Exception {
		controls = new HashMap<String, VoodooControl>();
	}
	
	/**
	 * Initializes the view and specifies its parent module.  
	 * @param parentModule - the module that owns this view, likely passed in using the module's this variable when constructing the view.
	 * @throws Exception 
	 */
	public View(Module parentModuleIn) throws Exception {
		controls = new HashMap<String, VoodooControl>();
		parentModule = parentModuleIn;
	}
	
	/**
	 * Adds a new control to the view definition. 
	 * @param controlName - VoodooGrimoire name of the element to create, to be used as its key in the map
	 * @param tag - HTML tag name of the element
	 * @param strategy - type of hook to use to identify the element, typically ID, CSS, XPATH, NAME, CLASS
	 * @param hook - the actual string to  the strategy
	 * @throws Exception 
	 */
	public void addControl(String controlName, String tag, String strategy, String hook) throws Exception {
		controls.put(controlName, new VoodooControl(tag, strategy, hook));
	}
	
	/**
	 * Adds a new select to the view definition. 
	 * @param controlName - VoodooGrimoire name of the element to create, to be used as its key in the map
	 * @param tag - HTML tag name of the element
	 * @param strategy - type of hook to use to identify the element, typically ID, CSS, XPATH, NAME, CLASS
	 * @param hook - the actual string to  the strategy
	 * @throws Exception 
	 */
	public void addSelect(String controlName, String tag, String strategy, String hook) throws Exception {
		controls.put(controlName, new VoodooSelect(tag, strategy, hook));
	}
	
	/**
	 * Returns a reference to the specified control based on the VoodooGrimoire name used as its hash key. 
	 * @param controlName - the VoodooGrimoire name used as the hash key of the element to access 
	 * @return a VoodooControl object representing the requested control.
	 */
	public VoodooControl getControl(String controlName) throws Exception {
		VoodooControl toReturn = controls.get(controlName);  
		
		if(toReturn == null)
		{
			VoodooUtils.voodoo.log.severe("Control \"" + controlName + "\" not defined in this view!");
			Exception e = new ControlNotDefinedException(controlName);
			throw(e);
		}
			
		return(toReturn);
	}
}
