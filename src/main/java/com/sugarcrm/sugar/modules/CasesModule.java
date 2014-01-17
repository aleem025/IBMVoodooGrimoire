package com.sugarcrm.sugar.modules;

import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.CaseRecord;
import com.sugarcrm.sugar.views.Menu;
import com.sugarcrm.sugar.views.MassUpdate;
import com.sugarcrm.sugar.views.Subpanel;

/**
 * Cases module object which contains tasks associated with the Cases module
 * like create/deleteAll
 * 
 * @author Mazen Louis <mlouis@sugarcrm.com>
 * 
 */
public class CasesModule extends StandardModule {
	protected static CasesModule module;

	public static CasesModule getInstance() throws Exception {
		if (module == null)
			module = new CasesModule();
		return module;
	}

	private CasesModule() throws Exception {
		moduleNameSingular = "Case";
		moduleNamePlural = "Cases";
		recordClassName = CaseRecord.class.getName();

		// Load field defs from CSV
		loadFields();

		// Define the columns on the ListView.
		listView.addHeader("case_number");
		listView.addHeader("name");
		listView.addHeader("account_name");
		listView.addHeader("priority");
		listView.addHeader("status");
		listView.addHeader("assigned_user_name");
		listView.addHeader("date_entered");

		// Relate Fields
		relatedModulesOne.put("accountName", "Account");
		relatedModulesOne.put("assignedUserName", "User");
		relatedModulesOne.put("teamName", "Team");

		// Cases Module Menu Items
		// TODO: When JIRA story VOOD-451 is un-blocked by SFA-1287, please
		// update element def's
		menu = new Menu(this);
		menu.addControl("createCase", "a", "css", "li[data-module='Cases'] ul[role='menu'] a[data-navbar-menu-item='LNK_NEW_CASE']");
		menu.addControl("viewCases", "a", "css", "li[data-module='Cases'] ul[role='menu'] a[data-navbar-menu-item='LNK_CASE_LIST']");
		menu.addControl("viewCaseReports", "a", "css", "li[data-module='Cases'] ul[role='menu'] a[data-navbar-menu-item='LNK_CASE_REPORTS']");
		menu.addControl("importCases", "a", "css", "li[data-module='Cases'] ul[role='menu'] a[data-navbar-menu-item='LNK_IMPORT_CASES']");
		
		// Cases Subpanel
				subpanel = new Subpanel(this);
	}

	/**
	 * Perform setup which depends on other modules already being constructed.
	 */
	@Override
	public void init() throws Exception {
		VoodooUtils.voodoo.log.info("Init Cases...");

		// Related Subpanels
		relatedModulesMany.put("cases_contacts", sugar.contacts);

		// Add Subpanels
		recordView.addSubpanels();

		// Cases Mass Update Panel
		massUpdate = new MassUpdate(this);
		subpanel.initSubpanel();
	}
} // Cases