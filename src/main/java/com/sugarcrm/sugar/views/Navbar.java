package com.sugarcrm.sugar.views;

import java.util.HashMap;

import com.sugarcrm.sugar.AppModel;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.modules.Module;


// TODO: Rewrite this whole thing to actually model the navbar.
/**
 * Models the SugarCRM navbar.
 * @author Mazen Louis <mlouis@sugarcrm.com>
 */
public class Navbar extends View {
	protected static Navbar navbar;
	protected static AppModel sugar;

	public View userAction;

	HashMap<Module, Menu> menus = new HashMap<Module, Menu>();

	public static Navbar getInstance() throws Exception {
		if (navbar == null) navbar = new Navbar();
		return navbar;
	}

	/**
	 * Initialize the navbar.
	 * @throws Exception
	 */
	private Navbar() throws Exception {
		sugar = AppModel.getInstance();
		userAction = new View();

		addControl("showAllModules", "a", "xpath", "//a[@data-original-title = 'Quick Create']");
		userAction.addControl("userActions", "a", "css", "#userActions a");
		userAction.addControl("admin", "a", "css", "#userActions li.administration a");
		userAction.addControl("profile", "a", "css", "#userActions li.profileactions-profile a");
		userAction.addControl("logout","a","css",".profileactions-logout a");
	}

	/**
	 * Clicks on the "caret" to activate the dropdown menu for a given module
	 * @param module desired module
	 * @throws Exception
	 */
	public void clickModuleDropdown(Module module) throws Exception {
		new VoodooControl("b", "css", "li[data-module='" + module.moduleNamePlural + "'] a[data-toggle='dropdown']").click();
		VoodooUtils.pause(1000);
	}

	/**
	 * Clicks on a module action e.g. clicking on Accounts -> Create Account
	 * @param module desired module
	 * @param menuItem menu tab action to click on e.g. createAccount, viewAccounts
	 */
	public void selectMenuItem(Module module, String menuItem) throws Exception {
		navToModule(module.moduleNamePlural);
		clickModuleDropdown(module);
		if(module.isBwc()) {
			// A bit of a hack because when the "Loading..." div closes, so does
			// the menu. 
			VoodooUtils.waitForAlertExpiration();
			if(!menus.get(module).getControl(menuItem).queryVisible()) {
				clickModuleDropdown(module);
			}
		}
		menus.get(module).getControl(menuItem).click();
		VoodooUtils.pause(2000);
	} // selectMenuItem

	/**
	 * Navigates to Admin Tools page (note: this method is just an alias for
	 * convenience).
	 * You must be on a page which displays the navbar to use this method.
	 * Leaves you on the Administration page.
	 * @throws Exception
	 */
	public void navToAdminTools() throws Exception {
		selectUserAction("admin");
		// wait a bit for the iframe to render.
		VoodooUtils.pause(5000);
	}
	
	/**
	 * Navigates to the Users Profile page (note: this method is just an alias for
	 * convenience).
	 * You must be on a page which displays the navbar to use this method.
	 * Leaves you on the Users Profile page.
	 * @throws Exception
	 */
	public void navToProfile() throws Exception {
		selectUserAction("profile");
		// wait a bit for the iframe to render.
		VoodooUtils.pause(3000);
	}

	/**
	 * Navigates to a given module.
	 * You must be on a page which displays the navbar to use this method.
	 * Leaves you on the ListView of the specified module.
	 * @param module	plural name of the module to navigate to
	 */
	public void navToModule(String module) throws Exception {
		showAllModules();
		new VoodooControl("a", "xpath", "//a[@data-module='" + module + "']").click();
		VoodooUtils.pause(3000);
	}

	/**
	 * Selects a menu item from the user actions menu.
	 * You must be on a page which displays the navbar to use this method.
	 * Leaves you on the page corresponding to the menu item selected.
	 * @param selection	the Voodoo name for the menu item to click on.
	 */
	public void selectUserAction(String selection) throws Exception {
		userAction.getControl("userActions").click();
		VoodooUtils.pause(1000);
		userAction.getControl(selection).click();
	}

	/**
	 * Expands the overflow menu to display more modules.
	 * You must be on a page which displays the navbar to use this method.
	 * Leaves you on the same page with the overflow menu open.
	 */
	public void showAllModules() throws Exception {
		getControl("showAllModules").click();
	}

	/**
	 * Adds a Menu object
	 * @param module module to add
	 * @param menu menu of the module to add
	 * @throws Exception
	 */
	public void addMenu(Module module, Menu menu) throws Exception {
		menus.put(module, menu);
	}

	/**
	 * This method will click on a recently viewed record using the passed in index
	 * @param module desired module 
	 * @param index 1 based index of the record in recently viewed to click on
	 * @throws Exception
	 */
	public void clickRecentlyViewed(Module module, int index) throws Exception {
		clickModuleDropdown(module);

		String moduleTab = "li[data-module='" + module.moduleNamePlural + "']";
		String recordIndex = " ul[role='menu'] .recentContainer li:nth-of-type(" + index + ") a";

		new VoodooControl("a","css", moduleTab + recordIndex).click();
		VoodooUtils.pause(1000);
	}

	/**
	 * This method will click on a recently viewed record using the passed in record name string
	 * @param module desired module 
	 * @param record string of the records name
	 * @throws Exception
	 */
	public void clickRecentlyViewed(Module module, String record) throws Exception {
		clickModuleDropdown(module);

		String moduleTab = "//li[@data-module='" + module.moduleNamePlural + "']";
		String recordName = " //ul[@role='menu']//a[.='" + record + "']";

		new VoodooControl("a","xpath", moduleTab + recordName).click();
		VoodooUtils.pause(1000);
	}
} // Navbar