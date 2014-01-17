package com.sugarcrm.sugar.modules;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.AccountRecord;
import com.sugarcrm.sugar.views.MassUpdate;
import com.sugarcrm.sugar.views.Menu;
import com.sugarcrm.sugar.views.Subpanel;

/**
 * Contains data and tasks associated with the Accounts module, such as field
 * data.
 * 
 * @author Mazen Louis <mlouis@sugarcrm.com>
 * @author David Safar <dsafar@sugarcrm.com> 
 */
public class AccountsModule extends StandardModule {
	protected static AccountsModule module;
	public final VoodooControl ADVANCED_SEARCH_LINK;
	public final VoodooControl BASIC_SEARCH_LINK;
	public final VoodooControl CLEAR_SEARCH_BUTTON;
	public final VoodooControl SEARCH_NAME_BASIC;
	public final VoodooControl CURRENT_USER_BASIC_CHBOX;
	public final VoodooControl FAVORITES_BASIC_CHBOX;
	public final VoodooControl SEARCH_BASIC_BUTTON;
	
	public static AccountsModule getInstance() throws Exception {
		if(module == null) 
			module = new AccountsModule();
		return module;
	}

	/**
	 * Perform setup which does not depend on other modules.
	 * @throws Exception
	 */
	private AccountsModule() throws Exception {
		moduleNameSingular = "Account";
		moduleNamePlural = "Accounts";
		recordClassName = AccountRecord.class.getName();
		
		// Load field defs from CSV
		loadFields();

		// Define the columns on the ListView.
		listView.addHeader("name");
		listView.addHeader("billing_address_city");
		listView.addHeader("billing_address_country");
		listView.addHeader("phone_office");
		listView.addHeader("assigned_user_name");
		listView.addHeader("email");
		listView.addHeader("date_entered");
		
		// Search forms on list view
		ADVANCED_SEARCH_LINK = new VoodooControl("a", "id", "advanced_search_link");
		BASIC_SEARCH_LINK = new VoodooControl("a", "id", "basic_search_link");
		CLEAR_SEARCH_BUTTON = new VoodooControl("input", "id", "search_form_clear");
		SEARCH_NAME_BASIC = new VoodooControl("input", "id", "name_basic");
		CURRENT_USER_BASIC_CHBOX = new VoodooControl("input", "id", "current_user_only_basic");
		FAVORITES_BASIC_CHBOX = new VoodooControl("input", "id", "favorites_only_basic");
		SEARCH_BASIC_BUTTON = new VoodooControl("input", "id", "search_form_submit");

		// TODO: Move the common controls for each view to its View subclass. 
		recordView.addControl("relAssignedUserName", "a", "css", "span.fld_assigned_user_name a");
		recordView.addControl("assignedUserName", "a", "css", "span.fld_assigned_user_name div a");
		
		// What?
		recordView.addControl("selectListSearchField", "input", "css", "html body div.select2-drop div.select2-search input.select2-input");
		recordView.addControl("selectListFirstItem", "span", "css", "html body div.select2-drop ul.select2-results li.select2-results-dept-0 div.select2-result-label span.select2-match");
		
		recordView.addControl("accountName", "a", "css", "div.record span.fld_parent_name div a"); // Account relate widget
		recordView.addControl("campaignName", "a", "css", "div.record span.fld_campaign_name div a"); // Campaign relate widget
		recordView.addControl("assignedUserName", "a", "css", "div.record span.fld_assigned_user_name div a"); // Assigned User relate widget

		// Relate Widget access
		relatedModulesOne.put("assignedUserName", "Users");
		relatedModulesOne.put("parentName", "Accounts");
		relatedModulesOne.put("campaignName", "Campaigns");
		relatedModulesOne.put("teamName", "Teams");
	
		// Account Subpanel
		subpanel = new Subpanel(this);
		
		// Account Module Menu items
		menu = new Menu(this);
		menu.addControl("createAccount", "a", "xpath", "//*[@id='module_list']/li[3]/div/div/ul/li[1]/a");
		menu.addControl("viewAccounts", "a", "css", "li[data-module='Accounts'] ul[role='menu'] a[data-navbar-menu-item='LNK_ACCOUNT_LIST']");
		menu.addControl("viewAccountReports", "a", "css", "li[data-module='Accounts'] ul[role='menu'] a[data-navbar-menu-item='LNK_ACCOUNT_REPORTS']");
		menu.addControl("importAccounts", "a", "css", "li[data-module='Accounts'] ul[role='menu'] a[data-navbar-menu-item='LNK_IMPORT_ACCOUNTS']");
	}

	/**
	 * Perform setup which depends on other modules already being constructed. 	
	 */
	@Override
	public void init() throws Exception {
		VoodooUtils.voodoo.log.info("Init Accounts...");

		// Related Subpanels
		relatedModulesMany.put("accounts_contacts", sugar.contacts);
		
		// Add Subpanels
		recordView.addSubpanels();
		
		// Account Mass Update Panel
		massUpdate = new MassUpdate(this);

		subpanel.initSubpanel();		
	}
} // AccountsModule
