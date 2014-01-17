package com.sugarcrm.sugar.modules;

import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.QuoteRecord;
import com.sugarcrm.sugar.views.MassUpdate;
import com.sugarcrm.sugar.views.Menu;
import com.sugarcrm.sugar.views.Subpanel;

/**
 * Contains data and tasks associated with the Quotes module, such as field
 * data.
 * 
 * @author Jessica Cho 
 */
public class QuotesModule extends BWCModule {
	protected static QuotesModule module;
	
	public static QuotesModule getInstance() throws Exception {
		if(module == null) 
			module = new QuotesModule();
		return module;
	}

	/**
	 * Perform setup which does not depend on other modules.
	 * @throws Exception
	 */
	private QuotesModule() throws Exception {
		moduleNameSingular = "Quote";
		moduleNamePlural = "Quotes";
		recordClassName = QuoteRecord.class.getName();
		
		// Load field defs from CSV
		loadFields();

		// Define the columns on the ListView.
		listView.addHeader("number");
		listView.addHeader("subject");
		listView.addHeader("account_name");
		listView.addHeader("stage");
		listView.addHeader("total_amount");
		listView.addHeader("valid_until");
		listView.addHeader("user");
		listView.addHeader("date_created");

		// Related Fields
		relatedModulesOne.put("billingAccountName", "Accounts");
		relatedModulesOne.put("shippingAccountName", "Accounts");
		relatedModulesOne.put("billingContactName", "Contacts");
		relatedModulesOne.put("shippingContactName", "Contacts");
		relatedModulesOne.put("assignedUserName", "Users");
		relatedModulesOne.put("teamName", "Teams");
	
		// Quotes Subpanel
		subpanel = new Subpanel(this);
		
		// Quotes Module Menu items
		menu = new Menu(this);
		// This is a dup of Classic, needed for standard code to work.
		menu.addControl("createQuote", "a", "css", "[data-navbar-menu-item='LNK_NEW_QUOTE']");
		menu.addControl("viewQuotes", "a", "css", "[data-navbar-menu-item='LNK_QUOTE_LIST']");
		menu.addControl("viewQuoteReports", "a", "css", "[data-navbar-menu-item='LNK_QUOTE_REPORTS']");
	}

	/**
	 * Perform setup which depends on other modules already being constructed. 	
	 */
	@Override
	public void init() throws Exception {
		VoodooUtils.voodoo.log.info("Init Quotes...");

		// TODO: vood-613 Need to add documents in there after documents module is created
		// Related Subpanels 
		// relatedModulesMany.put("quotes_documents", sugar.activities);
		// relatedModulesMany.put("quotes_documents", sugar.history);
		// relatedModulesMany.put("quotes_documents", sugar.documents);
		
		// Add Subpanels
		detailView.addSubpanels();
		
		// Add Mass Update Panel
		massUpdate = new MassUpdate(this);

		subpanel.initSubpanel();		
	}
} // QuotesModule
