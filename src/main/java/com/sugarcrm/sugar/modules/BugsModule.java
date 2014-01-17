package com.sugarcrm.sugar.modules;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.BugRecord;
import com.sugarcrm.sugar.views.MassUpdate;
import com.sugarcrm.sugar.views.Menu;
import com.sugarcrm.sugar.views.Subpanel;
import com.sugarcrm.candybean.datasource.DataSource;
import com.sugarcrm.candybean.datasource.FieldSet;

/**
 * Contains data and tasks associated with the Bugs module, such as field data.
 * 
 * @author Ian Fleming <ifleming@sugarcrm.com>
 */
public class BugsModule extends StandardModule {
	protected static BugsModule module;

	public static BugsModule getInstance() throws Exception {
		if (module == null)
			module = new BugsModule();
		return module;
	}

	/**
	 * Perform setup which does not depend on other modules.
	 * 
	 * @throws Exception
	 */
	private BugsModule() throws Exception {
		moduleNameSingular = "Bug";
		moduleNamePlural = "Bugs";
		recordClassName = BugRecord.class.getName();

		// Load field defs from CSV
		loadFields();

		// Define the columns on the ListView.
		listView.addHeader("bug_number");
		listView.addHeader("name");
		listView.addHeader("status");
		listView.addHeader("type");
		listView.addHeader("priority");
		listView.addHeader("fixed_in_release");
		listView.addHeader("assigned_user_name");

		// Relate Widget access
		// TODO - Need to add releases relationships when the Releases module is
		// Automated
		relatedModulesOne.put("assignedUserName", "User");
		relatedModulesOne.put("teamName", "Teams");

		// Bugs Module Menu items
		// TODO: When JIRA story VOOD-451 is un-blocked by SFA-1287, please
		// update element def's
		menu = new Menu(this);
		menu.addControl("createBug", "a", "css", "li[data-module='Bugs'] ul[role='menu'] a[data-navbar-menu-item='LNK_NEW_BUG']");
		menu.addControl("viewBugs", "a", "css", "li[data-module='Bugs'] ul[role='menu'] a[data-navbar-menu-item='LNK_BUG_LIST']");
		menu.addControl("viewBugReports", "a", "css", "li[data-module='Bugs'] ul[role='menu'] a[data-navbar-menu-item='LNK_BUG_REPORTS']");
		menu.addControl("importBugs", "a", "css", "li[data-module='Bugs'] ul[role='menu'] a[data-navbar-menu-item='LNK_IMPORT_BUGS']");

		// Bugs Subpanel
		subpanel = new Subpanel(this);
	}

	/**
	 * Perform setup which depends on other modules already being constructed.
	 */
	@Override
	public void init() throws Exception {
		VoodooUtils.voodoo.log.info("Init Bugs...");

		// Related Subpanels

		relatedModulesMany.put("contacts_bugs", sugar.contacts);
		relatedModulesMany.put("accounts_bugs", sugar.accounts);
		relatedModulesMany.put("cases_bugs", sugar.cases);

		// Add Subpanels
		recordView.addSubpanels();

		// Bugs Mass Update Panel
		massUpdate = new MassUpdate(this);

		subpanel.initSubpanel();
	}

	public static void enableModule() throws Exception {
		// TODO - This will be removed when a common admin function exists for
		// enabling modules

		sugar.navbar.navToAdminTools();
		VoodooUtils.focusFrame("bwc-frame");

		new VoodooControl("a", "id", "configure_tabs").click();

		// Need to re-focus because the; last action reloaded the page.
		VoodooUtils.focusDefault();
		VoodooUtils.focusFrame("bwc-frame");

		new VoodooControl("div", "xpath", "//table[@id='ConfigureTabs']//div[.='Bug Tracker']").dragNDrop(new VoodooControl("div",
				"xpath", "//table[@id='ConfigureTabs']//div[@id='enabled_div']"));

		new VoodooControl("input", "xpath", "//input[@value='Save']").click();
		VoodooUtils.pause(2000);
		VoodooUtils.focusDefault();
		VoodooUtils.waitForAlertExpiration(100000);
	}

	public static void disableModule() throws Exception {
		// TODO - This will be removed when a common admin function exists for
		// disabling modules

		sugar.navbar.navToAdminTools();
		VoodooUtils.focusFrame("bwc-frame");

		new VoodooControl("a", "id", "configure_tabs").click();

		// Need to re-focus because the; last action reloaded the page.
		// Just calling focusFrame gave an error so calling focusDefault first
		VoodooUtils.focusDefault();
		VoodooUtils.focusFrame("bwc-frame");

		new VoodooControl("div", "xpath", "//table[@id='ConfigureTabs']//div[.='Bug Tracker']").dragNDrop(new VoodooControl("div",
				"xpath", "//table[@id='ConfigureTabs']//div[@id='disabled_div']"));

		new VoodooControl("input", "xpath", "//input[@value='Save']").click();
		VoodooUtils.pause(2000);
		VoodooUtils.focusDefault();
		VoodooUtils.waitForAlertExpiration(100000);
	}

	public static void createReleases(DataSource releases) throws Exception {
		// TODO - This will be removed when a common admin function exists for
		// Creating Releases

		sugar.navbar.navToAdminTools();
		VoodooUtils.focusFrame("bwc-frame");

		new VoodooControl("a", "id", "bug_tracker").click();
		VoodooUtils.pause(2000);
		VoodooUtils.focusFrame("bwc-frame");
		for (FieldSet release : releases) {
			new VoodooControl("input", "xpath", "//input[@value='  Create  ']").click();
			VoodooUtils.pause(2000);
			new VoodooControl("input", "name", "name").set(release.get("name"));

			new VoodooControl("input", "xpath", "//input[@value='  Save  ']").click();
			VoodooUtils.pause(2000);
		}
		VoodooUtils.focusDefault();
	}

	public static void deleteReleases(DataSource releases) throws Exception {
		// TODO - This will be removed when a common admin function exists for
		// Deleting Releases

		sugar.navbar.navToAdminTools();
		VoodooUtils.focusFrame("bwc-frame");

		new VoodooControl("a", "id", "bug_tracker").click();

		for (FieldSet release : releases) {
			// Iterate thru the initial data file used for creation to click the
			// delete button the correct number of times

			new VoodooControl("a", "css", ".listViewTdToolsS1").click();

			VoodooUtils.acceptDialog();
		}
		VoodooUtils.focusDefault();
	}
} // BugsModule
