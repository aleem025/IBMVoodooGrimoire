package com.sugarcrm.sugar.modules;

import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.ContactRecord;
import com.sugarcrm.sugar.views.MassUpdate;
import com.sugarcrm.sugar.views.Menu;
import com.sugarcrm.sugar.views.Subpanel;

/**
 * Contact module object which contains tasks associated with the Contact module
 * like create/deleteAll
 * @author Mazen Louis <mlouis@sugarcrm.com>
 *
 */
public class ContactsModule extends StandardModule {
	protected static ContactsModule module;
	public final VoodooControl ADVANCED_SEARCH_LINK;
	public final VoodooControl BASIC_SEARCH_LINK;
		
	public final VoodooControl BASIC_CLEAR_BUTTON;
	public final VoodooControl BASIC_SEARCH_BUTTON;
	public final VoodooControl BASIC_SEARCH_NAME;
	public final VoodooControl BASIC_CURRENT_USER_CHBOX;
	public final VoodooControl BASIC_FAVORITES_CHBOX;	
	public final VoodooControl BASIC_SEARCH_EMAIL;
	public final VoodooControl BASIC_SEARCH_TAGS;
	public final VoodooControl BASIC_SEARCH_COUNTRY;
	
	public final VoodooControl ADVANCED_CLEAR_BUTTON;
	public final VoodooControl ADVANCED_SEARCH_BUTTON;
	public final VoodooControl ADVANCED_SEARCH_NAME;
	public final VoodooControl ADVANCED_CURRENT_USER_CHBOX;
	public final VoodooControl ADVANCED_FAVORITES_CHBOX;	
	public final VoodooControl ADVANCED_SEARCH_EMAIL;
	public final VoodooControl ADVANCED_SEARCH_TAGS;
	public final VoodooControl ADVANCED_SEARCH_COUNTRY;
	public final VoodooControl ADVANCED_SEARCH_STATE;
	public final VoodooControl ADVANCED_SEARCH_CITY;

	public static ContactsModule getInstance() throws Exception {
		if(module == null) module = new ContactsModule();
		return module;
	}

	private ContactsModule() throws Exception {
		moduleNameSingular = "Contact";
		moduleNamePlural = "Contacts";
		recordClassName = ContactRecord.class.getName();

		loadFields();
		
		// Define the columns on the ListView.
		listView.addHeader("full_name");
		listView.addHeader("title");
		listView.addHeader("phone_work");
		listView.addHeader("date_entered");
		listView.addHeader("account_name");
		listView.addHeader("email");
		listView.addHeader("assigned_user_name");
		
		// Search forms on list view
		ADVANCED_SEARCH_LINK = new VoodooControl("a", "id", "advanced_search_link");
		BASIC_SEARCH_LINK = new VoodooControl("a", "id", "basic_search_link");
				
		BASIC_CLEAR_BUTTON = new VoodooControl("input", "xpath", "//div[@id='Contactsbasic_searchSearchForm']//input[@id='search_form_clear']");
		BASIC_SEARCH_BUTTON = new VoodooControl("input", "xpath", "//div[@id='Contactsbasic_searchSearchForm']//input[@id='search_form_submit']");
		BASIC_SEARCH_NAME = new VoodooControl("input", "id", "search_name_basic");
		BASIC_CURRENT_USER_CHBOX = new VoodooControl("input", "id", "current_user_only_basic");
		BASIC_FAVORITES_CHBOX = new VoodooControl("input", "id", "favorites_only_basic");		
		BASIC_SEARCH_EMAIL = new VoodooControl("input", "id", "email_basic");
		BASIC_SEARCH_TAGS = new VoodooControl("input", "id", "//div[@id='Contactsbasic_searchSearchForm']//input[@id='Contacts_tags_search']");
		BASIC_SEARCH_COUNTRY = new VoodooControl("input", "id", "primary_address_country_basic-input");
		
		ADVANCED_CLEAR_BUTTON = new VoodooControl("input", "xpath", "//div[@id='Contactsadvanced_searchSearchForm']//input[@id='search_form_clear']");
		ADVANCED_SEARCH_BUTTON = new VoodooControl("input", "xpath", "//div[@id='Contactsadvanced_searchSearchForm']//input[@id='search_form_submit']");
		ADVANCED_SEARCH_NAME = new VoodooControl("input", "id", "search_name_advanced");
		ADVANCED_CURRENT_USER_CHBOX = new VoodooControl("input", "id", "current_user_only_advanced");
		ADVANCED_FAVORITES_CHBOX = new VoodooControl("input", "id", "favorites_only_advanced");		
		ADVANCED_SEARCH_EMAIL = new VoodooControl("input", "id", "email_advanced");
		ADVANCED_SEARCH_TAGS = new VoodooControl("input", "id", "//div[@id='Contactsadvanced_searchSearchForm']//input[@id='Contacts_tags_search']");
		ADVANCED_SEARCH_COUNTRY = new VoodooControl("input", "id", "primary_address_country_advanced-input");
		ADVANCED_SEARCH_STATE = new VoodooControl("input", "id", "primary_address_state_advanced-input");
		ADVANCED_SEARCH_CITY = new VoodooControl("input", "id", "primary_address_city_advanced");
				
		// Contact Subpanel
		subpanel = new Subpanel(this);
		
		// Contacts Module Menu Items
		menu = new Menu(this);
		menu.addControl("createContact", "a", "css", "li[data-module='Contacts'] ul[role='menu'] a[data-navbar-menu-item='LNK_NEW_CONTACT']");
		menu.addControl("createContactFromVcard", "a", "css", "li[data-module='Contacts'] ul[role='menu'] a[data-navbar-menu-item='LNK_IMPORT_VCARD']");
		menu.addControl("viewContacts", "a", "css", "li[data-module='Contacts'] ul[role='menu'] a[data-navbar-menu-item='LNK_CONTACT_LIST']");
		menu.addControl("viewContactReports", "a", "css", "li[data-module='Contacts'] ul[role='menu'] a[data-navbar-menu-item='LNK_CONTACT_REPORTS']");
		menu.addControl("importContacts", "a", "css", "li[data-module='Contacts'] ul[role='menu'] a[data-navbar-menu-item='LNK_IMPORT_CONTACTS']");
	}
		
	public void basicSelectCountry(String countrySearch, String countryName) throws Exception{
		this.BASIC_SEARCH_COUNTRY.set(countrySearch);
		new VoodooControl("li", "xpath", "//li[@data-text='" + countryName + "']").click();
	}
		
	public void advancedSelectCountry(String countrySearch, String countryName) throws Exception{
		this.ADVANCED_SEARCH_COUNTRY.set(countrySearch);
		new VoodooControl("li", "xpath", "//li[@data-text='" + countryName + "']").click();
	}
	
	public void advancedSelectState(String countrySearch, String countryName) throws Exception{
		this.ADVANCED_SEARCH_STATE.set(countrySearch);
		new VoodooControl("li", "xpath", "//li[@data-text='" + countryName + "']").click();
	}	
			
	/**
	 * Perform setup which depends on other modules already being constructed. 	
	 */
	@Override
	public void init() throws Exception {
		VoodooUtils.voodoo.log.info("Init Contacts...");

		// Related Subpanels
		relatedModulesMany.put("accounts_contacts", sugar.accounts);
		relatedModulesMany.put("cases_contacts", sugar.cases);
		
		// Add Subpanels
		recordView.addSubpanels();
		
		// Account Mass Update Panel
		massUpdate = new MassUpdate(this);
		
		subpanel.initSubpanel();
	}	
	
}//ContactsModule