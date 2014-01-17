package com.sugarcrm.sugar.modules;

import com.sugarcrm.sugar.AppModel;
import com.sugarcrm.sugar.VoodooControl;
import com.sugarcrm.sugar.VoodooUtils;
import com.sugarcrm.sugar.records.CallRecord;
import com.sugarcrm.sugar.views.MassUpdate;
import com.sugarcrm.sugar.views.Menu;
import com.sugarcrm.sugar.views.Subpanel;

/**
 * Contains data and tasks associated with the Calls module, such as field
 * data.
 * 
 * @author Ian Fleming <ifleming@sugarcrm.com> 
 */
public class CallsModule extends StandardModule {
	protected static CallsModule module;
	public final VoodooControl SEARCH_NAME;
	public final VoodooControl SEARCH_EMAIL_ADVANCED;
	public final VoodooControl SEARCH_LOTUS_NOTES;
	public final VoodooControl SEARCH_FORM_SUBMIT;
	public final VoodooControl SEARCH_FORM_CLEAR;
	
	public static CallsModule getInstance() throws Exception {
		if(module == null) 
			module = new CallsModule();
		return module;
	}

	/**
	 * Perform setup which does not depend on other modules.
	 * @return 
	 * @throws Exception
	 */
	private CallsModule() throws Exception {
		moduleNameSingular = "Call";
		moduleNamePlural = "Calls";
		recordClassName = CallRecord.class.getName();
		
		//Fields and Buttons of Search Form
		SEARCH_NAME = new VoodooControl("input", "id", "search_name_advanced");
		SEARCH_EMAIL_ADVANCED = new VoodooControl("input", "id", "email_advanced"); 
		SEARCH_LOTUS_NOTES = new VoodooControl("input", "id", "lotus_notes_id_advanced");
		SEARCH_FORM_SUBMIT = new VoodooControl("input", "id", "search_form_submit");
		SEARCH_FORM_CLEAR = new VoodooControl("input", "id", "search_form_clear");
		
		
		// Load field defs from CSV
		loadFields();

		// Define the columns on the ListView.
		listView.addHeader("direction");
		listView.addHeader("subject");
		listView.addHeader("contact");
		listView.addHeader("related_to");
		listView.addHeader("start_date");
		listView.addHeader("assigned_to");
		listView.addHeader("date_created");
		
		
		//This will override the standard link identifiers in BWCListView as the Calls list view is different from the standard BWC list view
		for(int i=1; i <= 99; i++){
			// Build internal Voodoo names for each control in a row.
			String link = String.format("link%02d", i);
			// Build a string prefix that represents the current row in each control.
			String currentRow = "table.list.view tbody tr:nth-of-type(" + (i + 2) + ")";
			//changed type index from 4 to 6 for Calls
			listView.addControl(link, "a", "css", currentRow + " td:nth-of-type(6) a");
		}		

		// Relate Widget access
		relatedModulesOne.put("assignedUserName", "Users");
		relatedModulesOne.put("teamName", "Teams");	
	
		// Calls Subpanel
		subpanel = new Subpanel(this);
		
		// Calls Module Menu items
		menu = new Menu(this);
		// This is a dup of Classic, needed for standard code to work.
		menu.addControl("createCall", "a", "css", "[data-module='Calls'] [data-navbar-menu-item='LNK_NEW_CALL']");
		menu.addControl("viewCalls", "a", "css", "[data-navbar-menu-item='LNK_CALL_LIST']");
		menu.addControl("importCalls", "a", "css", "[data-navbar-menu-item='LNK_IMPORT_CALLS']");
		menu.addControl("activitiesReport", "a", "css", "[data-navbar-menu-item='LBL_ACTIVITIES_REPORTS']");
	}
		//Calls Search form
		

	/**
	 * Perform setup which depends on other modules already being constructed. 	
	 */
	@Override
	public void init() throws Exception {
		VoodooUtils.voodoo.log.info("Init Calls...");

		// Related Subpanels
		relatedModulesMany.put("call_contacts", sugar.contacts);
		relatedModulesMany.put("call_leads", sugar.leads);
		relatedModulesMany.put("call_users", sugar.users);
		
		// Add Subpanels
		detailView.addSubpanels();
		
		// Call Mass Update Panel
		massUpdate = new MassUpdate(this);

		subpanel.initSubpanel();		
	}
} // CallsModule
