package com.sugarcrm.sugar.modules;

import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.LeadRecord;
import com.sugarcrm.sugar.views.Menu;

/**
 * Lead module object exposing controls and tasks for the Leads module
 * 
 * @author Mazen Louis <mlouis@sugarcrm.com>
 */
public class LeadsModule extends StandardModule {
	protected static LeadsModule module;

	public static LeadsModule getInstance() throws Exception {
		if (module == null) module = new LeadsModule();
		return module;
	}

	private LeadsModule() throws Exception {
		moduleNameSingular = "Lead";
		moduleNamePlural = "Leads";
		recordClassName = LeadRecord.class.getName();

		//Load Leads Module element definitions from CSV
		loadFields();
		
		// Define the columns on the ListView.
		listView.addHeader("full_name");
		listView.addHeader("status");
		listView.addHeader("phone_work");
		listView.addHeader("date_entered");
		listView.addHeader("account_name");
		listView.addHeader("email");
		listView.addHeader("assigned_user_name");
		
		// Leads Module Menu Items
		menu = new Menu(this);
		menu.addControl("createLead", "a", "css", "li[data-module='Leads'] ul[role='menu'] a[data-navbar-menu-item='LNK_NEW_LEAD']");
		menu.addControl("createLeadFromVcard", "a", "css", "li[data-module='Leads'] ul[role='menu'] a[data-navbar-menu-item='LNK_IMPORT_VCARD']");
		menu.addControl("viewLeads", "a", "css", "li[data-module='Leads'] ul[role='menu'] a[data-navbar-menu-item='LNK_LEAD_LIST']");
		menu.addControl("viewLeadReports", "a", "css", "li[data-module='Leads'] ul[role='menu'] a[data-navbar-menu-item='LNK_LEAD_REPORTS']");
		menu.addControl("importLeads", "a", "css", "li[data-module='Leads'] ul[role='menu'] a[data-navbar-menu-item='LNK_IMPORT_LEADS']");
	}
	
	/**
	 * Perform setup which depends on other modules already being constructed. 	
	 */
	@Override
	public void init()
	{
		VoodooUtils.voodoo.log.info("Init Leads...");
	}
} // end LeadsModule
